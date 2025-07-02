package com.jairo.spring_ecomerce.repository;

import com.jairo.spring_ecomerce.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUsuarioRepository extends JpaRepository<Usuario, Long> {
}
