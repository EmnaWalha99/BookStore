package com.example.Bookstore.services;

import com.example.Bookstore.dto.CartDto;
import com.example.Bookstore.models.CartItem;

import java.util.List;

public interface CartService {

    List<CartItem> addBookToCart(Long bookId);

    boolean removeBookFromCart(Long bookId);

    List<CartItem> getCartItems();

    int getCartItemCount();

    CartItem increaseBookQuantity(Long bookId);

    CartItem decreaseBookQuantity(Long bookId);

    void clearCart();




}
