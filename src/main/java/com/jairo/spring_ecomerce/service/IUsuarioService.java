package com.jairo.spring_ecomerce.service;

import com.jairo.spring_ecomerce.model.Usuario;

import java.util.List;
import java.util.Optional;

public interface IUsuarioService {
    List<Usuario> findAll();
    Optional<Usuario> findUsuario(Long id);
    Usuario saveUsuario(Usuario usuario);
    Optional<Usuario> findByEmail(String email);
}
