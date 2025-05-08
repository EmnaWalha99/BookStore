package com.example.Bookstore.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="carts")
public class Cart {

    @Id
    @GeneratedValue
    private Long id ;

    private  boolean isActive=true ;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user ;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItem> items = new ArrayList<>();

    public Cart() {
    }

    public Cart(boolean isActive, User user) {
        this.isActive = isActive;
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<CartItem> getItems() {
        return items;
    }

    public void setItems(List<CartItem> items) {
        this.items = items;
    }

    public double getTotalPrice() {
        return items.stream()
                .mapToDouble(CartItem::getTotalPrice)
                .sum();
    }
}

