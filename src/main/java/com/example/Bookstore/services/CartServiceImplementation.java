package com.example.Bookstore.services;

import com.example.Bookstore.models.Book;
import com.example.Bookstore.models.Cart;
import com.example.Bookstore.models.CartItem;
import com.example.Bookstore.models.User;
import com.example.Bookstore.repositories.BookRepository;
import com.example.Bookstore.repositories.CartItemRepository;
import com.example.Bookstore.repositories.CartRepository;
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
    private HttpSession session ;

    //get or create active cart for the logged in user

    private Cart getOrCreateActiveCart(User user){

        Cart cart = cartRepository.findByUserAndIsActive(user, true);
        if (cart == null) {
            cart = new Cart();
            cart.setUser(user);
            cart.setActive(true);
            cartRepository.save(cart);
        }
        return cart ;

    }




    public List<CartItem> addBookToCart(long bookId) {
        User user = (User) session.getAttribute("user");
        if (user == null) throw new RuntimeException("User not logged in");

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        Cart cart = getOrCreateActiveCart(user);

        Optional<CartItem> existingItemOpt = cartItemRepository.findByCartAndBook(cart, book);
        CartItem item;
        if (existingItemOpt.isPresent()) {
            item = existingItemOpt.get();
            item.setQuantity(item.getQuantity() + 1);
        } else {
            item = new CartItem();
            //item.setCart(cart);
            item.setBook(book);
            item.setQuantity(1);
        }

        cartItemRepository.save(item);
        return cartItemRepository.findByCart(cart);
    }



}
