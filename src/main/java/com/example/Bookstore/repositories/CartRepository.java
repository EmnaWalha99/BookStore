package com.example.Bookstore.repositories;

import com.example.Bookstore.models.Cart;
import com.example.Bookstore.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {

    Optional<Cart> findByUser(User user);



}
