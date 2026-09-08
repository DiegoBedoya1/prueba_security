package com.example.prueba.Persistance.Crud;

import com.example.prueba.Persistance.Entity.Rol;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RolCrudRepository extends CrudRepository<Rol, Long> {
    Optional<Rol> findByNombre(String nombre);
}
