package com.example.Bookstore.services;

import com.example.Bookstore.dto.CartDto;
import com.example.Bookstore.models.CartItem;

import java.util.List;

public interface CartService {

    List<CartItem>  addBookToCart(long bookId);

    List<CartItem> getCartItems();

    boolean removeBookFromCart();

    int getCartItemCount();


    CartItem increaseBookQuantity(Long bookId ,CartDto dto);
    CartItem decreaseBookQuantity(Long bookId , CartDto dto);




}
