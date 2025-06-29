package com.jairo.spring_ecomerce.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name ="ordenes")
public class Orden {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String numero;
    private Date fechaCreacion;
    private Date fechaRecibida;
    private double total; //total de orden

    //relacion de orden con usuario
    @ManyToOne
    private Usuario usuario;

    //relacion con detalle
    @OneToOne(mappedBy = "orden")
    private DetalleOrden detalle;
}
