package com.example.odata.codegen.mybatis;

import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 동적 SQL(include/sql, if, choose/when, foreach) 파라미터 추출 검증.
 */
class MapperXmlParserTest {

    private Map<String, MapperModel.StatementModel> parseDynamic() throws Exception {
        Path xml = Path.of("src/test/resources/mapper/DynamicMapper.xml");
        MapperModel model = new MapperXmlParser().parse(xml);
        return model.statements().stream()
                .collect(Collectors.toMap(MapperModel.StatementModel::id, Function.identity()));
    }

    @Test
    void include_안의_sql조각_파라미터가_잡힌다() throws Exception {
        MapperModel.StatementModel st = parseDynamic().get("searchDynamic");
        // <include refid="searchWhere"/> 안의 keyword, ownerId 가 추출돼야 한다
        assertThat(st.sqlParams()).containsExactly("keyword", "ownerId");
    }

    @Test
    void choose_when_과_if_의_인라인_파라미터가_잡힌다() throws Exception {
        MapperModel.StatementModel st = parseDynamic().get("chooseDynamic");
        assertThat(st.sqlParams()).containsExactly("status", "minAge");
    }

    @Test
    void foreach_는_collection을_쓰고_루프변수는_제외한다() throws Exception {
        MapperModel.StatementModel st = parseDynamic().get("inDynamic");
        // 루프변수 id 는 제외, 실제 컬렉션 ids 와 일반 파라미터 status 만 남는다
        assertThat(st.sqlParams()).containsExactly("ids", "status");
        assertThat(st.sqlParams()).doesNotContain("id");
    }
}
