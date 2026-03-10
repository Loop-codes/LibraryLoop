package com.example.LibraryLoop.service;

import com.example.LibraryLoop.client.OpenLibraryClient;
import com.example.LibraryLoop.dto.book.BookSearchDTO;
import com.example.LibraryLoop.dto.edition.OpenLibraryEditionsResponse;
import com.example.LibraryLoop.dto.openLibrary.OpenLibraryResponse;
import com.example.LibraryLoop.dto.read.ReadLinkDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<BookSearchDTO> searchBooks(String title, int limit) {

        OpenLibraryResponse response =
                OpenLibraryClient.searchBooks(title);

        if (response.getDocs() == null || response.getDocs().isEmpty()) {
            System.out.println("Docs está vazio!");
        } else {
            System.out.println("Docs encontrados: " + response.getDocs().size());
            response.getDocs().forEach(doc -> System.out.println(doc.getTitle()));
        }

        return response.getDocs()
                .stream()
                .limit(limit)
                .map(doc -> new BookSearchDTO(
                        doc.getKey().replace("/works/", ""),
                        doc.getTitle(),
                        doc.getAuthor_name(),
                        doc.getAuthor_key(),
                        doc.getCover_i(),
                        doc.getFirst_publish_year(),
                        doc.getLanguage(),
                        doc.getHas_fulltext(),
                        doc.getEdition_count()
                ))
                .toList();
    }
}