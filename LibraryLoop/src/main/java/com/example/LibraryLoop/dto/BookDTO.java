package com.example.LibraryLoop.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BookDTO {

    private String title;

    private List<String> author_name;

    private String first_publisher_year;

}
