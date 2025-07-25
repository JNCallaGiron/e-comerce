package com.jairo.spring_ecomerce.controller;

import com.jairo.spring_ecomerce.model.Producto;
import com.jairo.spring_ecomerce.model.Usuario;
import com.jairo.spring_ecomerce.service.CloudinaryService;
import com.jairo.spring_ecomerce.service.IUsuarioService;
import com.jairo.spring_ecomerce.service.ProductoService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Controller
@Slf4j
@RequestMapping("/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private CloudinaryService cloudinaryService;

    @Autowired
    private IUsuarioService usuarioService;

    @GetMapping("")
    public String show(Model model){
        model.addAttribute("productos", productoService.listProductos());
        return "productos/show";
    }

    @GetMapping("/create")
    public String create(){
        return "productos/create";
    }

    @PostMapping("/save")
    public String save(Producto producto,
                       @RequestParam("img") MultipartFile file,
                       HttpSession session) throws IOException {

        log.info("Este es el objeto producto {}", producto);
        Usuario u = usuarioService.findUsuario(Long.parseLong(session.getAttribute("idUsuario").toString())).get();
        producto.setUsuario(u);

        if (producto.getId() == null && !file.isEmpty()) {
            String imageUrl = cloudinaryService.uploadFile(file);
            producto.setImagen(imageUrl);
        }

        productoService.saveProdcuto(producto);
        return "redirect:/productos";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model){
        Optional<Producto> optionalProducto = productoService.getProducto(id);
        Producto producto = optionalProducto.orElse(new Producto());
        log.info("Producto buscado: {}", producto);
        model.addAttribute("producto", producto);
        return "productos/edit";
    }

    @PostMapping("/update")
    public String update(Producto producto,
                         @RequestParam("img") MultipartFile file) throws IOException{

        Producto p = productoService.getProducto(producto.getId()).orElse(new Producto());

        if (file.isEmpty()) {
            producto.setImagen(p.getImagen());
        } else {
            String imageUrl = cloudinaryService.uploadFile(file);
            producto.setImagen(imageUrl);
        }

        producto.setUsuario(p.getUsuario());
        productoService.update(producto);
        return "redirect:/productos";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id){
        productoService.deleteProducto(id);
        return "redirect:/productos";
    }
}

