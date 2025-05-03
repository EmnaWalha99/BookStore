package com.example.Bookstore.repositories;

import com.example.Bookstore.models.Book;
import com.example.Bookstore.models.CartItem;
import com.example.Bookstore.models.User;

import java.util.List;

public interface CartItemRepository {
    List<CartItem> findByUser(User user);

    CartItem findByUserAndBook(User user , Book book);

}
