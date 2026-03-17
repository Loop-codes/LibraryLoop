package com.example.LibraryLoop.service;

import com.example.LibraryLoop.Repository.userRepository;
import com.example.LibraryLoop.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final userRepository userRepository;

    public UserService(userRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Create User
    public User createUser(User user) {
        return userRepository.save(user);
    }

    // Get all Users
    public List<User> findAll() { return userRepository.findAll();}

    // Get by ID
    public Optional<User> findById(Long id) { return userRepository.findById(id);}

    // Delete User by ID
    public void deleteUser(Long id) { userRepository.deleteById(id); }

    // Update User
    public User updateUser(User user,  Long id) {

        Optional<User> usuarioExistenteOptional = userRepository.findById(id);

        if (usuarioExistenteOptional.isPresent()) {
            user.setId(id); user.setUsername(user.getUsername());
            return userRepository.save(user);
        }else {
            return null;
        }
    }
}
