package com.example.Bookstore.dto;

import lombok.Data;

@Data
public class AuthRelationDTO {
    private Long id;
    private String username;
    private String email;

    public AuthRelationDTO() {

    }

    public AuthRelationDTO(Long id, String email, String username) {
        this.id = id;
        this.email = email;
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}