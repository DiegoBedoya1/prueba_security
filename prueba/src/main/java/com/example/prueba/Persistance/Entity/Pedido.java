package com.example.prueba.Persistance.Entity;

import com.example.prueba.ENUMS.Estado;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "pedido")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;
    @NotNull
    private LocalDateTime fechaPedido;
    @NotNull
    private Double total;
    @NotNull
    private Estado estado;
}
