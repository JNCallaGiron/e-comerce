package com.jairo.spring_ecomerce.repository;

import com.jairo.spring_ecomerce.model.Orden;
import com.jairo.spring_ecomerce.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IOrdenRepository extends JpaRepository<Orden, Long> {
    List<Orden> findByUsuario   (Usuario usuario);
}

