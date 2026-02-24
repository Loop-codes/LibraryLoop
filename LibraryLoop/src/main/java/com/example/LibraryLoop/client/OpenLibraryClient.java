package com.example.LibraryLoop.client;

import com.example.LibraryLoop.dto.OpenLibraryResponseDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class OpenLibraryClient {

    private final RestTemplate restTemplate = new RestTemplate();

    private final String URL =
            "https://openlibrary.org/search.json?q=%s";

    public OpenLibraryResponseDTO searchforBook(String title){
        String url = String.format(URL, title.replace(" ", "+"));

        return restTemplate.getForObject(url, OpenLibraryResponseDTO.class);
    }
}
