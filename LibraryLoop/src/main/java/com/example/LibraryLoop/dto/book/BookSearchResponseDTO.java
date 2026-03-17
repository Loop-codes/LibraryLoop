package com.example.LibraryLoop.dto.book;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BookSearchResponseDTO {

    private String title;
    private Integer coverId;
    private List<String> author_key;

    private Integer first_publish_year;
    private String key;
    private List<String> language;

    public BookSearchResponseDTO(String title, Integer coverI, Integer firstPublishYear, String key, List<String> language, List<String> authorKey) {
        this.title = title;
        this.coverId = coverI;
        this.first_publish_year = firstPublishYear;
        this.key = key;
        this.language = language;
        this.author_key = authorKey;
    }


}