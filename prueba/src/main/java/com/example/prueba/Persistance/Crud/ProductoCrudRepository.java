package com.example.prueba.Persistance.Crud;

import com.example.prueba.ENUMS.Categoria;
import com.example.prueba.Persistance.Entity.Producto;
import org.springframework.data.repository.CrudRepository;
import java.util.List;
import java.util.Optional;

public interface ProductoCrudRepository extends CrudRepository<Producto,Long>{
    List<Producto> findAllByDisponible(boolean disponible);
    List<Producto> findAllByCategoria(Categoria categoria);

}
