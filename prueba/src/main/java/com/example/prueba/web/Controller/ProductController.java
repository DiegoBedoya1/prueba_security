package com.example.prueba.web.Controller;

import com.example.prueba.Domain.DTO.Product;
import com.example.prueba.Domain.Service.ProductService;
import com.example.prueba.ENUMS.Categoria;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(
        name = "productos",
        description = "Operaciones para el manejo de productos"
)
@SecurityRequirement(name = "Bearer Authentication")
@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @Operation(
            summary = "Actualizar producto",
            description = "Permite la actualizacion de un producto"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "El producto fue actualizado exitosamente"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Formato no valido"
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
                    description = "El producto no fue encontrado"
            )
    })
    @PutMapping("/update/{id}")
    public ResponseEntity<Product> updateProduct(@RequestBody Product product, @PathVariable long id){
        return ResponseEntity.ok(service.updateProduct(id,product));
    }

    @Operation(
            summary = "Muestra productos",
            description = "Muestra todos los productos disponibles"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Los productos fueron obtenidos exitosamente"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "No autenticado"
            )
    })
    @GetMapping("/availables")
    public ResponseEntity<List<Product>> showAvailables(){
        return ResponseEntity.ok(service.showAllAvailable());
    }

    @Operation(
            summary = "Crear producto",
            description = "Permite la creacion de un producto"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "El producto fue creado exitosamente"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Formato no valido"
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
    @PostMapping("/new")
    public ResponseEntity<Product> create(@RequestBody Product product){
        return ResponseEntity.ok(service.create(product));
    }

    @Operation(
            summary = "Muestra productos por categoria",
            description = "Muestra todos los productos de una  categoria seleccionada"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Los productos fueron obtenidos exitosamente"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "No autenticado"
            ),
    })
    @GetMapping("/all/{category}")
    public ResponseEntity<List<Product>> showByCategory(@PathVariable Categoria category){
        return ResponseEntity.ok(service.showByCategory(category));
    }
}