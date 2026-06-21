package com.example.demo.service;

import com.example.demo.model.dto.UserLoginDTO;
import com.example.demo.model.dto.UserRegisterDTO;
import com.example.demo.model.entity.User;
import com.example.demo.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean register(UserRegisterDTO registerDTO) {
        if (!registerDTO.getPassword().equals(registerDTO.getConfirmPassword())) return false;
        if (userRepository.existsByUsername(registerDTO.getUsername())) return false;
        if (userRepository.existsByEmail(registerDTO.getEmail())) return false;

        User user = new User();
        user.setUsername(registerDTO.getUsername());
        user.setEmail(registerDTO.getEmail());
        user.setPassword(registerDTO.getPassword());

        userRepository.save(user);
        return true;
    }

    // Our new login validation engine logic
    @Override
    public boolean login(UserLoginDTO loginDTO) {
        Optional<User> optionalUser = userRepository.findByUsername(loginDTO.getUsername());

        // If the user doesn't exist, deny access
        if (optionalUser.isEmpty()) {
            return false;
        }

        User user = optionalUser.get();

        // Check if the input password matches what we have stored in the database
        return user.getPassword().equals(loginDTO.getPassword());
    }
}