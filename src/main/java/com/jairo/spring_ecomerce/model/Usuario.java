package com.jairo.spring_ecomerce.model;


import lombok.*;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Usuario {

    private Long id;
    private String nombre;
    private String userName;
    private String email;
    private String direccion;
    private String telefono;
    private String tipo; //tipo de usuario
    private String password;
}
