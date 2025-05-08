package com.example.Bookstore.repositories;

import com.example.Bookstore.models.Book;
import com.example.Bookstore.models.Cart;
import com.example.Bookstore.models.CartItem;
import com.example.Bookstore.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findByCart(Cart cart);
    Optional<CartItem>  findByCartAndBook(Cart cart, Book book);

}
