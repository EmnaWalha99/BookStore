package com.example.Bookstore.services;

import com.example.Bookstore.dto.CartDto;
import com.example.Bookstore.models.CartItem;

import java.util.List;

public interface CartService {

    List<CartItem> addBookToCart(String username, Long bookId, int quantity);

    boolean removeBookFromCart(String username, Long bookId);

    List<CartItem> getCartItems(String username);

    int getCartItemCount(String username);

    CartItem increaseBookQuantity(String username, Long bookId);

    CartItem decreaseBookQuantity(String username, Long bookId);

    void clearCart(String username);




}
