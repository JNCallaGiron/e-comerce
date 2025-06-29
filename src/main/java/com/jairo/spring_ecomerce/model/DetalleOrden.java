package com.jairo.spring_ecomerce.model;

import jakarta.persistence.*;
import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "detalles")
public class DetalleOrden {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private double cantidad;
    private double precio;
    private double total;


    //relacion con orden
    @OneToOne
    private Orden orden;

    //relacion con producto unidireccional
    @ManyToOne
    private Producto producto;
}
