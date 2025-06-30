package com.jairo.spring_ecomerce.controller;

import com.jairo.spring_ecomerce.model.Producto;
import com.jairo.spring_ecomerce.model.Usuario;
import com.jairo.spring_ecomerce.service.ProductoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@Slf4j
@RequestMapping("/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping("")
    public String show(Model model){
        model.addAttribute("productos",productoService.listProductos());
        return "productos/show";
    }

    @GetMapping("/create")
    public String create(){
        return "productos/create";
    }

    @PostMapping("/save")
    public String save(Producto producto){
        log.info("Este es el objeto producto {}", producto);
        Usuario u = new Usuario(1L,"","","","","","","",null,null);
        producto.setUsuario(u);
        productoService.saveProdcuto(producto);
        return "redirect:/productos";
    }


    //metodos crud
}
