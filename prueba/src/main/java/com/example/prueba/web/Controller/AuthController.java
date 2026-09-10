package com.example.prueba.web.Controller;

import com.example.prueba.Domain.DTO.AuthResponse;
import com.example.prueba.Domain.DTO.LoginRequest;
import com.example.prueba.Domain.Service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(
        name = "Login",
        description = "Operacion para el inicio de sesion"
)
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService service;

    public AuthController(AuthService service) {
        this.service = service;
    }

    @Operation(
            summary = "Iniciar sesion",
            description = "Permite iniciar sesion en la aplicacion y obtiene un JWT"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "El inicio de sesion fue exitososo"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Formato no valido"
            )
    })
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request){
        return ResponseEntity.ok(service.login(request));
    }
}
