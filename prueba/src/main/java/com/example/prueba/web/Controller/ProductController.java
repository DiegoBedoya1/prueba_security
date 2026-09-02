package com.example.prueba.web.Controller;

import com.example.prueba.Domain.DTO.Product;
import com.example.prueba.Domain.Service.ProductService;

import java.util.List;

//@RestController
//@RequestMapping("/products")
public class ProductController {
    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    //@PutMapping("/update/{i}")
    public ResponseEntity<Product>  updateProduct(@RequestBody Product product, @PathVariable long id){
        return ResponseEntity.ok(service.updateProduct(id,product));
    }

    @GetMapping("/availables")
    public ResponseEntity<List<Product>> showAvailables(){
        return ResponseEntity.ok(service.showAllAvailable());
    }
}
