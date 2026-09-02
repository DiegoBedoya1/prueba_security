package com.example.prueba.Domain.Repository;

import com.example.prueba.Domain.DTO.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    Product update(long id, Product product);
    List<Product> showAllAvailable();
}
