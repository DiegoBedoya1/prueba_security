package com.example.prueba.web.Controller;

import com.example.prueba.Domain.DTO.Order;
import com.example.prueba.Domain.Service.OrderService;
import com.example.prueba.ENUMS.Estado;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(
        name = "pedidos",
        description = "Operaciones para el manejo de pedidos"
)
@SecurityRequirement(name = "Bearer Authentication")
@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @Operation(
            summary = "Pedidos por cliente",
            description = "Muestra todos los pedidos de un cliente"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Los pedidos fueron obtenidos exitosamente"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "No tienes permitido ver los pedidos de otro cliente"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "No autenticado"
            )
    })
    @GetMapping("/all/{id}")
    public ResponseEntity<List<Order>> showOrders(@PathVariable long id, Authentication authentication) {
        return ResponseEntity.ok(service.showOrders(id, authentication));
    }

    @Operation(
            summary = "Entregar pedido",
            description = "Marca un pedido como entregado"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "El pedido fue entregado exitosamente"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "No autenticado"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "No tienes permiso de administrador"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "El pedido no fue encontrado"
            )
    })
    @PutMapping("/delivered/{id}")
    public ResponseEntity<Order> delivered(@PathVariable long id){
        return ResponseEntity.ok(service.delivered(id));
    }

    @Operation(
            summary = " Pedidos por estado",
            description = "Muestra todos los pedidos por un estado"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Los pedidos fueron obtenidos exitosamente"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "No autenticado"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "No tienes permiso de administrador"
            )
    })
    @GetMapping("/state/{state}")
    public ResponseEntity<List<Order>> showByState(@PathVariable Estado state){
        return ResponseEntity.ok(service.showByState(state));
    }

    @Operation(
            summary = "Cancelar pedido",
            description = "Permite la cancelacion de un pedido"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "El pedido fue cancelado exitosamente"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "No autenticado"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "El pedido no fue encontrado"
            )
    })
    @PutMapping("/cancel/{id}")
    public ResponseEntity<Order> cancel(@PathVariable long id, Authentication authentication){
        return ResponseEntity.ok(service.cancel(id, authentication));
    }

    @Operation(
            summary = "Confirmar pedido",
            description = "Marca un pedido como confirmado"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "El pedido fue confirmado exitosamente"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "No autenticado"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "No tienes permiso de administrador"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "El pedido no fue encontrado"
            )
    })
    @PutMapping("confirm/{id}")
    public ResponseEntity<Order> confirm(@PathVariable long id){
        return ResponseEntity.ok(service.confirm(id));
    }

    @Operation(
            summary = "Crear pedido",
            description = "Permite la creacion de un pedido"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "El pedido fue creado exitosamente"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "No autenticado"
            )
    })
    @PostMapping("/new")
    public ResponseEntity<Order> create(@RequestBody  Order order, Authentication authentication){
        return ResponseEntity.ok(service.create(order, authentication));
    }

}
