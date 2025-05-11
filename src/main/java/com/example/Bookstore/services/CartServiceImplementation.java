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
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
//@RequiredArgsConstructor
public class CartServiceImplementation implements CartService {
    @Autowired
    private  UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private  CartRepository cartRepository;
    @Autowired
    private  CartItemRepository cartItemRepository;

    @Autowired
    private  HttpSession session;



    private User getCurrentUser() {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            throw new RuntimeException("Utilisateur non connecté");
        }
        return user;
    }

    @Transactional
    public List<CartItem> addBookToCart(Long bookId) {
        User user = getCurrentUser();
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Livre introuvable"));

        Cart cart = cartRepository.findByUser(user).orElseGet(() -> {
            Cart newCart = new Cart();
            newCart.setUser(user);
            newCart.setTotalPrice(0.0);
            return cartRepository.save(newCart);
        });

        Optional<CartItem> optionalItem = cartItemRepository.findByCartAndBook(cart, book);

        CartItem item;
        if (optionalItem.isPresent()) {
            item = optionalItem.get();
            item.setQuantity(item.getQuantity() + 1);
            item.setPrice(item.getQuantity() * book.getPrice());
        } else {
            item = new CartItem();
            item.setCart(cart);
            item.setBook(book);
            item.setQuantity(1);
            item.setPrice(book.getPrice());
        }

        cartItemRepository.save(item);

        List<CartItem> items = cartItemRepository.findByCart(cart);
        cart.setTotalPrice(items.stream().mapToDouble(CartItem::getPrice).sum());
        cartRepository.save(cart);

        return items;
    }

    public List<CartItem> getCartItems() {
        User user = getCurrentUser();
        Cart cart = cartRepository.findByUser(user).orElse(null);
        if (cart == null) {
            return new ArrayList<>();
        }
        return cartItemRepository.findByCart(cart);
    }

    @Transactional
    public boolean removeBookFromCart(Long bookId) {
        User user = getCurrentUser();
        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Panier non trouvé"));

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Livre introuvable"));

        Optional<CartItem> optionalItem = cartItemRepository.findByCartAndBook(cart, book);

        if (optionalItem.isPresent()) {
            cartItemRepository.delete(optionalItem.get());
            List<CartItem> items = cartItemRepository.findByCart(cart);
            cart.setTotalPrice(items.stream().mapToDouble(CartItem::getPrice).sum());
            cartRepository.save(cart);
            return true;
        }

        return false;
    }

    public int getCartItemCount() {
        User user = getCurrentUser();
        Cart cart = cartRepository.findByUser(user).orElse(null);
        if (cart == null) {
            return 0;
        }
        return cartItemRepository.findByCart(cart)
                .stream()
                .mapToInt(CartItem::getQuantity)
                .sum();
    }

    @Transactional
    public CartItem increaseBookQuantity(Long bookId) {
        User user = getCurrentUser();
        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Panier non trouvé"));

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Livre introuvable"));

        CartItem item = cartItemRepository.findByCartAndBook(cart, book)
                .orElseThrow(() -> new RuntimeException("Item non trouvé"));

        item.setQuantity(item.getQuantity() + 1);
        item.setPrice(item.getQuantity() * book.getPrice());
        cartItemRepository.save(item);

        updateCartTotal(cart);
        return item;
    }

    @Transactional
    public CartItem decreaseBookQuantity(Long bookId) {
        User user = getCurrentUser();
        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Panier non trouvé"));

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Livre introuvable"));

        CartItem item = cartItemRepository.findByCartAndBook(cart, book)
                .orElseThrow(() -> new RuntimeException("Item non trouvé"));

        if (item.getQuantity() > 1) {
            item.setQuantity(item.getQuantity() - 1);
            item.setPrice(item.getQuantity() * book.getPrice());
            cartItemRepository.save(item);
        } else {
            cartItemRepository.delete(item);
        }

        updateCartTotal(cart);
        return item;
    }

    @Transactional
    public void clearCart() {
        User user = getCurrentUser();
        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Panier non trouvé"));

        List<CartItem> items = cartItemRepository.findByCart(cart);
        cartItemRepository.deleteAll(items);
        cart.setTotalPrice(0.0);
        cartRepository.save(cart);
    }

    private void updateCartTotal(Cart cart) {
        List<CartItem> items = cartItemRepository.findByCart(cart);
        cart.setTotalPrice(items.stream().mapToDouble(CartItem::getPrice).sum());
        cartRepository.save(cart);
    }
}