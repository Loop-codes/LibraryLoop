package com.example.LibraryLoop.controller;

import com.example.LibraryLoop.dto.book.BookSearchDTO;
import com.example.LibraryLoop.dto.read.ReadLinkDTO;
import com.example.LibraryLoop.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
@CrossOrigin(origins = "http://localhost:5173")
@RequiredArgsConstructor
public class BookController {

    private final BookService service;

    @GetMapping("/{workId}/read")
    public ReadLinkDTO getReadLink(@PathVariable String workId) {
        return service.getReadLink(workId);
    }

    @GetMapping("/search")
    public List<BookSearchDTO> searchBooks(
            @RequestParam String title,
            @RequestParam(defaultValue = "20") int limit) {

        return service.searchBooks(title, limit);
    }
}