package com.jairo.spring_ecomerce.service;
import com.jairo.spring_ecomerce.model.Usuario;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;


import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

    @Service
    public class UserDetailServiceImpl implements UserDetailsService {

        @Autowired
        private IUsuarioService usuarioService;

        @Autowired
        private HttpSession session;

        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            Optional<Usuario> optionalUser = usuarioService.findByEmail(username);

            if (optionalUser.isPresent()) {
                Usuario usuario = optionalUser.get();
                session.setAttribute("idUsuario", usuario.getId());

                String role = usuario.getTipo();
                if (!role.startsWith("ROLE_")) {
                    role = "ROLE_" + role;
                }

                return User.builder()
                        .username(usuario.getEmail())
                        .password(usuario.getPassword())
                        .authorities(role)
                        .build();
            } else {
                throw new UsernameNotFoundException("Usuario no encontrado");
            }
        }


    }


