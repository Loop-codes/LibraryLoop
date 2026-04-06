package com.example.LibraryLoop.dto.book;

import java.util.List;

public class BookSearchDTO {

    private String olid;
    private String title;
    private List<String> authorName;
    private String coverId;
    private Integer firstPublishYear;
    private Integer editionCount;
    private List<String> language;
    private Boolean hasFullText;
    private Integer ratingsAverage;
    private Boolean readable;
    private String source;

    // ✔ CONSTRUTOR COMPLETO
    public BookSearchDTO(
            String olid,
            String title,
            List<String> authorName,
            String coverId,
            Integer firstPublishYear,
            Integer editionCount,
            List<String> language,
            Boolean hasFullText,
            Integer ratingsAverage
    ) {
        this.olid = olid;
        this.title = title;
        this.authorName = authorName;
        this.coverId = coverId;
        this.firstPublishYear = firstPublishYear;
        this.editionCount = editionCount;
        this.language = language;
        this.hasFullText = hasFullText;
        this.ratingsAverage = ratingsAverage;
        this.readable = readable;
        this.source = source;
    }

    // ✔ GETTERS

    public String getOlid() {
        return olid;
    }

    public String getTitle() {
        return title;
    }

    public List<String> getAuthorName() {
        return authorName;
    }

    public String getCoverId() {
        return coverId;
    }

    public Integer getFirstPublishYear() {
        return firstPublishYear;
    }

    public Integer getEditionCount() {
        return editionCount;
    }

    public List<String> getLanguage() {
        return language;
    }

    public Boolean getHasFullText() {
        return hasFullText;
    }

    public Integer getRatingsAverage() {
        return ratingsAverage;
    }

    public Boolean getReadable() {
        return readable;
    }

    public String getSource() {
        return source;
    }
}