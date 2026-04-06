package com.example.LibraryLoop.service;

import com.example.LibraryLoop.client.GutendexClient;
import com.example.LibraryLoop.dto.book.BookSearchDTO;
import com.example.LibraryLoop.dto.gutendex.GutendexAuthor;
import com.example.LibraryLoop.dto.gutendex.GutendexBookResponse;
import com.example.LibraryLoop.dto.gutendex.GutendexResponse;
import com.example.LibraryLoop.dto.read.ReadLinkDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class BookService {

    private final GutendexClient gutendexClient;
    private final RestTemplate restTemplate;

    @Cacheable(value = "books", key = "#title + '_' + #limit")
    public List<BookSearchDTO> searchBooks(String title, int limit) {

        return gutendexClient.searchBooks(title)
                .getResults()
                .stream()
                .limit(limit)
                .map(book -> {

                    String cover = null;
                    if (book.getFormats() != null) {
                        cover = book.getFormats()
                                .entrySet()
                                .stream()
                                .filter(f -> f.getKey().contains("image"))
                                .map(Map.Entry::getValue)
                                .findFirst()
                                .orElse(null);
                    }

                    boolean readable = book.getFormats() != null &&
                            book.getFormats().keySet().stream()
                                    .anyMatch(key -> key.contains("text/plain"));

                    return new BookSearchDTO(
                            String.valueOf(book.getId()),
                            book.getTitle(),
                            book.getAuthors()
                                    .stream()
                                    .map(GutendexAuthor::getName)
                                    .toList(),
                            cover,
                            null, null, null, true, null
                    );
                })
                .toList();
    }

    @Cacheable(value = "bookText", key = "#id")
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
                .filter(f -> f.getKey().contains("text/plain; charset=utf-8"))
                .findFirst()
                .map(Map.Entry::getValue)
                .orElse(null);

        if (textUrl == null) {
            throw new RuntimeException("Livro não encontrado");
        }

        textUrl = textUrl.replace("http://", "https://");
        String book = restTemplate.getForObject(textUrl, String.class);

        if (book == null) return "Erro ao carregar o livro";

        return cleanText(book);
    }

    @Cacheable(value = "bookPages", key = "#id")
    public List<String> getBookPages(Long id) {

        String fullText = readBook(id);

        int pageSize = 1500;
        List<String> pages = new ArrayList<>();
        int start = 0;

        while (start < fullText.length()) {
            int end = Math.min(start + pageSize, fullText.length());

            if (end < fullText.length()) {
                int lastSpace = fullText.lastIndexOf(" ", end);
                if (lastSpace > start) end = lastSpace;
            }

            pages.add(fullText.substring(start, end).trim());
            start = end;
        }

        return pages;
    }

    @CacheEvict(value = {"bookText", "bookPages"}, key = "#id")
    public void evictBookCache(Long id) {}

    public ReadLinkDTO getReadLink(String bookId) {
        return new ReadLinkDTO(true, "https://www.gutenberg.org/ebooks/" + bookId);
    }

    private String cleanText(String text) {
        if (text == null) return "";
        text = text.replace("\uFEFF", "");
        text = text.replace("\r\n", "\n");

        int start = text.indexOf("*** START OF");
        if (start != -1) {
            int firstLineBreak = text.indexOf("\n", start);
            text = text.substring(firstLineBreak + 1);
        }

        int end = text.indexOf("*** END OF");
        if (end != -1) text = text.substring(0, end);

        text = text.replaceAll("\n{3,}", "\n\n");
        return text.trim();
    }
}