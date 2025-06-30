package com.jairo.spring_ecomerce.model;


import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"productos", "ordenes"})
@Entity
@Table(name = "usuarios")  // recomendable usar nombre de tablas en plural
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String userName;
    private String email;
    private String direccion;
    private String telefono;
    private String tipo; //tipo de usuario
    private String password;

    //1 a n, bidireccional
    @OneToMany(mappedBy = "usuario")
    private List<Producto> productos;

    //lista de ordenes para un usuario
    @OneToMany(mappedBy = "usuario")
    private List<Orden> ordenes;



}
