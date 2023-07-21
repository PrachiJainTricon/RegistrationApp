package com.example.registerlogin.service;

import com.example.registerlogin.dto.LoginDTO;
import com.example.registerlogin.dto.UserDTO;
import com.example.registerlogin.entity.User;
import com.example.registerlogin.response.LoginResponse;

public interface UserService {


    User addUser(UserDTO userDTO);

    LoginResponse loginUser(LoginDTO loginDTO);
}
