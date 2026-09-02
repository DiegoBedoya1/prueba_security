package com.example.prueba.Persistance.Crud;

import com.example.prueba.Persistance.Entity.Producto;

import java.util.List;
import java.util.Optional;

public interface ProductoCrudRepository extends CrudRepository<Producto,Long>{
    Optional<Producto> findById(Long id);
    List<Producto> findAllByEstado(Boolean estado);
}
