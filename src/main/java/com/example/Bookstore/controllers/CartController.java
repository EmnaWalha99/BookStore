package com.example.Bookstore.controllers;

import com.example.Bookstore.models.CartItem;
import com.example.Bookstore.services.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin(origins = "*")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/add/{bookId}")
    public ResponseEntity<List<CartItem>> addBookToCart(@PathVariable Long bookId) {
        return ResponseEntity.ok(cartService.addBookToCart(bookId));
    }

    @GetMapping("/items")
    public ResponseEntity<List<CartItem>> getCartItems() {
        return ResponseEntity.ok(cartService.getCartItems());
    }

    @DeleteMapping("/remove/{bookId}")
    public ResponseEntity<String> removeBookFromCart(@PathVariable Long bookId) {
        boolean removed = cartService.removeBookFromCart(bookId);
        if (removed) return ResponseEntity.ok("Livre retiré du panier.");
        else return ResponseEntity.badRequest().body("Livre non trouvé.");
    }

    @GetMapping("/count")
    public ResponseEntity<Integer> getCartItemCount() {
        return ResponseEntity.ok(cartService.getCartItemCount());
    }

    @PutMapping("/increase/{bookId}")
    public ResponseEntity<CartItem> increaseQuantity(@PathVariable Long bookId) {
        return ResponseEntity.ok(cartService.increaseBookQuantity(bookId));
    }

    @PutMapping("/decrease/{bookId}")
    public ResponseEntity<CartItem> decreaseQuantity(@PathVariable Long bookId) {
        return ResponseEntity.ok(cartService.decreaseBookQuantity(bookId));
    }

    @DeleteMapping("/clear")
    public ResponseEntity<String> clearCart() {
        cartService.clearCart();
        return ResponseEntity.ok("Panier vidé.");
    }
}