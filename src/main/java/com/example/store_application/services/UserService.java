package com.example.store_application.services;

import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.store_application.dto.UserDTO;
import com.example.store_application.entities.User;
import com.example.store_application.repositories.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserDTO createUser(UserDTO userDTO) {
        String hashedPassword = passwordEncoder.encode(userDTO.getPassword());
        userDTO.setPassword(hashedPassword);
        User user = userRepository.save(convertToEntity(userDTO));
        return convertToDTO(user);
    }

    public UserDTO getUserByEmail(String email) {
        User user = userRepository.findByEmail(email).orElse(null);
        return convertToDTO(user);
    }

    public UserDTO getUserByUsername(String username) {
        User user = userRepository.findByUsername(username).orElse(null);
        return convertToDTO(user);
    }

    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id).orElse(null);
        return convertToDTO(user);
    }

    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(this::convertToDTO).toList();
    }

    public Long getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;

        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }

        User user = userRepository.findByUsername(username).orElse(null);
        return user.getId();
    }

    private UserDTO convertToDTO(User user) {
        return UserDTO.builder().id(user.getId()).username(user.getUsername()).email(user.getEmail())
                .role(user.getRole())
                .build();
    }

    private User convertToEntity(UserDTO userDTO) {
        return User.builder().id(userDTO.getId()).username(userDTO.getUsername()).email(userDTO.getEmail())
                .password(userDTO.getPassword()).role(userDTO.getRole()).build();
    }

}
