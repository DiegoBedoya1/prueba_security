package com.example.prueba;

import com.example.prueba.Domain.DTO.Order;
import com.example.prueba.Domain.DTO.OrderDetail;
import com.example.prueba.Domain.DTO.Product;
import com.example.prueba.Domain.DTO.User;
import com.example.prueba.ENUMS.Categoria;
import com.example.prueba.ENUMS.Estado;
import com.example.prueba.PedidoRepository;
import com.example.prueba.Persistance.Crud.PedidoCrudRepository;
import com.example.prueba.Persistance.Crud.ProductoCrudRepository;
import com.example.prueba.Persistance.Crud.UsuarioCrudRepository;
import com.example.prueba.Persistance.Entity.DetallePedido;
import com.example.prueba.Persistance.Entity.Pedido;
import com.example.prueba.Persistance.Entity.Producto;
import com.example.prueba.Persistance.Entity.Usuario;
import com.example.prueba.Persistance.Mapper.DetallePedidoMapper;
import com.example.prueba.Persistance.Mapper.PedidoMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PedidoRepositoryTest {
    @Mock
    private PedidoCrudRepository crud;

    @Mock
    private PedidoMapper mapper;

    @Mock
    private UsuarioCrudRepository crudU;

    @Mock
    private ProductoCrudRepository crudP;

    @Mock
    private DetallePedidoMapper mapperDP;

    @InjectMocks
    private PedidoRepository repo;

    @Test
    void CrearPedidoCorrectamente(){
        Usuario usuario = new Usuario();
        usuario.setId(9L);

        Producto producto = new Producto();
        producto.setId(1L);
        producto.setPrecio(5.0);
        producto.setStock(10);
        producto.setDisponible(true);

        User user = new User(9L, "Juan", "juan@gmail.com", "099999", "password", null);

        Product product = new Product(1L, "Pan", "Pan artesanal", Categoria.ENTRADA, 5.0, 10,true);

        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setProduct(product);
        orderDetail.setQuantity(2);

        Order order = new Order();
        order.setUser(user);
        order.setDetails(List.of(orderDetail));

        DetallePedido detalle = new DetallePedido();
        detalle.setProducto(producto);
        detalle.setCantidad(2);

        Pedido guardado = new Pedido();
        Order resultado = new Order();
        resultado.setTotal(10.0);
        resultado.setState(Estado.PENDIENTE);

        when(crudU.findById(9L))
                .thenReturn(Optional.of(usuario));

        when(mapperDP.toDetallePedidos(any()))
                .thenReturn(List.of(detalle));

        when(crudP.findById(1L))
                .thenReturn(Optional.of(producto));

        when(crud.save(any(Pedido.class)))
                .thenReturn(guardado);

        when(mapper.toOrder(guardado))
                .thenReturn(resultado);

        Order respuesta = repo.create(order);

        assertNotNull(respuesta);
        assertEquals(10.0, respuesta.getTotal());
        assertEquals(Estado.PENDIENTE, respuesta.getState());

        verify(crud).save(any(Pedido.class));
    }

    @Test
    void ProductoInexistente(){
        Usuario usuario = new Usuario();
        usuario.setId(9L);

        User user = new User(9L, "juan", "juan@gmail.com", "099999", "password", null);

        Product product = new Product(99L, "nada", "no hay no existe", Categoria.ENTRADA, 5.0, 10, true);

        OrderDetail detail = new OrderDetail();
        detail.setProduct(product);
        detail.setQuantity(2);

        Order order = new Order();
        order.setDetails(List.of(detail));
        order.setUser(user);

        DetallePedido detalle = new DetallePedido();
        detalle.setProducto(new Producto());
        detalle.getProducto().setId(99L);
        detalle.setCantidad(2);

        when(crudU.findById(9L))
                .thenReturn(Optional.of(usuario));
        when(mapperDP.toDetallePedidos(any()))
                .thenReturn(List.of(detalle));

        when(crudP.findById(99L))
                .thenReturn(Optional.empty());

        RuntimeException e = assertThrows(
                RuntimeException.class,
                () -> repo.create(order)
        );
        assertEquals("El producto no existe", e.getMessage());
        verify(crud,never()).save(any(Pedido.class));
    }

    @Test
    void ProductoNoDisponible(){
        Usuario usuario = new Usuario();
        usuario.setId(9L);

        User user = new User(9L, "juan", "juan@gmail.com", "0999999", "hola1", null);

        Producto producto = new Producto();
        producto.setId(1L);
        producto.setPrecio(5.0);
        producto.setStock(10);
        producto.setDisponible(false);

        Product product = new Product(1L, "pan", "pan", Categoria.ENTRADA,5.0, 10, false);

        OrderDetail detail = new OrderDetail();
        detail.setProduct(product);
        detail.setQuantity(3);

        Order order = new Order();
        order.setUser(user);
        order.setDetails(List.of(detail));

        DetallePedido detalle = new DetallePedido();
        detalle.setProducto(producto);
        detalle.setCantidad(3);

        when(crudU.findById(9L))
                .thenReturn(Optional.of(usuario));

        when(mapperDP.toDetallePedidos(any()))
                .thenReturn(List.of(detalle));

        when(crudP.findById(1L))
                .thenReturn(Optional.of(producto));

        RuntimeException e = assertThrows(
                RuntimeException.class,
                () -> repo.create(order));

        assertEquals("El producto no esta disponible", e.getMessage());

        verify(crud,never()).save(any(Pedido.class));
    }

    @Test
    void StockInsuficiente(){
        Usuario usuario = new Usuario();
        usuario.setId(9L);

        User user = new User(9L, "juan", "juan@gmail.com", "0999999", "hola1", null);

        Producto producto = new Producto();
        producto.setId(1L);
        producto.setPrecio(5.0);
        producto.setStock(5);
        producto.setDisponible(true);

        Product product = new Product(1L, "pan", "pan", Categoria.ENTRADA,5.0, 5, true);

        OrderDetail detail = new OrderDetail();
        detail.setProduct(product);
        detail.setQuantity(6);

        Order order = new Order();
        order.setUser(user);
        order.setDetails(List.of(detail));

        DetallePedido detalle = new DetallePedido();
        detalle.setProducto(producto);
        detalle.setCantidad(6);

        when(crudU.findById(9L))
                .thenReturn(Optional.of(usuario));

        when(mapperDP.toDetallePedidos(any()))
                .thenReturn(List.of(detalle));

        when(crudP.findById(1L))
                .thenReturn(Optional.of(producto));

        RuntimeException e = assertThrows(
                RuntimeException.class,
                () -> repo.create(order));

        assertEquals("No hay suficiente stock para este producto", e.getMessage());

        verify(crud,never()).save(any(Pedido.class));
    }

    @Test
    void CalculoCorrectoSubtotal(){
        Usuario usuario = new Usuario();
        usuario.setId(9L);

        User user = new User(9L, "juan", "juan@gmail.com", "0999999", "hola1", null);

        Producto producto = new Producto();
        producto.setId(1L);
        producto.setPrecio(5.0);
        producto.setStock(10);
        producto.setDisponible(true);

        Product product = new Product(1L, "pan", "pan", Categoria.ENTRADA,5.0, 10, true);

        OrderDetail detail = new OrderDetail();
        detail.setProduct(product);
        detail.setQuantity(3);

        Order order = new Order();
        order.setUser(user);
        order.setDetails(List.of(detail));

        DetallePedido detalle = new DetallePedido();
        detalle.setProducto(producto);
        detalle.setCantidad(3);

        Pedido guardado = new Pedido();

        when(crudU.findById(9L))
                .thenReturn(Optional.of(usuario));

        when(mapperDP.toDetallePedidos(any()))
                .thenReturn(List.of(detalle));

        when(crudP.findById(1L))
                .thenReturn(Optional.of(producto));

        when(crud.save(any(Pedido.class)))
                .thenAnswer(invocation -> {
                    Pedido pedido = invocation.getArgument(0);
                    assertEquals(15.0,pedido.getDetalles().getFirst().getSubtotal());
                    return guardado;
                });

        when(mapper.toOrder(guardado))
                .thenReturn(new Order());
        repo.create(order);
        verify(crud).save(any(Pedido.class));
    }

    @Test
    void CalculoCorrectoTotal(){
        Usuario usuario = new Usuario();
        usuario.setId(9L);

        User user = new User(9L, "juan", "juan@gmail.com", "0999999", "hola1", null);

        Producto producto1 = new Producto();
        producto1.setId(1L);
        producto1.setPrecio(5.0);
        producto1.setStock(10);
        producto1.setDisponible(true);

        Producto producto2 = new Producto();
        producto2.setId(2L);
        producto2.setPrecio(3.0);
        producto2.setStock(10);
        producto2.setDisponible(true);

        Product product1 = new Product(1L, "pan", "pan", Categoria.ENTRADA,5.0, 10, true);

        Product product2 = new Product(2L, "pan", "pan", Categoria.ENTRADA,3.0, 10, true);


        OrderDetail detail1 = new OrderDetail();
        detail1.setProduct(product1);
        detail1.setQuantity(3);

        OrderDetail detail2 = new OrderDetail();
        detail2.setProduct(product2);
        detail2.setQuantity(3);

        Order order = new Order();
        order.setUser(user);
        order.setDetails(List.of(detail1,detail2));

        DetallePedido detalle1 = new DetallePedido();
        detalle1.setProducto(producto1);
        detalle1.setCantidad(3);

        DetallePedido detalle2 = new DetallePedido();
        detalle2.setProducto(producto2);
        detalle2.setCantidad(3);

        Pedido guardado = new Pedido();

        when(crudU.findById(9L))
                .thenReturn(Optional.of(usuario));

        when(mapperDP.toDetallePedidos(any()))
                .thenReturn(List.of(detalle1, detalle2));

        when(crudP.findById(1L))
                .thenReturn(Optional.of(producto1));

        when(crudP.findById(2L))
                .thenReturn(Optional.of(producto2));

        when(crud.save(any(Pedido.class)))
                .thenAnswer(invocation -> {
                    Pedido pedido = invocation.getArgument(0);
                    assertEquals(24.0,pedido.getTotal());
                    return guardado;
                });

        when(mapper.toOrder(guardado))
                .thenReturn(new Order());
        repo.create(order);
        verify(crud).save(any(Pedido.class));
    }

    @Test
    void ConfirmacionPedido(){
        Producto producto = new Producto();
        producto.setPrecio(5.0);
        producto.setStock(10);
        producto.setDisponible(true);

        DetallePedido detalle = new DetallePedido();
        detalle.setProducto(producto);
        detalle.setCantidad(3);

        Pedido pedido = new Pedido();
        pedido.setEstado(Estado.PENDIENTE);
        pedido.setDetalles(List.of(detalle));

        Pedido guardado = pedido;

        when(crud.findById(1L))
                .thenReturn(Optional.of(pedido));

        when(crud.save(pedido))
                .thenReturn(guardado);

        when(mapper.toOrder(guardado))
                .thenReturn(new Order());

        repo.confirm(1L);
        assertEquals(7,producto.getStock());
        assertEquals(Estado.CONFIRMADO, pedido.getEstado());

        verify(crudP).save(producto);
        verify(crud).save(guardado);
    }

    @Test
    void CancelacionPedido(){
        Producto producto = new Producto();
        producto.setPrecio(5.0);
        producto.setStock(10);
        producto.setDisponible(true);

        DetallePedido detalle = new DetallePedido();
        detalle.setProducto(producto);
        detalle.setCantidad(3);

        Pedido pedido = new Pedido();
        pedido.setEstado(Estado.CONFIRMADO);
        pedido.setDetalles(List.of(detalle));

        when(crud.findById(1L))
                .thenReturn(Optional.of(pedido));

        when(crud.save(pedido))
                .thenReturn(pedido);

        when(mapper.toOrder(pedido))
                .thenReturn(new Order());

        repo.cancel(1L);

        assertEquals(13, producto.getStock());
        assertEquals(Estado.CANCELADO, pedido.getEstado());

        verify(crudP).save(producto);
        verify(crud).save(pedido);
    }

    @Test
    void CancelarPedidoEntregado(){
        Pedido pedido = new Pedido();
        pedido.setEstado(Estado.ENTREGADO);

        when(crud.findById(1L))
                .thenReturn(Optional.of(pedido));
        RuntimeException e = assertThrows(
                RuntimeException.class,
                () -> repo.cancel(1L)
        );
        assertEquals("No se puede cancelar un pedido que este entregado o que ya este cancelado", e.getMessage());
        verify(crud,never()).save(any(Pedido.class));
    }


}

