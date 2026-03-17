package com.example.LibraryLoop.client;

import com.example.LibraryLoop.dto.GutendexResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class GutendexClient {

    private final RestTemplate restTemplate;

    private static final String BASE_URL = "https://gutendex.com";

    public GutendexClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public GutendexResponse searchBooks(String title) {

        String url = BASE_URL + "/books?search=" + title;

        return restTemplate.getForObject(url, GutendexResponse.class);
    }
}