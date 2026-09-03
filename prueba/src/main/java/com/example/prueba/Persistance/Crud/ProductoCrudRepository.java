package com.example.prueba.Persistance.Crud;

import com.example.prueba.Persistance.Entity.Producto;
import org.springframework.data.repository.CrudRepository;
import java.util.List;
import java.util.Optional;

public interface ProductoCrudRepository extends CrudRepository<Producto,Long>{
    Optional<Producto> findById(long id);
    List<Producto> findAllByDisponible(boolean disponible);
}
