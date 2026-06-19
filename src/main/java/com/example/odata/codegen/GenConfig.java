package com.example.odata.codegen;

import java.nio.file.Path;

/**
 * 코드 생성 설정.
 *
 * @param basePackage 생성 코드 루트 패키지 (예: com.example.odata.generated)
 * @param outputDir   소스 출력 디렉토리 (예: src/main/java)
 */
public record GenConfig(String basePackage, Path outputDir) {

    public String dtoPackage() {
        return basePackage + ".dto";
    }

    public String servicePackage() {
        return basePackage + ".service";
    }

    public String controllerPackage() {
        return basePackage + ".controller";
    }

    public String mapperPackage() {
        return basePackage + ".mapper";
    }
}
