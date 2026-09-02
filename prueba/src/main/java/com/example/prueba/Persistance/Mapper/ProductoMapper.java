package com.example.prueba.Persistance.Mapper;

import com.example.prueba.Domain.DTO.Product;
import com.example.prueba.Persistance.Entity.Producto;

import java.util.List;

//@Mapper(componentModel = "spring")
public interface ProductoMapper {

   /* @Mappings({
            @Mapping(source = "nombre", target = "name"),
            @Mapping(source = "descripcion", target = "description"),
            @Mapping(source = "categoria", target = "category"),
            @Mapping(source = "precio", target = "price"),
            @Mapping(source = "disponible", target = "available")
    })*/
    Product toProduct(Producto producto);

    //@InheriteInverseConfiguration
    Producto toProducto(Product product);

    List<Product> toProducts(List<Producto> productos);
    List<Producto> toProductos(List<Product> produts);


}
