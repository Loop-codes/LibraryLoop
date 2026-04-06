package com.example.LibraryLoop.security.config;

import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;

import org.apache.hc.client5.http.classic.HttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

    @Bean
    public RestTemplate restTemplate() {

        HttpClient httpClient = HttpClientBuilder.create()
                .build(); // ✅ simples

        HttpComponentsClientHttpRequestFactory factory =
                new HttpComponentsClientHttpRequestFactory(httpClient);

        return new RestTemplate(factory);
    }
}