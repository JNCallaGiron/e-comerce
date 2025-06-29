package com.jairo.spring_ecomerce.model;

import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Producto {

    private Long id;
    private String nombre;
    private String descripcion;
    private String imagen; //url de la imagen
    private double precio;
    private int cantidad;
}
