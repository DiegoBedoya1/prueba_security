package com.example.prueba;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.example.prueba.Domain.Repository.OrderRepository;
import com.example.prueba.Persistance.Crud.UsuarioCrudRepository;
import com.example.prueba.Persistance.Crud.PedidoCrudRepository;
import com.example.prueba.Domain.Service.OrderService;
import com.example.prueba.Persistance.Entity.Usuario;
import com.example.prueba.Persistance.Entity.Rol;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {
    @Mock
    private  OrderRepository repo;

     @Mock
     private  UsuarioCrudRepository crudU;

     @InjectMocks
     private OrderService service;

     @Test
    void ClienteConsultaPedidoOtroUsuario(){
         Usuario usuario = new Usuario();
         usuario.setId(1L);

         Rol rol = new Rol();
         rol.setNombre("CLIENTE");
         usuario.setRol(rol);

         Authentication authentication = new UsernamePasswordAuthenticationToken("hola@gmail.com", null);
         when(crudU.findByCorreo(authentication.getName()))
                 .thenReturn(Optional.of(usuario));

         RuntimeException e = assertThrows(
                 RuntimeException.class,
                 () -> service.showOrders(15L, authentication)
         );
         assertEquals("No tienes permiso para consultar pedidos de este usuario", e.getMessage());
         verify(repo, never()).showOrders(15L);
     }
}
