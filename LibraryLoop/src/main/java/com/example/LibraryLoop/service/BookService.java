package com.example.LibraryLoop.service;

import com.example.LibraryLoop.client.GutendexClient;
import com.example.LibraryLoop.dto.GutendexAuthor;
import com.example.LibraryLoop.dto.GutendexBookResponse;
import com.example.LibraryLoop.dto.book.BookSearchDTO;
import com.example.LibraryLoop.dto.GutendexResponse;
import com.example.LibraryLoop.dto.read.ReadLinkDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


import static com.example.LibraryLoop.client.OpenLibraryClient.restTemplate;

@Service
@RequiredArgsConstructor
public class BookService {

    private final GutendexClient gutendexClient;


    public List<BookSearchDTO> searchBooks(String title, int limit) {

        GutendexResponse response =
                gutendexClient.searchBooks(title);

        return response.getResults()
                .stream()
                .limit(limit)
                .map(book -> new BookSearchDTO(
                        String.valueOf(book.getId()),
                        book.getTitle(),
                        book.getAuthors()
                                .stream()
                                .map(GutendexAuthor::getName)
                                .toList(),
                        null,
                        null,
                        null,
                        null,
                        true,
                        null
                ))
                .toList();
    }

    public List<String> getBookPages(Long id) {

        String fullText = readBook(id);

        int pageSize = 1000; // caracteres por página

        List<String> pages = new ArrayList<>();

        for (int i = 0; i < fullText.length(); i += pageSize) {
            pages.add(fullText.substring(i, Math.min(i + pageSize, fullText.length())));
        }

        return pages;
    }

    public String readBook(Long id) {

        String url = "https://gutendex.com/books/" + id;

        GutendexBookResponse response =
                restTemplate.getForObject(url, GutendexBookResponse.class);

        if (response == null || response.getFormats() == null) {
            return "Livro não encontrado";
        }

        String textUrl = response.getFormats()
                .entrySet()
                .stream()
                .filter(f -> f.getKey().contains("text/html"))
                .findFirst()
                .map(Map.Entry::getValue)
                .orElse(null);

        if (textUrl == null) {
            return "Livro não possui versão em texto";
        }

        String book = restTemplate.getForObject(textUrl, String.class);

        if (book == null) {
            return "Erro ao carregar o livro";
        }

        book = book.replace("\uFEFF", "");
        book = book.replaceAll("([a-z])([A-Z])", "$1 $2");
        book = book.replaceAll("\\r", "");
        book = book.replaceAll("([.,;:])([A-Za-z])", "$1 $2");
        book = book.replaceAll("\\n+", "\n\n");
        book = book.replaceAll(" +", " ");


        return restTemplate.getForObject(textUrl, String.class);
    }

    public ReadLinkDTO getReadLink(String bookId) {

        String link = "https://www.gutenberg.org/ebooks/" + bookId;

        return new ReadLinkDTO(true, link);
    }
}