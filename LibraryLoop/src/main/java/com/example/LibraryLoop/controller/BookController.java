package com.example.LibraryLoop.controller;

import com.example.LibraryLoop.dto.BookDTO;
import com.example.LibraryLoop.service.BookService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("books")
public class BookController {

    private final BookService service;

    public BookController(BookService service){
        this.service = service;
    }

    @GetMapping
    public List<BookDTO> getBooks(@RequestParam String title){
        return service.searchForBookByTitle(title);
    }


}
