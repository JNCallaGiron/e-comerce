package com.jairo.spring_ecomerce.controller;

import com.jairo.spring_ecomerce.model.Producto;
import com.jairo.spring_ecomerce.model.Usuario;
import com.jairo.spring_ecomerce.service.ProductoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


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

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model){
        Producto producto=new Producto();
        Optional<Producto> optionalProducto= productoService.getProducto(id);
        producto=optionalProducto.get();
        log.info("producto buscado: {}", producto);
        model.addAttribute("producto",producto);
        return "productos/edit";
    }

    @PostMapping("/update")
    public String update(Producto producto){
        productoService.update(producto);
        return "redirect:/productos";
    }

    //eliminar producto
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id){
        productoService.deleteProducto(id);
        return"redirect:/productos";
    }
}
