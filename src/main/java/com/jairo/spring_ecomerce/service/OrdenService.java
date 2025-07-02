package com.jairo.spring_ecomerce.service;

import com.jairo.spring_ecomerce.model.Orden;
import com.jairo.spring_ecomerce.repository.IDetalleOrdenRepository;
import com.jairo.spring_ecomerce.repository.IOrdenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrdenService implements IOrdenService  {

    @Autowired
    private IOrdenRepository ordenRepository;

    @Override
    public Orden orden(Orden orden) {
        return ordenRepository.save(orden);
    }
}
