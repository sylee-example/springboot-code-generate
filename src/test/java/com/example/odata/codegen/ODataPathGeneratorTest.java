package com.example.odata.codegen;

import org.apache.olingo.client.api.ODataClient;
import org.apache.olingo.client.core.ODataClientFactory;
import org.apache.olingo.commons.api.edm.Edm;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * EDMX(v4) → OData path 생성 검증. bound/unbound action·function 포함.
 */
class ODataPathGeneratorTest {

    private Edm loadEdm() throws Exception {
        ODataClient client = ODataClientFactory.getClient();
        try (InputStream in = Files.newInputStream(Path.of("src/test/resources/metadata/sample-v4.xml"))) {
            return client.getReader().readMetadata(in);
        }
    }

    private List<ODataPathGenerator.PathEntry> resolve() throws Exception {
        GenConfig cfg = new GenConfig("com.example.odata.generated", Path.of("build/tmp"));
        return new ODataPathGenerator(cfg).resolve(loadEdm());
    }

    @Test
    void entityset_bykey_navigation_경로() throws Exception {
        List<String> templates = resolve().stream().map(ODataPathGenerator.PathEntry::template).toList();
        assertThat(templates).contains(
                "/Products",
                "/Products({ID})",
                "/Products({ID})/Category",
                "/Categories",
                "/Categories({ID})");
    }

    @Test
    void bound_action_과_function_이_바인딩_경로에_붙는다() throws Exception {
        List<String> templates = resolve().stream().map(ODataPathGenerator.PathEntry::template).toList();
        // 단건 바인딩
        assertThat(templates).contains("/Products({ID})/Sample.Activate");      // bound action
        assertThat(templates).contains("/Products({ID})/Sample.GetRating()");   // bound function
        // 컬렉션 바인딩 — 키 없이
        assertThat(templates).contains("/Products/Sample.DiscountAll");
    }

    @Test
    void unbound_action_function_import_경로() throws Exception {
        List<String> templates = resolve().stream().map(ODataPathGenerator.PathEntry::template).toList();
        assertThat(templates).contains(
                "/ResetAll",                       // ActionImport (POST)
                "/TopProducts(Count={Count})");    // FunctionImport (GET)
    }

    @Test
    void 상수명이_생성된다() throws Exception {
        Map<String, ODataPathGenerator.PathEntry> byConst = resolve().stream()
                .collect(Collectors.toMap(ODataPathGenerator.PathEntry::constName, Function.identity()));
        assertThat(byConst).containsKeys("PRODUCTS", "PRODUCTS_BY_KEY", "PRODUCTS_ACTIVATE", "RESET_ALL", "TOP_PRODUCTS");
    }

    @Test
    void toConst_변환규칙() {
        assertThat(ODataPathGenerator.toConst("Products")).isEqualTo("PRODUCTS");
        assertThat(ODataPathGenerator.toConst("Products_byKey")).isEqualTo("PRODUCTS_BY_KEY");
        assertThat(ODataPathGenerator.toConst("ResetAll")).isEqualTo("RESET_ALL");
    }
}
