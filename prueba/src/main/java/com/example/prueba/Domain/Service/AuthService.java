package com.example.prueba.Domain.Service;

import com.example.prueba.Domain.DTO.AuthResponse;
import com.example.prueba.Domain.DTO.LoginRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final JWTService jwt;
    private final AuthenticationManager manager;

    public AuthService(JWTService jwt, AuthenticationManager manager) {
        this.jwt = jwt;
        this.manager = manager;
    }

    public AuthResponse login(LoginRequest request){
        var authentication = manager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getMail(), request.getPassword())
        );
        String token = jwt.generateToken((UserDetails)authentication.getPrincipal());
        return new AuthResponse(token);
    }
}
