package com.example.odata.codegen.mybatis;

import com.example.odata.codegen.GenConfig;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

/**
 * MyBatis mapper XML 코드 생성기 진입점.
 *
 * 실행:
 *   ./gradlew genMybatis --args="<mapperXmlDir 또는 파일> [outputDir] [basePackage]"
 *
 * 동작:
 *   1) mapperXmlDir 하위 *.xml glob
 *   2) DOM 파싱 → MapperModel
 *   3) resultMap→Response DTO, #{}→Request DTO, Mapper 인터페이스(namespace FQCN), Service, Controller 생성
 */
public class MybatisGenerator {

    public static void main(String[] args) throws Exception {
        if (args.length < 1) {
            System.err.println("사용법: genMybatis --args=\"<mapperXmlDir|file> [outputDir] [basePackage]\"");
            System.exit(1);
        }
        Path input = Path.of(args[0]);
        Path outputDir = Path.of(args.length > 1 ? args[1] : "src/main/java");
        String basePackage = args.length > 2 ? args[2] : "com.example.odata.generated";

        List<Path> xmlFiles = collectXml(input);
        if (xmlFiles.isEmpty()) {
            System.err.println("[genMybatis] mapper XML 없음: " + input);
            return;
        }

        GenConfig cfg = new GenConfig(basePackage, outputDir);
        MapperXmlParser parser = new MapperXmlParser();
        MapperDtoGenerator dtoGen = new MapperDtoGenerator(cfg);
        MapperInterfaceGenerator mapperGen = new MapperInterfaceGenerator(cfg);
        MapperServiceGenerator serviceGen = new MapperServiceGenerator(cfg);
        MapperControllerGenerator controllerGen = new MapperControllerGenerator(cfg);

        for (Path xml : xmlFiles) {
            MapperModel model = parser.parse(xml);
            if (model.namespace() == null || model.namespace().isBlank()) {
                System.out.println("  [SKIP] namespace 없음: " + xml.getFileName());
                continue;
            }
            dtoGen.generate(model);
            mapperGen.generate(model);
            serviceGen.generate(model);
            controllerGen.generate(model);
            System.out.printf("  [OK] %s → %s(DTO %d, stmt %d)%n",
                    xml.getFileName(), model.entityName(),
                    model.resultMaps().size(), model.statements().size());
        }
        System.out.println("[genMybatis] 완료 → " + outputDir.resolve(basePackage.replace('.', '/')));
    }

    private static List<Path> collectXml(Path input) throws Exception {
        if (Files.isRegularFile(input)) {
            return List.of(input);
        }
        if (!Files.isDirectory(input)) {
            return List.of();
        }
        try (Stream<Path> s = Files.walk(input)) {
            return s.filter(Files::isRegularFile)
                    .filter(p -> p.getFileName().toString().endsWith(".xml"))
                    .sorted()
                    .toList();
        }
    }
}
