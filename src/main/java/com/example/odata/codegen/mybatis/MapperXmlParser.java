package com.example.odata.codegen.mybatis;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * MyBatis mapper XML → {@link MapperModel} 파서 (DOM).
 * mybatis-3-mapper.dtd 외부 조회를 막기 위해 DTD 로딩 비활성화.
 */
final class MapperXmlParser {

    // #{paramName} 또는 #{paramName,jdbcType=...} 에서 paramName 추출
    private static final Pattern PARAM = Pattern.compile("#\\{\\s*([A-Za-z0-9_$]+)");

    MapperModel parse(Path xmlFile) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        // 외부 DTD/엔티티 차단 (오프라인 파싱)
        factory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
        factory.setFeature("http://xml.org/sax/features/external-general-entities", false);
        factory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
        DocumentBuilder builder = factory.newDocumentBuilder();

        Document doc;
        try (InputStream in = Files.newInputStream(xmlFile)) {
            doc = builder.parse(in);
        }
        Element root = doc.getDocumentElement(); // <mapper>
        String namespace = root.getAttribute("namespace");

        List<MapperModel.ResultMapModel> resultMaps = parseResultMaps(root);
        List<MapperModel.StatementModel> statements = parseStatements(root);
        return new MapperModel(namespace, resultMaps, statements);
    }

    private List<MapperModel.ResultMapModel> parseResultMaps(Element root) {
        List<MapperModel.ResultMapModel> list = new ArrayList<>();
        for (Element rm : childElements(root, "resultMap")) {
            String id = rm.getAttribute("id");
            String type = rm.getAttribute("type");
            List<MapperModel.FieldModel> fields = new ArrayList<>();
            // <id> + <result> 둘 다 컬럼 매핑
            for (Element f : childElements(rm, "id")) {
                fields.add(field(f));
            }
            for (Element f : childElements(rm, "result")) {
                fields.add(field(f));
            }
            list.add(new MapperModel.ResultMapModel(id, emptyToNull(type), fields));
        }
        return list;
    }

    private MapperModel.FieldModel field(Element e) {
        return new MapperModel.FieldModel(
                e.getAttribute("column"),
                e.getAttribute("property"),
                emptyToNull(e.getAttribute("javaType")));
    }

    private List<MapperModel.StatementModel> parseStatements(Element root) {
        List<MapperModel.StatementModel> list = new ArrayList<>();
        for (String kind : new String[]{"select", "insert", "update", "delete"}) {
            for (Element st : childElements(root, kind)) {
                list.add(new MapperModel.StatementModel(
                        kind,
                        st.getAttribute("id"),
                        emptyToNull(st.getAttribute("parameterType")),
                        emptyToNull(st.getAttribute("resultType")),
                        emptyToNull(st.getAttribute("resultMap")),
                        scanParams(st.getTextContent())));
            }
        }
        return list;
    }

    /** SQL 본문에서 #{...} 파라미터명 추출 (등장순, 중복 제거). 점 표기(a.b)는 루트만. */
    private List<String> scanParams(String sql) {
        Set<String> names = new LinkedHashSet<>();
        Matcher m = PARAM.matcher(sql == null ? "" : sql);
        while (m.find()) {
            names.add(m.group(1));
        }
        return new ArrayList<>(names);
    }

    private List<Element> childElements(Element parent, String tag) {
        List<Element> out = new ArrayList<>();
        NodeList nodes = parent.getChildNodes();
        for (int i = 0; i < nodes.getLength(); i++) {
            Node n = nodes.item(i);
            if (n.getNodeType() == Node.ELEMENT_NODE && tag.equals(n.getNodeName())) {
                out.add((Element) n);
            }
        }
        return out;
    }

    private String emptyToNull(String s) {
        return (s == null || s.isBlank()) ? null : s;
    }
}
