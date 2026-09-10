package com.example.prueba.web.Controller;

import com.example.prueba.Domain.DTO.User;
import com.example.prueba.Domain.Service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Tag(
        name = "Usuarios",
        description = "Operacion para la creacion de usuario"
)
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService service;

    public UserController(UserService service){
        this.service = service;
    }

    @Operation(
            summary = "Crear usuario",
            description = "Permite la creacion de un nuevo usuario"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "El usuario fue creado exitosamente"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Formato no valido"
            )
    })
    @PostMapping("/new")
    public ResponseEntity<User> create( @Valid @RequestBody User user){
        return ResponseEntity.ok(service.create(user));
    }
}
