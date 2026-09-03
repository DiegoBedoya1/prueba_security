package com.example.prueba;

import com.example.prueba.Domain.DTO.Order;
import com.example.prueba.Domain.Repository.OrderRepository;
import com.example.prueba.ENUMS.Estado;
import com.example.prueba.Persistance.Crud.PedidoCrudRepository;
import com.example.prueba.Persistance.Crud.ProductoCrudRepository;
import com.example.prueba.Persistance.Crud.UsuarioCrudRepository;
import com.example.prueba.Persistance.Entity.DetallePedido;
import com.example.prueba.Persistance.Entity.Pedido;
import com.example.prueba.Persistance.Entity.Producto;
import com.example.prueba.Persistance.Entity.Usuario;
import com.example.prueba.Persistance.Mapper.PedidoMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PedidoRepository implements OrderRepository {
    private final PedidoCrudRepository crud;
    private final PedidoMapper mapper;
    private final UsuarioCrudRepository crudUser;
    private final ProductoCrudRepository crudProdu;

    public PedidoRepository(PedidoCrudRepository crud, PedidoMapper mapper, UsuarioCrudRepository crudUser, ProductoCrudRepository crudProdu) {
        this.crud = crud;
        this.mapper = mapper;
        this.crudUser = crudUser;
        this.crudProdu = crudProdu;
    }

    @Override
    public Order create(Order order){
        return null;
    }

    @Override
    public List<Order> showOrders(long id){
        if(crudUser.findById(id).isEmpty()){
            throw new RuntimeException("El cliente no existe");
        }
        return mapper.toOrders(crud.findAllByUsuarioId(id));
    }

    @Override
    public Order delivered(long id){
        Pedido pedido = crud.findById(id)
                .orElseThrow(()-> new RuntimeException("La orden no existe"));
        if(!pedido.getEstado().equals(Estado.CONFIRMADO)){
            throw new RuntimeException("Un pedido puede ser marcado como entragado solo si previamente estaba confirmado");
        }
        pedido.setEstado(Estado.ENTREGADO);
        Pedido guardado = crud.save(pedido);
        return mapper.toOrder(guardado);
    }

    @Override
    public List<Order> showByState(Estado state){
        return mapper.toOrders(crud.findAllByEstado(state));
    }

    @Override
    public Order cancel(long id){
        Pedido pedido = crud.findById(id)
                .orElseThrow(() -> new RuntimeException("El pedido no existe"));
        if(pedido.getEstado().equals(Estado.ENTREGADO) || pedido.getEstado().equals(Estado.CANCELADO)){
            throw new RuntimeException("No se puede cancelar un pedido que este entregado o que ya este cancelado");
        }
        pedido.setEstado(Estado.CANCELADO);
        Pedido guardado = crud.save(pedido);
        return mapper.toOrder(guardado);
    }

    @Override
    public Order confirm(long id){
        Pedido pedido = crud.findById(id)
                .orElseThrow(() -> new RuntimeException("El pedido no existe"));
        if(!pedido.getEstado().equals(Estado.PENDIENTE)){
            throw new RuntimeException("El pedido debe de estar pendiente");
        }
        for(DetallePedido detalle: pedido.getDetalles()){
            Producto producto = detalle.getProducto();
            if(producto.getStock()<0){
                throw new RuntimeException("el producto "+producto.getNombre() + " no cuenta con stock");
            }
            producto.setStock(producto.getStock() - detalle.getCantidad());
            crudProdu.save(producto);
        }
        pedido.setEstado(Estado.CONFIRMADO);
        Pedido guardado = crud.save(pedido);
        return mapper.toOrder(guardado);
    }
}
