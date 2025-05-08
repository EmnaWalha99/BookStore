package com.example.Bookstore.repositories;

import com.example.Bookstore.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {

    User findByEmail(String email);




}
