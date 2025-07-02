package com.jairo.spring_ecomerce.repository;

import com.jairo.spring_ecomerce.model.Orden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IOrdenRepository extends JpaRepository<Orden, Long> {
}
