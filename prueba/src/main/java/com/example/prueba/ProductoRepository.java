package com.example.prueba;

import com.example.prueba.Domain.DTO.Product;
import com.example.prueba.Domain.Repository.ProductRepository;
import com.example.prueba.Persistance.Crud.ProductoCrudRepository;
import com.example.prueba.Persistance.Entity.Producto;
import com.example.prueba.Persistance.Mapper.ProductoMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
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
        producto.setDescripcion(product.getDescription());
        producto.setCategoria(product.getCategory());
        producto.setPrecio(product.getPrice());
        producto.setStock(product.getStock());
        producto.setDisponible(product.getAvailable());
        Producto guardado = crud.save(producto);
        return mapper.toProduct(guardado);
    }

    @Override
    public List<Product> showAllAvailable(){
        return mapper.toProducts(crud.findAllByDisponible(true));
    }

    @Override
    public Product create(Product product){
        if(product.getPrice()<=0){
            throw new RuntimeException("El precio no puede ser 0 o negativo");
        }
        if(product.getStock()<0){
            throw new RuntimeException("EL stock no puede ser negativo");
        }
        if(product.getName() == null){
            throw new RuntimeException("El nombre debe de ser obligatorio");
        }
        if(product.getCategory() == null){
            throw new RuntimeException("La categoria debe de ser obligatoria");
        }
        Product produ = new Product(product.getId(),
                product.getName(),
                product.getDescription(),
                product.getCategory(),
                product.getPrice(),
                product.getStock(),
                product.getAvailable());
        Producto guardado = crud.save(mapper.toProducto(produ));
        return mapper.toProduct(guardado);
    }
}


