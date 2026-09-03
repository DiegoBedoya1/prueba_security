package com.example.prueba.Persistance.Crud;

import com.example.prueba.ENUMS.Estado;
import com.example.prueba.Persistance.Entity.Pedido;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface PedidoCrudRepository extends CrudRepository<Pedido,Long> {
    Optional<Pedido> findById(long id);
    List<Pedido> findAllByUsuarioId(long id);
    List<Pedido> findAllByEstado(Estado estado);
}
