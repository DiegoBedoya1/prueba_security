package com.example.prueba.web.Controller;

import com.example.prueba.Domain.DTO.User;
import com.example.prueba.Domain.Service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService service;

    public UserController(UserService service){
        this.service = service;
    }

    @PostMapping("/new")
    public ResponseEntity<User> create(@RequestBody User user){
        return ResponseEntity.ok(service.create(user));
    }
}
