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
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * MyBatis mapper XML → {@link MapperModel} 파서 (DOM).
 * mybatis-3-mapper.dtd 외부 조회를 막기 위해 DTD 로딩 비활성화.
 */
final class MapperXmlParser {

    // #{paramName} 또는 #{paramName,jdbcType=...} 에서 paramName 추출 (점 표기 a.b 는 루트 a 만)
    private static final Pattern PARAM = Pattern.compile("#\\{\\s*([A-Za-z0-9_$]+)");
    // OGNL 식별자 루트 추출용 — 컬렉션 표현식(a.b, a[0]) 의 선두 토큰
    private static final Pattern IDENT = Pattern.compile("[A-Za-z_$][A-Za-z0-9_$]*");

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

        Map<String, Element> sqlFragments = indexSqlFragments(root);
        List<MapperModel.ResultMapModel> resultMaps = parseResultMaps(root);
        List<MapperModel.StatementModel> statements = parseStatements(root, sqlFragments);
        return new MapperModel(namespace, resultMaps, statements);
    }

    /** <sql id="..."> 조각을 id → Element 로 인덱싱 (include 해석용). */
    private Map<String, Element> indexSqlFragments(Element root) {
        Map<String, Element> map = new HashMap<>();
        for (Element sql : childElements(root, "sql")) {
            String id = sql.getAttribute("id");
            if (!id.isBlank()) {
                map.put(id, sql);
            }
        }
        return map;
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

    private List<MapperModel.StatementModel> parseStatements(Element root, Map<String, Element> sqlFragments) {
        List<MapperModel.StatementModel> list = new ArrayList<>();
        for (String kind : new String[]{"select", "insert", "update", "delete"}) {
            for (Element st : childElements(root, kind)) {
                list.add(new MapperModel.StatementModel(
                        kind,
                        st.getAttribute("id"),
                        emptyToNull(st.getAttribute("parameterType")),
                        emptyToNull(st.getAttribute("resultType")),
                        emptyToNull(st.getAttribute("resultMap")),
                        scanParams(st, sqlFragments)));
            }
        }
        return list;
    }

    /**
     * statement DOM 을 순회하며 파라미터명 추출 (등장순, 중복 제거).
     * - 텍스트의 #{...} 수집 (점 표기 a.b 는 루트 a 만)
     * - <include refid> 는 대상 <sql> 조각으로 들어가 수집 (재귀, 순환 방지)
     * - <foreach> 는 collection 식의 루트를 파라미터로, item/index 는 로컬 루프변수로 제외
     * - <bind name> 도 로컬 변수로 제외
     */
    private List<String> scanParams(Element statement, Map<String, Element> sqlFragments) {
        Set<String> names = new LinkedHashSet<>();
        Set<String> localVars = new HashSet<>(); // foreach item/index, bind name — 파라미터 아님
        walk(statement, sqlFragments, names, localVars, new HashSet<>());
        names.removeAll(localVars);
        return new ArrayList<>(names);
    }

    private void walk(Node node, Map<String, Element> frags, Set<String> names,
                      Set<String> localVars, Set<String> visitingRefids) {
        NodeList children = node.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            Node n = children.item(i);
            switch (n.getNodeType()) {
                case Node.TEXT_NODE, Node.CDATA_SECTION_NODE -> scanText(n.getNodeValue(), names);
                case Node.ELEMENT_NODE -> walkElement((Element) n, frags, names, localVars, visitingRefids);
                default -> { /* 무시 */ }
            }
        }
    }

    private void walkElement(Element e, Map<String, Element> frags, Set<String> names,
                             Set<String> localVars, Set<String> visitingRefids) {
        switch (e.getNodeName()) {
            case "include" -> {
                String refid = e.getAttribute("refid");
                Element frag = frags.get(refid);
                // refid 가 #{prop} 동적이거나 미정의면 건너뜀. 순환 방지.
                if (frag != null && visitingRefids.add(refid)) {
                    walk(frag, frags, names, localVars, visitingRefids);
                    visitingRefids.remove(refid);
                }
            }
            case "foreach" -> {
                rootIdent(e.getAttribute("collection")).ifPresent(names::add);
                addLocal(e.getAttribute("item"), localVars);
                addLocal(e.getAttribute("index"), localVars);
                walk(e, frags, names, localVars, visitingRefids);
            }
            case "bind" -> addLocal(e.getAttribute("name"), localVars);
            default -> walk(e, frags, names, localVars, visitingRefids);
        }
    }

    private void scanText(String text, Set<String> names) {
        if (text == null) {
            return;
        }
        Matcher m = PARAM.matcher(text);
        while (m.find()) {
            names.add(m.group(1));
        }
    }

    private void addLocal(String name, Set<String> localVars) {
        if (name != null && !name.isBlank()) {
            localVars.add(name.trim());
        }
    }

    /** 컬렉션 식("ids", "user.roleIds", "list") 의 선두 식별자 추출. */
    private java.util.Optional<String> rootIdent(String expr) {
        if (expr == null || expr.isBlank()) {
            return java.util.Optional.empty();
        }
        Matcher m = IDENT.matcher(expr.trim());
        return m.find() ? java.util.Optional.of(m.group()) : java.util.Optional.empty();
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
