package com.example.prueba.Persistance.Mapper;

import com.example.prueba.Domain.DTO.Order;
import com.example.prueba.Persistance.Entity.Pedido;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring", uses = {UsuarioMapper.class,  DetallePedidoMapper.class})
public interface PedidoMapper {

    @Mappings({
            @Mapping(source = "usuario", target = "user"),
            @Mapping(source = "fechaPedido", target = "orderDate"),
            @Mapping(source = "estado", target = "state"),
            @Mapping(source = "detalles", target = "details")
    })
    Order toOrder(Pedido pedido);

    @InheritInverseConfiguration
    Pedido toPedido(Order order);

    List<Order> toOrders(List<Pedido> pedidos);
    List<Pedido> toPedidos(List<Order> orders);


}
