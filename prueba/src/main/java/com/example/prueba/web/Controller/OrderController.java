package com.example.prueba.web.Controller;

import com.example.prueba.Domain.DTO.Order;
import com.example.prueba.Domain.Service.OrderService;
import com.example.prueba.ENUMS.Estado;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @GetMapping("/all/{id}")
    public ResponseEntity<List<Order>> showOrders(@PathVariable long id){
        return ResponseEntity.ok(service.showOrders(id));
    }

    @PutMapping("/delivered/{id}")
    public ResponseEntity<Order> delivered(@PathVariable long id){
        return ResponseEntity.ok(service.delivered(id));
    }

    @GetMapping("/state/{state}")
    public ResponseEntity<List<Order>> showByState(@PathVariable Estado state){
        return ResponseEntity.ok(service.showByState(state));
    }

    @PutMapping("/cancel/{id}")
    public ResponseEntity<Order> cancel(@PathVariable long id){
        return ResponseEntity.ok(service.cancel(id));
    }

    @PutMapping("confirm/{id}")
    public ResponseEntity<Order> confirm(@PathVariable long id){
        return ResponseEntity.ok(service.confirm(id));
    }

    @PostMapping("/new")
    public ResponseEntity<Order> create(@RequestBody  Order order){
        return ResponseEntity.ok(service.create(order));
    }

}
