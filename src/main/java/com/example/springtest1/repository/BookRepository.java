package com.example.springtest1.repository;

import com.example.springtest1.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    
    List<Book> findByName(String name);
    List<Book> findByAuthor(String author);
    List<Book> findByCategory(String category);
}
