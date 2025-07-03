package com.jairo.spring_ecomerce.service;

import com.jairo.spring_ecomerce.model.Orden;

import java.util.List;

public interface IOrdenService {
    List<Orden> listOrden();
    Orden orden(Orden orden);

}
