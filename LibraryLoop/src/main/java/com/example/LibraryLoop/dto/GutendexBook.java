package com.example.LibraryLoop.dto;

import lombok.Data;
import java.util.List;
import java.util.Map;

@Data
public class GutendexBook {

    private Integer id;

    private String title;

    private List<GutendexAuthor> authors;

    private Map<String, String> formats;

}