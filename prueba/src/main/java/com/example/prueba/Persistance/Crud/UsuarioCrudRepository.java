package com.example.prueba.Persistance.Crud;

import com.example.prueba.Persistance.Entity.Usuario;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UsuarioCrudRepository extends CrudRepository<Usuario,Long> {
    Optional<Usuario> findById(long id);
}
