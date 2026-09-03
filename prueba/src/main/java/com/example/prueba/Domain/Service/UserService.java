package com.example.prueba.Domain.Service;

import com.example.prueba.Domain.DTO.User;
import com.example.prueba.Domain.Repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository repo;

    public UserService(UserRepository repo){
         this.repo = repo;
    }

    public User create(User user){
        return repo.create(user);
    }
}
