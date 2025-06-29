package com.jairo.spring_ecomerce.model;

import jakarta.persistence.*;
import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name= "productos")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String descripcion;
    private String imagen; //url de la imagen
    private double precio;
    private int cantidad;

    //relacion a 1 usuario
    @ManyToOne
    private Usuario usuario;

}
