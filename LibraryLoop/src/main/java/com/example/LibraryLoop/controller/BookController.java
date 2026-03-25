package com.example.LibraryLoop.controller;

import com.example.LibraryLoop.dto.book.BookReadResponse;
import com.example.LibraryLoop.dto.book.BookSearchDTO;
import com.example.LibraryLoop.dto.book.PageResponse;
import com.example.LibraryLoop.dto.read.ReadLinkDTO;
import com.example.LibraryLoop.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
@CrossOrigin(origins = "http://localhost:5173")
public class BookController {

    private final BookService service;

    private final BookService bookService;

    public BookController(BookService service, BookService bookService) {
        this.service = service;
        this.bookService = bookService;
    }

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
    public ResponseEntity<BookReadResponse> readBook(@PathVariable Long id) {

        String text = bookService.readBook(id);

        return ResponseEntity.ok(new BookReadResponse(id, text));
    }

    @GetMapping("/{id}/pages/{page}")
     public ResponseEntity<PageResponse> getBookPage(
            @PathVariable Long id,
            @PathVariable int page
    ) {

        List<String> pages = bookService.getBookPages(id);

        if (page < 0 || page >= pages.size()) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(
                new PageResponse(page, pages.size(), pages.get(page))
        );
    }   

    // 🔎 buscar livros
    @GetMapping(value = "/search")
    public List<BookSearchDTO> searchBooks(
            @RequestParam String title,
            @RequestParam(defaultValue = "20") int limit) {

        return service.searchBooks(title, limit);
    }
}