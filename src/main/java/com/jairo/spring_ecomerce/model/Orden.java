package com.jairo.spring_ecomerce.model;

import lombok.*;

import java.util.Date;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Orden {
    private Long id;
    private String numero;
    private Date fechaCreacion;
    private Date fechaRecibida;

    private double total; //total de orden
}
