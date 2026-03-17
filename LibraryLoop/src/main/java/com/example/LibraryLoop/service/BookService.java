package com.example.LibraryLoop.service;

import com.example.LibraryLoop.client.GutendexClient;
import com.example.LibraryLoop.dto.GutendexAuthor;
import com.example.LibraryLoop.dto.GutendexBookResponse;
import com.example.LibraryLoop.dto.book.BookSearchDTO;
import com.example.LibraryLoop.dto.GutendexResponse;
import com.example.LibraryLoop.dto.read.ReadLinkDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

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
                .filter(f -> f.getKey().contains("text/plain"))
                .findFirst()
                .map(Map.Entry::getValue)
                .orElse(null);

        if (textUrl == null) {
            return "Livro não possui versão em texto";
        }

        ResponseEntity<String> result =
                restTemplate.exchange(textUrl, HttpMethod.GET, null, String.class);

        // se veio redirect
        if (result.getStatusCode().value() == 302) {

            String redirectUrl =
                    Objects.requireNonNull(result.getHeaders().getLocation()).toString();

            return restTemplate.getForObject(redirectUrl, String.class);
        }

        return result.getBody();
    }

    public ReadLinkDTO getReadLink(String bookId) {

        String link = "https://www.gutenberg.org/ebooks/" + bookId;

        return new ReadLinkDTO(true, link);
    }
}