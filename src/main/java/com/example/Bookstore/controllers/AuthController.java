package com.example.Bookstore.controllers;

import com.example.Bookstore.dto.AuthResponse;
import com.example.Bookstore.dto.LoginRequest;
import com.example.Bookstore.dto.RegisterRequest;
import com.example.Bookstore.models.User;
import com.example.Bookstore.repositories.UserRepository;
import com.example.Bookstore.security.JwtTokenProvider;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;


import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import java.util.List;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(AuthenticationManager authenticationManager,
                          JwtTokenProvider tokenProvider,
                          UserRepository userRepository,
                          PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Show register page (GET request)
    @GetMapping("/login")
    public String showLoginPage() {
        return "login"; // Return the name of the register.html view
    }

    // Show register page (GET request)
    @GetMapping("/register")
    public String showRegisterPage() {
        return "register"; // Return the name of the register.html view
    }

    @PostMapping("/login")
    public String login(@Valid LoginRequest loginRequest, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            List<FieldError> errors = bindingResult.getFieldErrors();
            StringBuilder errorMessage = new StringBuilder();
            for (FieldError error : errors) {
                errorMessage.append(error.getField())
                        .append(": ")
                        .append(error.getDefaultMessage())
                        .append(" ");
            }
            model.addAttribute("error", errorMessage.toString());
            return "login"; // Return to login page with error message
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(), loginRequest.getPassword()
                )
        );
        String jwt = tokenProvider.generateToken(authentication);
        model.addAttribute("token", jwt); // Add JWT to the model
        return "home"; // Redirect to home page after successful login
    }


    @PostMapping("/register")
    public String register(@Valid RegisterRequest registerRequest, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            List<FieldError> errors = bindingResult.getFieldErrors();
            StringBuilder errorMessage = new StringBuilder();
            for (FieldError error : errors) {
                errorMessage.append(error.getField())
                        .append(": ")
                        .append(error.getDefaultMessage())
                        .append(" ");
            }
            model.addAttribute("error", errorMessage.toString());
            return "register"; // Return to register page with error message
        }

        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            model.addAttribute("error", "Email already in use");
            return "register"; // Return to register page with email in use error
        }

        // Save user after registration
        User user = new User();
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setUsername(registerRequest.getFullName());
        userRepository.save(user);

        // Redirect to login page after successful registration
        return "redirect:/auth/login";
    }
}