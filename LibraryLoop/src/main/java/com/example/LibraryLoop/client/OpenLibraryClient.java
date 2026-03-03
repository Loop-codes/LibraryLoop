package com.example.LibraryLoop.client;

import com.example.LibraryLoop.dto.edition.OpenLibraryEditionsResponse;
import com.example.LibraryLoop.dto.openLibrary.OpenLibraryResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class OpenLibraryClient {

    private static RestTemplate restTemplate;

    private static final String BASE_URL = "https://openlibrary.org";

    public OpenLibraryClient(RestTemplate restTemplate) {
        OpenLibraryClient.restTemplate = restTemplate;
    }

    public static OpenLibraryResponse searchBooks(String title) {

        String url = BASE_URL + "/search.json?title=" + title;

        return restTemplate.getForObject(url, OpenLibraryResponse.class);
    }

    public OpenLibraryEditionsResponse getEditions(String workId) {

        String url = BASE_URL + "/works/" + workId + "/editions.json";

        return restTemplate.getForObject(url, OpenLibraryEditionsResponse.class);
    }

}