package com.jairo.spring_ecomerce.service;

import com.jairo.spring_ecomerce.model.Usuario;
import com.jairo.spring_ecomerce.repository.IUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService implements IUsuarioService {

   @Autowired
   private IUsuarioRepository usuarioRepository;

    @Override
    public Optional<Usuario> findUsuario(Long id) {
        return usuarioRepository.findById(id);
    }

    @Override
    public Usuario saveUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }
}
