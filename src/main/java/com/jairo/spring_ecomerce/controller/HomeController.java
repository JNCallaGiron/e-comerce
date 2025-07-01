package com.jairo.spring_ecomerce.controller;

import com.jairo.spring_ecomerce.model.DetalleOrden;
import com.jairo.spring_ecomerce.model.Orden;
import com.jairo.spring_ecomerce.model.Producto;
import com.jairo.spring_ecomerce.service.IProductoService;
import jakarta.persistence.Id;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/")
@Slf4j
public class HomeController {

    @Autowired
    private IProductoService productoService;

    //para almacenar los detalles de la orden
    List<DetalleOrden> detalles = new ArrayList<DetalleOrden>();

    //almacen los datos de la orden
    Orden orden = new Orden();

    @GetMapping("")
    public String home(Model model){
        model.addAttribute("productos", productoService.listProductos());
        return "usuario/home";
    }

    //de ver producto a una nueva vista
    @GetMapping("/productohome/{id}")
    public String productoHome(@PathVariable Long id,
                               Model model){
        log.info("Id  producto enviado como parametro {}", id);
        Producto producto= new Producto();
        Optional<Producto> productoOptional=productoService.getProducto(id);

        producto= productoOptional.get();

        model.addAttribute("producto",producto);
        return "usuario/productohome";
    }

    //
    @PostMapping("/cart")
    public String addCart(@RequestParam Long    id,
                          @RequestParam Integer cantidad,//atento al tipo dedato int puede causar un error
                          Model model){
        DetalleOrden detalleOrden = new DetalleOrden();
        Producto producto = new Producto();
        double sumaTotal= 0;

        Optional<Producto> optionalProducto = productoService.getProducto(id);
        log.info("Producto añadido: {}", optionalProducto.get());
        log.info("Cantidad: {}",cantidad);
        producto=optionalProducto.get();

        detalleOrden.setCantidad(cantidad);
        detalleOrden.setPrecio(producto.getPrecio());
        detalleOrden.setNombre(producto.getNombre());
        detalleOrden.setTotal(producto.getPrecio()* cantidad);
        detalleOrden.setProducto(producto);

        detalles.add(detalleOrden);

        sumaTotal=detalles.stream().mapToDouble(dt->dt.getTotal()).sum();

        orden.setTotal(sumaTotal);
        model.addAttribute("cart",detalles);
        model.addAttribute("orden", orden);

        return"usuario/carrito";
    }

    //metodo quitar producto del carrito
    @GetMapping("/delete/cart/{id}")
    public String deleteProductCart(@PathVariable Long id,
                                    Model model){

        //lista nueva de productos
        List<DetalleOrden>ordenesNueva=new ArrayList<DetalleOrden>();

        for(DetalleOrden detalleOrden: detalles){
            if (detalleOrden.getProducto().getId() != id){
                ordenesNueva.add(detalleOrden);
            }
        }
        //poner la nueva lista con los productos restantes
        detalles=ordenesNueva;
        double sumaTotal = 0;
        sumaTotal=detalles.stream().mapToDouble(dt->dt.getTotal()).sum();
        orden.setTotal(sumaTotal);
        model.addAttribute("cart",detalles);
        model.addAttribute("orden", orden);


        return"usuario/carrito";
    }

}
