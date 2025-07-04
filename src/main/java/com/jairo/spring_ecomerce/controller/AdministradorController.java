package com.jairo.spring_ecomerce.controller;

import com.jairo.spring_ecomerce.model.Producto;
import com.jairo.spring_ecomerce.service.IProductoService;
import com.jairo.spring_ecomerce.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
@RequestMapping("/administrador")
public class AdministradorController {

    @Autowired
    private IProductoService productoService;

    @GetMapping("")
    public String home(Model model){
        List<Producto> productos= productoService.listProductos();
        model.addAttribute("productos", productos);
        return "administrador/home";
    }

    //para que muestre las imagenes y procuctos en la home

}
