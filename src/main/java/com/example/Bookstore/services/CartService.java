package com.example.Bookstore.services;

import com.example.Bookstore.dto.CartDto;
import com.example.Bookstore.models.CartItem;

import java.util.List;

public interface CartService {

    List<CartItem>  addBookToCart(String token , long bookId);

    List<CartItem> getCartItems(String token);

    boolean removeBookFromCart(String token);

    int getCartItemCount(String token);


    CartItem increaseBookQuantity(String token , Long bookId ,CartDto dto);
    CartItem decreaseBookQuantity(String token , Long bookId , CartDto dto);




}
