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
import com.example.prueba.Persistance.Mapper.DetallePedidoMapper;
import com.example.prueba.Persistance.Mapper.PedidoMapper;
import com.example.prueba.Persistance.Mapper.UsuarioMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class PedidoRepository implements OrderRepository {
    private final PedidoCrudRepository crud;
    private final PedidoMapper mapper;
    private final UsuarioCrudRepository crudUser;
    private final ProductoCrudRepository crudProdu;
    private final DetallePedidoMapper detalleMapper;

    public PedidoRepository(PedidoCrudRepository crud, PedidoMapper mapper, UsuarioCrudRepository crudUser, ProductoCrudRepository crudProdu, DetallePedidoMapper detalleMapper) {
        this.crud = crud;
        this.mapper = mapper;
        this.crudUser = crudUser;
        this.crudProdu = crudProdu;
        this.detalleMapper = detalleMapper;
    }

    @Override
    public Order create(Order order){
        Usuario usuario = crudUser.findById(order.getUser().getId())
                .orElseThrow(() -> new RuntimeException("El usuario no existe"));
        double total = 0;
        if(order.getDetails().isEmpty()){
            throw new RuntimeException("no hay productos");
        }
        List<DetallePedido> detalles = detalleMapper.toDetallePedidos(order.getDetails());

        for(DetallePedido detalle: detalles){
            Producto producto = crudProdu.findById(detalle.getProducto().getId())
                    .orElseThrow(() -> new RuntimeException("El producto no existe"));
            if(!producto.getDisponible()){
                throw new RuntimeException("El producto no esta disponible");
            }
            if(detalle.getCantidad()<=0){
                throw new RuntimeException("La cantidad del producto debe de ser mayor a 0");
            }
            if(producto.getStock() < detalle.getCantidad()){
                throw new RuntimeException("No hay suficiente stock para este producto");
            }
            double subtotal = producto.getPrecio() * detalle.getCantidad();
            detalle.setProducto(producto);
            detalle.setPrecioUnitario(producto.getPrecio());
            detalle.setSubtotal(subtotal);
            total += subtotal;
        }
        Pedido pedido = new Pedido(usuario, LocalDateTime.now(), total, Estado.PENDIENTE, detalles);
        for(DetallePedido detalle: detalles){
            detalle.setPedido(pedido);
        }
        Pedido guardado = crud.save(pedido);
        return mapper.toOrder(guardado);
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
        if(pedido.getEstado().equals(Estado.CONFIRMADO)){
            List<DetallePedido> detalles = pedido.getDetalles();
            for(DetallePedido detalle: detalles){
                Producto producto = detalle.getProducto();
                producto.setStock(producto.getStock() + detalle.getCantidad());
                crudProdu.save(producto);
            }
            pedido.setEstado(Estado.CANCELADO);
            Pedido guardado = crud.save(pedido);
            return mapper.toOrder(guardado);
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
            producto.setStock(producto.getStock() - detalle.getCantidad());
            crudProdu.save(producto);
        }
        pedido.setEstado(Estado.CONFIRMADO);
        Pedido guardado = crud.save(pedido);
        return mapper.toOrder(guardado);
    }
}
