package com.jairo.spring_ecomerce.controller;

import com.jairo.spring_ecomerce.model.Producto;
import com.jairo.spring_ecomerce.model.Usuario;
import com.jairo.spring_ecomerce.service.ProductoService;
import com.jairo.spring_ecomerce.service.UploadFileService;
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
    private UploadFileService upload;

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
    public String save(Producto producto,
                       @RequestParam("img") MultipartFile file) throws IOException {
        log.info("Este es el objeto producto {}", producto);
        Usuario u = new Usuario(1L,"","","","","","","",null,null);
        producto.setUsuario(u);

        //imagen cargada por primera vez
        if(producto.getId()==null) {//cuando se crea un producto
            String nombreImagen=upload.saveImage(file);
            producto.setImagen(nombreImagen);
        }else{

        }

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
    public String update(Producto producto,
                         @RequestParam("img") MultipartFile file) throws IOException{

        Producto p = new Producto();
        p= productoService.getProducto(producto.getId()).get();

        if (file.isEmpty()){ // editamos el producto pero no se cambia la imagen
            producto.setImagen(p.getImagen());
        }else{ // cuando se edita la imagen
            //eliminar cuando no sea la imagen poor defecto
            if (!p.getImagen().equals("default.jpg")){
                upload.deleteImage(p.getImagen());
            }
            String nombreImagen = upload.saveImage(file);
            producto.setImagen(nombreImagen);
        }
        producto.setUsuario(p.getUsuario());
        productoService.update(producto);
        return "redirect:/productos";
    }

    //eliminar producto
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id){
        Producto p = new Producto();
        p=productoService.getProducto(id).get();

        //eliminar cuando no sea la imagen poor defecto
        if (!p.getImagen().equals("default.jpg")){
            upload.deleteImage(p.getImagen());
        }

        productoService.deleteProducto(id);
        return"redirect:/productos";
    }
}
