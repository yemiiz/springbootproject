package com.example.springtest1.service;

import com.example.springtest1.dto.BookCreateDTO;
import com.example.springtest1.entity.Book;
import com.example.springtest1.repository.BookRepository;
import com.example.springtest1.vo.BookVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;

    private static final Logger log = LoggerFactory.getLogger(BookService.class);

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public List<Book> search(String keyword, String category) {
        if (category != null && !category.isEmpty()) {
            return bookRepository.findByCategory(category);
        }
        if (keyword != null && !keyword.isEmpty()) {
            return bookRepository.findByName(keyword);
        }
        return bookRepository.findAll();
    }

    public Page<BookVO> findPage(Pageable pageable, String keyword, String category) {
        Page<Book> bookPage;
        if (category != null && !category.isEmpty()) {
            bookPage = bookRepository.findByCategory(category, pageable);
        } else if (keyword != null && !keyword.isEmpty()) {
            bookPage = bookRepository.findByNameContaining(keyword, pageable);
        } else {
            bookPage = bookRepository.findAll(pageable);
        }
        return bookPage.map(this::toVO);
    }

    @Transactional
    public BookVO addBook(BookCreateDTO dto) {
        Book book = new Book();
        book.setName(dto.getName());
        book.setAuthor(dto.getAuthor());
        book.setCategory(dto.getCategory());
        book.setPrice(dto.getPrice());
        book.setCreateTime(LocalDateTime.now());
        Book saved = bookRepository.save(book);
        log.info("新增图书成功: id={}, name={}", saved.getId(), saved.getName());
        return toVO(saved);
    }

    @Transactional
    public BookVO updateBook(Long id, BookCreateDTO dto) {
        Book existing = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));
        existing.setName(dto.getName());
        existing.setAuthor(dto.getAuthor());
        existing.setCategory(dto.getCategory());
        existing.setPrice(dto.getPrice());
        return toVO(bookRepository.save(existing));
    }

    @Transactional
    public void deleteBook(Long id) {
        Book existing = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));
        bookRepository.delete(existing);
    }

    private BookVO toVO(Book book) {
        BookVO bookVO = new BookVO();
        bookVO.setId(book.getId());
        bookVO.setName(book.getName());
        bookVO.setAuthor(book.getAuthor());
        bookVO.setCategory(book.getCategory());
        bookVO.setPrice(book.getPrice());
        return bookVO;
    }
}
