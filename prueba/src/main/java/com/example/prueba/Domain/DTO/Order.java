package com.example.prueba.Domain.DTO;

import com.example.prueba.ENUMS.Estado;

import java.time.LocalDateTime;

public class Order {
    private Long id;
    private User user;
    private LocalDateTime orderDate;
    private Double total;
    private Estado state;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Estado getState() {
        return state;
    }

    public void setState(Estado state) {
        this.state = state;
    }
}
