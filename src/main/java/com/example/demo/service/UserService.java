package com.example.demo.service;

import com.example.demo.model.dto.UserLoginDTO;

public interface UserService {
    boolean register(com.example.demo.model.dto.UserRegisterDTO registerDTO);

    // Add this line:
    boolean login(UserLoginDTO loginDTO);
}