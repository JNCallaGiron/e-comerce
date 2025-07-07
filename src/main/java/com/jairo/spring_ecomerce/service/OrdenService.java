package com.jairo.spring_ecomerce.service;

import com.jairo.spring_ecomerce.model.Orden;
import com.jairo.spring_ecomerce.model.Usuario;
import com.jairo.spring_ecomerce.repository.IDetalleOrdenRepository;
import com.jairo.spring_ecomerce.repository.IOrdenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrdenService implements IOrdenService  {

    @Autowired
    private IOrdenRepository ordenRepository;

    @Override
    public List<Orden> listOrden() {
        return ordenRepository.findAll();
    }

    @Override
    public Orden saveOrden(Orden orden) {
        return ordenRepository.save(orden);
    }

    @Override
    public String generarNumeroOrden(){
        int numero = 0;
        String numeroConcatenado ="";

        List<Orden> ordenes = this.listOrden();
        List<Integer> numeros = new ArrayList<Integer>();

        ordenes.stream().forEach(o -> numeros.add(Integer.parseInt(o.getNumero())));

        if(ordenes.isEmpty()){
            numero=1;
        }else {
            numero = numeros.stream().max(Integer::compare).get();
            numero++;
        }
        if(numero < 10){ //000000000 primer numero de orden
            numeroConcatenado="000000000" + String.valueOf(numero);
        }else if(numero <100){
            numeroConcatenado="00000000" + String.valueOf(numero);
        } else if (numero < 1000) {
            numeroConcatenado="0000000" + String.valueOf(numero);
        }else if (numero < 10000)
            numeroConcatenado="000000" + String.valueOf(numero);
        return numeroConcatenado;
    }

    @Override
    public List<Orden> findByUsuario(Usuario usuario) {
        return ordenRepository.findByUsuario(usuario);
    }
}
