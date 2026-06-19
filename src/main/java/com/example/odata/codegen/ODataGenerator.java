package com.example.odata.codegen;

import org.apache.olingo.client.api.ODataClient;
import org.apache.olingo.client.core.ODataClientFactory;
import org.apache.olingo.commons.api.edm.Edm;
import org.apache.olingo.commons.api.edm.EdmComplexType;
import org.apache.olingo.commons.api.edm.EdmEntityContainer;
import org.apache.olingo.commons.api.edm.EdmEntitySet;
import org.apache.olingo.commons.api.edm.EdmEntityType;
import org.apache.olingo.commons.api.edm.EdmSchema;

import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

/**
 * OData v4 코드 생성기 진입점.
 *
 * 실행:
 *   ./gradlew genOData --args="https://host/sap/odata/v4/SERVICE [outputDir] [basePackage]"
 *
 * 동작:
 *   1) {serviceUrl}/$metadata 를 HTTP GET
 *   2) Apache Olingo 로 EDMX 파싱 → Edm
 *   3) ComplexType/EntityType → Response/Request DTO, EntitySet → Service/Controller 생성
 */
public class ODataGenerator {

    public static void main(String[] args) throws Exception {
        if (args.length < 1) {
            System.err.println("사용법: genOData --args=\"<serviceUrl> [outputDir] [basePackage]\"");
            System.exit(1);
        }
        String serviceUrl = stripTrailingSlash(args[0]);
        Path outputDir = Path.of(args.length > 1 ? args[1] : "src/main/java");
        String basePackage = args.length > 2 ? args[2] : "com.example.odata.generated";

        System.out.println("[genOData] metadata 조회: " + serviceUrl + "/$metadata");
        Edm edm = readMetadata(serviceUrl);

        GenConfig cfg = new GenConfig(basePackage, outputDir);
        DtoGenerator dtoGen = new DtoGenerator(cfg);
        ServiceGenerator serviceGen = new ServiceGenerator(cfg);
        ControllerGenerator controllerGen = new ControllerGenerator(cfg);

        Set<String> generatedDto = new HashSet<>();

        // 1) ComplexType → Response DTO
        for (EdmSchema schema : edm.getSchemas()) {
            for (EdmComplexType ct : schema.getComplexTypes()) {
                if (generatedDto.add(ct.getName())) {
                    dtoGen.generateResponse(ct, null); // 복합 타입 — 키 없음
                    System.out.println("  [DTO ] " + ct.getName() + "Response (complex)");
                }
            }
        }

        // 2) EntityType → Response/Request DTO
        for (EdmSchema schema : edm.getSchemas()) {
            for (EdmEntityType et : schema.getEntityTypes()) {
                if (generatedDto.add(et.getName())) {
                    dtoGen.generateResponse(et, et); // 엔티티 타입 — 키로 @JsonIdentityInfo
                    dtoGen.generateRequest(et);
                    System.out.println("  [DTO ] " + et.getName() + "Response/Request");
                }
            }
        }

        // 3) EntitySet → Service + Controller
        EdmEntityContainer container = edm.getEntityContainer();
        if (container == null) {
            System.err.println("[genOData] EntityContainer 없음 — 종료");
            return;
        }
        for (EdmEntitySet set : container.getEntitySets()) {
            serviceGen.generate(set);
            controllerGen.generate(set);
            System.out.println("  [SVC ] " + set.getEntityType().getName() + "Service");
            System.out.println("  [CTRL] " + set.getEntityType().getName() + "Controller (" + set.getName() + ")");
        }

        System.out.println("[genOData] 완료 → " + outputDir.resolve(basePackage.replace('.', '/')));
    }

    private static Edm readMetadata(String serviceUrl) throws Exception {
        HttpClient http = HttpClient.newHttpClient();
        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create(serviceUrl + "/$metadata"))
                .header("Accept", "application/xml")
                .GET()
                .build();
        HttpResponse<InputStream> resp = http.send(req, HttpResponse.BodyHandlers.ofInputStream());
        if (resp.statusCode() != 200) {
            throw new IllegalStateException("$metadata 조회 실패 HTTP " + resp.statusCode());
        }
        ODataClient client = ODataClientFactory.getClient();
        try (InputStream in = resp.body()) {
            return client.getReader().readMetadata(in);
        }
    }

    private static String stripTrailingSlash(String s) {
        return s.endsWith("/") ? s.substring(0, s.length() - 1) : s;
    }
}
