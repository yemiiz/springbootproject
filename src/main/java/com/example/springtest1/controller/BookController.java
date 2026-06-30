package com.example.springtest1.controller;

import com.example.springtest1.entity.Book;
import com.example.springtest1.service.BookService;
import com.example.springtest1.common.Result;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    // 查询全部 / 按关键字搜索 / 按分类筛选
    @GetMapping
    public Result<List<Book>> list(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String category) {
        return Result.success(bookService.search(keyword, category));
    }

    @PostMapping
    public Result<Book> add(@Valid @RequestBody Book book) {
        return Result.success(bookService.addBook(book));
    }

    @PutMapping("/{id}")
    public Result<Book> update(@PathVariable Long id, @Valid @RequestBody Book book) {
        return Result.success(bookService.updateBook(id, book));
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        bookService.deleteBook(id);
        return Result.success();
    }
}
