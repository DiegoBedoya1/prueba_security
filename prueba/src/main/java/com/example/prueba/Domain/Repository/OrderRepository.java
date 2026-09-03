package com.example.prueba.Domain.Repository;

import com.example.prueba.Domain.DTO.Order;
import com.example.prueba.ENUMS.Estado;

import java.util.List;

public interface OrderRepository {
    Order create(Order order);
    List<Order> showOrders(long id);
    Order delivered(long id);
    List<Order> showByState(Estado state);
    Order cancel(long id);
    Order confirm(long id);
}
