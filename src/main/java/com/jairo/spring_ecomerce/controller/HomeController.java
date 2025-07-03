package com.jairo.spring_ecomerce.controller;

import com.jairo.spring_ecomerce.model.DetalleOrden;
import com.jairo.spring_ecomerce.model.Orden;
import com.jairo.spring_ecomerce.model.Producto;
import com.jairo.spring_ecomerce.model.Usuario;
import com.jairo.spring_ecomerce.service.IDetalleOrdenService;
import com.jairo.spring_ecomerce.service.IOrdenService;
import com.jairo.spring_ecomerce.service.IProductoService;
import com.jairo.spring_ecomerce.service.IUsuarioService;
import jakarta.persistence.Id;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/")
@Slf4j
public class HomeController {

    @Autowired
    private IProductoService productoService;

    @Autowired
    private IUsuarioService usuarioService;

    @Autowired
    private IOrdenService iOrdenService;

    @Autowired
    private IDetalleOrdenService iDetalleOrdenService;

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
    public String addCart(@RequestParam Long id,
                          @RequestParam Integer cantidad,
                          Model model){
        DetalleOrden detalleOrden = new DetalleOrden();
        Producto producto = new Producto();
        double sumaTotal= 0;

        Optional<Producto> optionalProducto = productoService.getProducto(id);
        log.info("Producto añadido: {}", optionalProducto.get());
        log.info("Cantidad: {}", cantidad);
        producto = optionalProducto.get();

        detalleOrden.setCantidad(cantidad);
        detalleOrden.setPrecio(producto.getPrecio());
        detalleOrden.setNombre(producto.getNombre());
        detalleOrden.setTotal(producto.getPrecio() * cantidad);
        detalleOrden.setProducto(producto);

        // Validar que el producto no se añada 2 veces
        Long idProducto = producto.getId();
        boolean ingresado = detalles.stream()
                .anyMatch(p -> p.getProducto().getId().equals(idProducto));

        if (!ingresado) {
            detalles.add(detalleOrden);
        }

        sumaTotal = detalles.stream().mapToDouble(dt -> dt.getTotal()).sum();

        orden.setTotal(sumaTotal);
        model.addAttribute("cart", detalles);
        model.addAttribute("orden", orden);

        return "usuario/carrito";
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

    @GetMapping("/getCart")
    public String getCart(Model model){
        model.addAttribute("cart",detalles);
        model.addAttribute("orden", orden);
        return "/usuario/carrito";
    }

    //resumen de orden
    @GetMapping("/order")
    public String orden(Model model){

        Usuario usuario = usuarioService.findUsuario(1L).get();

        model.addAttribute("cart",detalles);
        model.addAttribute("orden", orden);
        model.addAttribute("usuario", usuario);
        return "usuario/resumenorden";
    }

    //guardar la orden
    @GetMapping("/saveOrder")
    public  String saveOrder(){
        Date fechaCreacion = new Date();
        orden.setFechaCreacion(fechaCreacion);
        orden.setNumero(iOrdenService.generarNumeroOrden());
        //usuario
        Usuario usuario = usuarioService.findUsuario(1L).get();
        orden.setUsuario(usuario);
        iOrdenService.saveOrden(orden);
        //guardar detalles
        for (DetalleOrden dt:detalles){
            dt.setOrden(orden);
            iDetalleOrdenService.save(dt);
        }
        //limpiar lista y orden
        orden = new Orden();
        detalles.clear();

        return"redirect:/";
    }
    //para la barra de busqueda
    @PostMapping("/search")
    public String searchProduct(@RequestParam String nombre,
                                Model model){
        log.info("nombre del producto: {}", nombre); //nombre por que en el form html esta asi
        List<Producto> productos= productoService.listProductos().stream().filter(
                p -> p.getNombre().contains(nombre)).collect(Collectors.toList());
        model.addAttribute("productos",productos);
        return"usuario/home";
    }
}
