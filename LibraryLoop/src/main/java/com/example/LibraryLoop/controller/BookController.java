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

    // 🔗 rota antiga (link externo)
    @GetMapping("/{workId}/link")
    public ReadLinkDTO getReadLink(@PathVariable String workId) {
        return service.getReadLink(workId);
    }

    @GetMapping("/{id}/page/{page}")
    public String getPage(@PathVariable Long id, @PathVariable int page) {

        List<String> pages = service.getBookPages(id);

        if (page < 1 || page > pages.size()) {
            return "Página inválida";
        }

        return pages.get(page - 1);
    }

    // 📚 nova rota (ler livro completo Gutendex)
    @GetMapping("/{id}/read")
    public String readBook(@PathVariable Long id) {
        return service.readBook(id);
    }

    // 🔎 buscar livros
    @GetMapping("/search")
    public List<BookSearchDTO> searchBooks(
            @RequestParam String title,
            @RequestParam(defaultValue = "20") int limit) {

        return service.searchBooks(title, limit);
    }
}