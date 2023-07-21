package com.example.registerlogin.userController;


import com.example.registerlogin.dto.LoginDTO;
import com.example.registerlogin.dto.UserDTO;
import com.example.registerlogin.entity.User;
import com.example.registerlogin.service.UserService;
import com.example.registerlogin.response.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("api/user")
public class UserController {


    @Autowired(required = true)
    private UserService userService;

    @PostMapping("/save")
    public User saveUser(@RequestBody UserDTO userDTO){
        User id = userService.addUser(userDTO);
        return id;
    }


    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginDTO loginDTO){
        LoginResponse loginResponse = userService.loginUser(loginDTO);
        return ResponseEntity.ok(loginResponse);

    }



}
