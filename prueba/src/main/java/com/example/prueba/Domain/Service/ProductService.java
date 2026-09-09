package com.example.prueba.Domain.Service;

import com.example.prueba.Domain.DTO.Product;
import com.example.prueba.Domain.Repository.ProductRepository;
import com.example.prueba.ENUMS.Categoria;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository repo;

    public ProductService(ProductRepository repo) {
        this.repo = repo;
    }

    public Product updateProduct(long id, Product product){
        return repo.update(id,product);
    }

    public List<Product> showAllAvailable(){
        return repo.showAllAvailable();
    }

    public Product create(Product product){
        return repo.create(product);
    }

    public List<Product> showByCategory(Categoria categoria){
        return repo.showByCategory(categoria);
    }
}
