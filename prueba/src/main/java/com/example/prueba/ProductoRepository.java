package com.example.prueba;

import com.example.prueba.Domain.DTO.Product;
import com.example.prueba.Domain.Repository.ProductRepository;
import com.example.prueba.Persistance.Crud.ProductoCrudRepository;
import com.example.prueba.Persistance.Entity.Producto;
import com.example.prueba.Persistance.Mapper.ProductoMapper;

import java.util.List;
import java.util.Optional;

//@Repository
public class ProductoRepository implements ProductRepository {
    private final ProductoCrudRepository crud;
    private final ProductoMapper mapper;

    public ProductoRepository(ProductoCrudRepository crud, ProductoMapper mapper) {
        this.crud = crud;
        this.mapper = mapper;
    }

    @Override
    public Product update(long id, Product product){
        Producto producto = crud.findById(id)
                .orElseThrow(() -> new RuntimeException("producto no encontrado"));
        producto.setNombre(product.getName());
        producto.setDescripcion(product.getDespcription());
        producto.setCategoria(product.getCategory());
        producto.setPrecio(product.getPrice());
        producto.setStock(producto.getStock());
        producto.setDisponible(product.getAvailable());
        return mapper.toProduct(crud.save(producto));
    }

    @Override
    public List<Product> showAllAvailable(){
        return mapper.toProducts(crud.findAllByEstado(true));
    }
}

