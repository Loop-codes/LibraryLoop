package com.example.LibraryLoop.controller;

import com.example.LibraryLoop.Repository.userRepository;
import com.example.LibraryLoop.dto.user.LoginRequestDTO;
import com.example.LibraryLoop.dto.user.RegisterRequestDTO;
import com.example.LibraryLoop.dto.user.ResponseDTO;
import com.example.LibraryLoop.entity.User;
import com.example.LibraryLoop.security.config.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final userRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO body) {
        User user = this.repository.findByEmail(body.email())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (passwordEncoder.matches(body.password(), user.getPassword())) {
            String token = this.tokenService.generateToken(user);
            return ResponseEntity.ok(new ResponseDTO(user.getUsername(), token));
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequestDTO body) {

        if (this.repository.findByEmail(body.email()).isPresent()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "Já existe um usuário com esse e-mail cadastrado."));
        }

        if (this.repository.findByUsername(body.username()).isPresent()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "Já existe um usuário com esse username cadastrado."));
        }

        User newUser = new User();
        newUser.setPassword(passwordEncoder.encode(body.password()));
        newUser.setEmail(body.email());
        newUser.setUsername(body.username());
        this.repository.save(newUser);

        String token = this.tokenService.generateToken(newUser);
        return ResponseEntity.ok(new ResponseDTO(newUser.getUsername(), token));
    }
}