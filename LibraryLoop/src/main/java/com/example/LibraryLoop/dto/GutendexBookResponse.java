package com.example.LibraryLoop.dto;

import lombok.Data;
import java.util.Map;

@Data
public class GutendexBookResponse {

    private Long id;

    private String title;

    private Map<String, String> formats;

}