package com.example.LibraryLoop.dto.edition;

import lombok.Data;
import java.util.List;

@Data
public class OpenLibraryEditionsResponse {

    private List<EditionDoc> entries;

    @Data
    public static class EditionDoc {
        private String ocaid;
    }
}