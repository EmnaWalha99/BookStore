package com.example.Bookstore.controllers;

import com.example.Bookstore.models.Book;
import com.example.Bookstore.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class BookController {
    @Autowired
    BookRepository repository;
    @RequestMapping(value = "/books/id/{id}", method = RequestMethod.GET)
    public Book getById(@PathVariable long id) {
        return repository.findById(id).orElse(null);
    }
    @RequestMapping(value = "/books/title/{title}", method = RequestMethod.GET)
    public Book getByTitle(@PathVariable String title) {
        return repository.findByTitle(title).orElse(null);
    }
    @RequestMapping(value = "/books/author/{author}", method = RequestMethod.GET)
    public Book getByAuthor(@PathVariable String author) {
        return repository.findByAuthor(author).orElse(null);
    }
    @RequestMapping(value = "/books", method = RequestMethod.GET)
    public List<Book> getAll(){
        return repository.findAll();
    }
    @RequestMapping(value = "/books", method = RequestMethod.POST)
    public Book save(@RequestBody Book book) {
        return repository.save(book);
    }
    @RequestMapping(value = "/books/title/{title}", method = RequestMethod.PUT)
    public ResponseEntity<Book> updateByTile(@PathVariable String title,@RequestBody Book book ) {

        Optional<Book> bookOptional =repository.findByTitle(title);
        if (bookOptional.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        Book existingBook =  bookOptional.get();
        existingBook.setTitle(book.getTitle());
        existingBook.setAuthor(book.getAuthor());
        existingBook.setPrice(book.getPrice());
        existingBook.setDescription(book.getDescription());
        existingBook.setImageUrl(book.getImageUrl());
        Book updatedBook= repository.save(existingBook);
        return ResponseEntity.ok(updatedBook);
    }
    @RequestMapping(value = "/books/image/{imageUrl}", method = RequestMethod.PUT)
    public ResponseEntity<Book> updateByImage(@PathVariable String imageUrl,@RequestBody Book book ) {

        Optional<Book> bookOptional =repository.findByImageUrl(imageUrl);
        if (bookOptional.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        Book existingBook =  bookOptional.get();

        existingBook.setTitle(book.getTitle());
        existingBook.setAuthor(book.getAuthor());
        existingBook.setPrice(book.getPrice());
        existingBook.setDescription(book.getDescription());
        existingBook.setImageUrl(book.getImageUrl());
        Book updatedBook= repository.save(existingBook);
        return ResponseEntity.ok(updatedBook);
    }
    @RequestMapping(value = "/books/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@PathVariable long id) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }





}


