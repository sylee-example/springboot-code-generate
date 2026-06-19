package com.example.odata.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * SAP OData v4 호출용 WebClient 설정.
 * 생성된 Service 들이 주입받아 사용.
 */
@Configuration
public class WebClientConfig {

    @Value("${sap.odata.base-url:http://localhost:8080/odata/v4}")
    private String baseUrl;

    @Value("${sap.odata.username:}")
    private String username;

    @Value("${sap.odata.password:}")
    private String password;

    @Bean
    public WebClient odataWebClient() {
        WebClient.Builder builder = WebClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);

        // 기본 인증(SAP 환경에 맞게 OAuth/CSRF 등으로 교체)
        if (!username.isBlank()) {
            builder.defaultHeaders(h -> h.setBasicAuth(username, password));
        }
        return builder.build();
    }
}
