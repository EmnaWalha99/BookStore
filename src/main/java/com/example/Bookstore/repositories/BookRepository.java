package com.example.Bookstore.repositories;

import com.example.Bookstore.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findByTitle(String title);
    Optional<Book> findByImageUrl(String imageUrl);
    Optional<Book> findByAuthor(String author);


}

