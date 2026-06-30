package com.example.springtest1.service;

import com.example.springtest1.entity.Book;
import com.example.springtest1.repository.BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;

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

    @Transactional
    public Book addBook(Book book) {
        book.setCreateTime(LocalDateTime.now());
        return bookRepository.save(book);
    }

    @Transactional
    public Book updateBook(Long id, Book updateBook) {
        Book existing = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));
        existing.setName(updateBook.getName());
        existing.setAuthor(updateBook.getAuthor());
        existing.setCategory(updateBook.getCategory());
        existing.setPrice(updateBook.getPrice());
        return bookRepository.save(existing);
    }

    @Transactional
    public void deleteBook(Long id) {
        Book existing = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));
        bookRepository.delete(existing);
    }
}
