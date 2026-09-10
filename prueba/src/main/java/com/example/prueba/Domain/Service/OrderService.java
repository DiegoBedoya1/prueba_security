package com.example.prueba.Domain.Service;

import com.example.prueba.Domain.DTO.Order;
import com.example.prueba.Domain.Repository.OrderRepository;
import com.example.prueba.ENUMS.Estado;
import com.example.prueba.Persistance.Crud.PedidoCrudRepository;
import com.example.prueba.Persistance.Crud.UsuarioCrudRepository;
import com.example.prueba.Persistance.Entity.Pedido;
import com.example.prueba.Persistance.Entity.Usuario;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class OrderService {
    private final OrderRepository repo;
    private final UsuarioCrudRepository crudUser;
    private final PedidoCrudRepository crudRepo;

    public OrderService(OrderRepository repo, UsuarioCrudRepository crudUser, PedidoCrudRepository crudRepo) {
        this.repo = repo;
        this.crudUser = crudUser;
        this.crudRepo = crudRepo;
    }

    public List<Order> showOrders(long id, Authentication authentication){
        Usuario usuario = crudUser.findByCorreo(authentication.getName())
                .orElseThrow(() -> new RuntimeException("usuario no encontrado"));
        String rol = usuario.getRol().getNombre();
        if(rol.equals("ADMIN")){
                return repo.showOrders(id);
        }
        if(!usuario.getId().equals(id)){
            throw new RuntimeException("No tienes permiso para consultar pedidos de este usuario");
        }
        return repo.showOrders(id);
    }

    public Order delivered(long id){
        return repo.delivered(id);
    }

    public List<Order> showByState(Estado state){
        return repo.showByState(state);
    }

    public Order cancel(long id,Authentication authentication){
        Usuario usuario = crudUser.findByCorreo(authentication.getName())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        String rol = usuario.getRol().getNombre();
        if(rol.equals("ADMIN")){
            return repo.cancel(id);
        }
        if(!isOwner(usuario.getId(), id)){
            throw new RuntimeException("No tienes permiso para cancelar este pedido");
        }
        return repo.cancel(id);
    }

    public Order confirm(long id){
        return repo.confirm(id);
    }

    public Order create(Order order, Authentication authentication){
        Usuario usuario = crudUser.findByCorreo(authentication.getName())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        order.getUser().setId(usuario.getId());
        return repo.create(order);
    }

    private boolean isOwner(long idUser, long idOrder){
        Pedido pedido = crudRepo.findById(idOrder)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));
        return pedido.getUsuario().getId().equals(idUser);
    }
}
