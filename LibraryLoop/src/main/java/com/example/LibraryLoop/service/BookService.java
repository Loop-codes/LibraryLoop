package com.example.LibraryLoop.service;

import com.example.LibraryLoop.client.OpenLibraryClient;
import com.example.LibraryLoop.dto.BookDTO;
import com.example.LibraryLoop.dto.OpenLibraryResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final OpenLibraryClient client;


    public BookService(OpenLibraryClient client) {
        this.client = client;
    }

    public List<BookDTO> searchForBookByTitle(String title) {

        OpenLibraryResponseDTO response = client.searchforBook(title);
        return  response.getDocs();
    }
}
