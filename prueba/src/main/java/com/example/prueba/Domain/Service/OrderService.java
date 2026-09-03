package com.example.prueba.Domain.Service;

import com.example.prueba.Domain.DTO.Order;
import com.example.prueba.Domain.Repository.OrderRepository;
import com.example.prueba.ENUMS.Estado;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    private final OrderRepository repo;

    public OrderService(OrderRepository repo) {
        this.repo = repo;
    }

    public List<Order> showOrders(long id){
        return repo.showOrders(id);
    }

    public Order delivered(long id){
        return repo.delivered(id);
    }

    public List<Order> showByState(Estado state){
        return repo.showByState(state);
    }

    public Order cancel(long id){
        return repo.cancel(id);
    }

    public Order confirm(long id){
        return repo.confirm(id);
    }
}
