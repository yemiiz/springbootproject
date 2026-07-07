package com.example.springtest1.repository;

import com.example.springtest1.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    Page<Book> findByCategory(String category, Pageable pageable);
    Page<Book> findByNameContaining(String name, Pageable pageable);
    //Page<Book> findAll(Pageable pageable);    // 不需要，因为可以使用默认的findAll
    List<Book> findByName(String name);
    List<Book> findByAuthor(String author);
    List<Book> findByCategory(String category);
}
