package com.example.prueba.Persistance.Mapper;

import com.example.prueba.Domain.DTO.OrderDetail;
import com.example.prueba.Persistance.Entity.DetallePedido;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", uses = {ProductoMapper.class})
public interface DetallePedidoMapper {

    @Mappings({
            @Mapping(source = "producto", target = "product"),
            @Mapping(source = "cantidad", target = "quantity"),
            @Mapping(source = "precioUnitario", target = "unitPrice"),
    })
    OrderDetail toOrderDetail(DetallePedido detallePedido);

    @InheritInverseConfiguration
    DetallePedido toDetallePedido(OrderDetail orderDetail);
}
