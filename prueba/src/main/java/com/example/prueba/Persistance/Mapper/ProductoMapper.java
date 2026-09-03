package com.example.prueba.Persistance.Mapper;

import com.example.prueba.Domain.DTO.Product;
import com.example.prueba.Persistance.Entity.Producto;

import java.util.List;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;


@Mapper(componentModel = "spring")
public interface ProductoMapper {

   @Mappings({
            @Mapping(source = "nombre", target = "name"),
            @Mapping(source = "descripcion", target = "description"),
            @Mapping(source = "categoria", target = "category"),
            @Mapping(source = "precio", target = "price"),
            @Mapping(source = "disponible", target = "available")
    })
    Product toProduct(Producto producto);

    @InheritInverseConfiguration
    Producto toProducto(Product product);

    List<Product> toProducts(List<Producto> productos);
    List<Producto> toProductos(List<Product> produts);


}
