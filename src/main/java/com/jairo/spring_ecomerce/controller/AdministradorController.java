package com.jairo.spring_ecomerce.controller;

import com.jairo.spring_ecomerce.model.Orden;
import com.jairo.spring_ecomerce.model.Producto;
import com.jairo.spring_ecomerce.service.IOrdenService;
import com.jairo.spring_ecomerce.service.IProductoService;
import com.jairo.spring_ecomerce.service.IUsuarioService;
import com.jairo.spring_ecomerce.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/administrador")
public class AdministradorController {

    @Autowired
    private IProductoService productoService;

    @Autowired
    private IUsuarioService usuarioService;

    @Autowired
    private IOrdenService ordenService;

    @GetMapping("")
    public String home(Model model){
        List<Producto> productos= productoService.listProductos();
        model.addAttribute("productos", productos);
        return "administrador/home";
    }
   @GetMapping("/usuarios")
    public String usuarios(Model model){
        model.addAttribute("usuarios", usuarioService.findAll());
        return"administrador/usuarios";
    }

    @GetMapping("/ordenes")
    public String ordenes(Model model){
        model.addAttribute("ordenes",ordenService.listOrden());
        return"administrador/ordenes";
    }

    @GetMapping("/detalle/{id}")
    public String detalle(@PathVariable Long id,
                          Model model){
       Orden orden = ordenService.findById(id).get();

       model.addAttribute("detalles", orden.getDetalles());
        return"administrador/detalleorden";
    }

}
