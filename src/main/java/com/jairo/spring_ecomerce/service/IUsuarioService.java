package com.jairo.spring_ecomerce.service;

import com.jairo.spring_ecomerce.model.Usuario;

import java.util.Optional;

public interface IUsuarioService {
    Optional<Usuario> findUsuario(Long id);
    Usuario saveUsuario(Usuario usuario);
}
