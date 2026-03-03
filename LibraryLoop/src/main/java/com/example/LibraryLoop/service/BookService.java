package com.example.LibraryLoop.service;

import com.example.LibraryLoop.client.OpenLibraryClient;
import com.example.LibraryLoop.dto.edition.OpenLibraryEditionsResponse;
import com.example.LibraryLoop.dto.read.ReadLinkDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookService {

    private final OpenLibraryClient openLibraryClient;

    public ReadLinkDTO getReadLink(String workId) {

        OpenLibraryEditionsResponse response =
                openLibraryClient.getEditions(workId);

        if (response.getEntries() == null) {
            return new ReadLinkDTO(false, null);
        }

        for (OpenLibraryEditionsResponse.EditionDoc edition : response.getEntries()) {

            if (edition.getOcaid() != null) {

                String link = "https://archive.org/details/" + edition.getOcaid();

                return new ReadLinkDTO(true, link);
            }
        }

        return new ReadLinkDTO(false, null);
    }
}