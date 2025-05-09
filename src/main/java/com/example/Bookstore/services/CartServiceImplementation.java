package com.example.Bookstore.services;

import com.example.Bookstore.models.Book;
import com.example.Bookstore.models.Cart;
import com.example.Bookstore.models.CartItem;
import com.example.Bookstore.models.User;
import com.example.Bookstore.repositories.BookRepository;
import com.example.Bookstore.repositories.CartItemRepository;
import com.example.Bookstore.repositories.CartRepository;
import com.example.Bookstore.repositories.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class CartServiceImplementation {


    @Autowired
    private CartItemRepository cartItemRepository ;

    @Autowired
    private CartRepository cartRepository ;

    @Autowired
    private BookRepository bookRepository ;

    @Autowired
    private UserRepository userRepository ;






}
