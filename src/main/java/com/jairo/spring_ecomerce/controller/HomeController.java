package com.jairo.spring_ecomerce.controller;

import com.jairo.spring_ecomerce.service.IProductoService;
import jakarta.persistence.Id;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
@Slf4j
public class HomeController {

    @Autowired
    private IProductoService productoService;

    @GetMapping("")
    public String home(Model model){
        model.addAttribute("productos", productoService.listProductos());
        return "usuario/home";
    }

    //de ver producto a una nueva vista
    @GetMapping("/productohome/{id}")
    public String productoHome(@PathVariable Long id){
        log.info("Id  producto enviado como parametro {}", id);
        return "usuario/productohome";
    }
}
