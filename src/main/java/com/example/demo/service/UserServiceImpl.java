package com.example.demo.service;

import com.example.demo.model.dto.UserRegisterDTO;
import com.example.demo.model.entity.User;
import org.springframework.stereotype.Service;
import com.example.demo.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    // Direct injection via constructor
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean register(UserRegisterDTO registerDTO) {
        // 1. Password match check
        if (!registerDTO.getPassword().equals(registerDTO.getConfirmPassword())) {
            return false;
        }

        // 2. Unique username check
        if (userRepository.existsByUsername(registerDTO.getUsername())) {
            return false;
        }

        // 3. Unique email check
        if (userRepository.existsByEmail(registerDTO.getEmail())) {
            return false;
        }

        // 4. Map DTO data to database Entity
        User user = new User();
        user.setUsername(registerDTO.getUsername());
        user.setEmail(registerDTO.getEmail());

        // TODO: Add PasswordEncoder hashing here later for security!
        user.setPassword(registerDTO.getPassword());

        userRepository.save(user);
        return true;
    }
}
