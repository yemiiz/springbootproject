package com.example.springtest1.controller;

import com.example.springtest1.dto.BookCreateDTO;
import com.example.springtest1.service.BookService;
import com.example.springtest1.common.Result;
import com.example.springtest1.vo.BookVO;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    private static final Logger log = LoggerFactory.getLogger(BookController.class);
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    // 查询全部 / 按关键字搜索 / 按分类筛选
    @GetMapping
    public Result<Page<BookVO>> list(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String category,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size){
        Pageable pageable = PageRequest.of(page, size);
        log.info("GET /books?keyword={}, page={}", keyword, page);
        return Result.success(bookService.findPage(pageable, keyword, category));
    }

    @PostMapping
    public Result<BookVO> add(@Valid @RequestBody BookCreateDTO dto) {
        return Result.success(bookService.addBook(dto));
    }

    @PutMapping("/{id}")
    public Result<BookVO> update(@PathVariable Long id, @Valid @RequestBody BookCreateDTO dto) {
        return Result.success(bookService.updateBook(id, dto));
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        bookService.deleteBook(id);
        return Result.success();
    }
}
