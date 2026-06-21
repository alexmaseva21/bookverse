package com.example.demo.service;

import com.example.demo.model.dto.UserRegisterDTO;

public interface UserService {
    boolean register(UserRegisterDTO registerDTO);
}
