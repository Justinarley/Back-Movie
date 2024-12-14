package com.example.back_api20.users.controller;

import org.springframework.http.ResponseEntity;
import com.example.back_api20.users.dto.UserRequestDto;
import com.example.back_api20.users.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api-users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserRequestDto userRequestDto) {
        String result = userService.registerUser(userRequestDto);
        if (result.contains("exitosamente")) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.badRequest().body(result);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody UserRequestDto userRequestDto) {
        String result = userService.authenticateUser(userRequestDto.getCorreo(), userRequestDto.getPassword());
        if (result.equals("Autenticación exitosa")) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.badRequest().body(result);
        }
    }
    
}
