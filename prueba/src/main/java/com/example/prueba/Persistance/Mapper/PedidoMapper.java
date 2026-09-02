package com.example.prueba.Persistance.Mapper;

import com.example.prueba.Domain.DTO.Order;
import com.example.prueba.Persistance.Entity.Pedido;

//@Mapper(componentModel = "spring", uses = {UsuarioMapper.class})
public interface PedidoMapper {

    /*@Mappings({
            @Mapping(source = "usuario", target = "user"),
            @Mapping(source = "fechaPedido", target = "orderDate"),
            @Mapping(source = "estado", target = "state")
    })*/
    Order toOrder(Pedido pedido);

    //@InheritInverseConfiguration
    Pedido toPedido(Order order);


}
