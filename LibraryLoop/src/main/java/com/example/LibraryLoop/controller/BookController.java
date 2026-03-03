package com.example.LibraryLoop.controller;

import com.example.LibraryLoop.dto.read.ReadLinkDTO;
import com.example.LibraryLoop.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService service;

    @GetMapping("/{workId}/read")
    public ReadLinkDTO getReadLink(@PathVariable String workId) {
        return service.getReadLink(workId);
    }
}