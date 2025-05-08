package com.example.Bookstore.repositories;

import com.example.Bookstore.models.Cart;
import com.example.Bookstore.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {

    Cart findByUserAndIsActive(User user , boolean isActive);
}
