package com.jairo.spring_ecomerce.repository;

import com.jairo.spring_ecomerce.model.DetalleOrden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IDetalleOrdenRepository extends JpaRepository<DetalleOrden, Long> {
}
