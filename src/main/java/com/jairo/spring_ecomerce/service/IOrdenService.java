package com.jairo.spring_ecomerce.service;

import com.jairo.spring_ecomerce.model.Orden;
import com.jairo.spring_ecomerce.model.Usuario;

import java.util.List;
import java.util.Optional;

public interface IOrdenService {
    List<Orden> listOrden();
    Orden saveOrden(Orden orden);
    public String generarNumeroOrden();
    List<Orden> findByUsuario   (Usuario usuario);
    Optional<Orden> findById(Long id);
}
