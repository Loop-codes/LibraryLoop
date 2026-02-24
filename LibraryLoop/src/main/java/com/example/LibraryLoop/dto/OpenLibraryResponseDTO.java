package com.example.LibraryLoop.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OpenLibraryResponseDTO {

    private List<BookDTO> docs;

}
