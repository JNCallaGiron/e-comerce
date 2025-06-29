package com.jairo.spring_ecomerce.model;

import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DetalleOrden {

    private Long id;
    private String nombre;
    private double cantidad;
    private double precio;
    private double total;

}
