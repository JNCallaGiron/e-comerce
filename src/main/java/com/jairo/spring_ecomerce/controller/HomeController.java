/*package com.jairo.spring_ecomerce.controller;

import com.jairo.spring_ecomerce.model.DetalleOrden;
import com.jairo.spring_ecomerce.model.Orden;
import com.jairo.spring_ecomerce.model.Producto;
import com.jairo.spring_ecomerce.model.Usuario;
import com.jairo.spring_ecomerce.service.IDetalleOrdenService;
import com.jairo.spring_ecomerce.service.IOrdenService;
import com.jairo.spring_ecomerce.service.IProductoService;
import com.jairo.spring_ecomerce.service.IUsuarioService;
import jakarta.persistence.Id;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;
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
    public String home(Model model,
                       HttpSession session){
        log.info("Sesion del usuario {}", session.getAttribute("idUsuario"));
        model.addAttribute("productos", productoService.listProductos());

        //session
        model.addAttribute("sesion",session.getAttribute("idUsuario"));
        return "usuario/home";
    }

    //de ver producto a una nueva vista En HomeController
    @GetMapping("/productohome/{id}")
    public String productoHome(
            @PathVariable Long id,
            Model model,
            HttpSession session
    ) {
        log.info("Id producto: {}", id);

        // En lugar de .orElseThrow(...), usa un Supplier válido:
        Producto producto = productoService
                .getProducto(id)
                .orElseThrow(() ->
                        new NoSuchElementException("Producto con id " + id + " no encontrado")
                );

        model.addAttribute("producto", producto);
        model.addAttribute("sesion", session.getAttribute("idUsuario"));
        return "usuario/productohome";
    }



    //
    @PostMapping("/cart")
    public String addCart(@RequestParam Long id,
                          @RequestParam Integer cantidad,
                          Model model,
                          HttpSession session){
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

        model.addAttribute("sesion",session.getAttribute("isUsuario"));

        return "usuario/carrito";
    }


    //metodo quitar producto del carrito
    @GetMapping("/delete/cart/{id}")
    public String deleteProductCart(@PathVariable Long id,
                                    Model model,
                                    HttpSession session){

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

        model.addAttribute("sesion",session.getAttribute("isUsuario"));


        return"usuario/carrito";
    }

    @GetMapping("/getCart")
    public String getCart(Model model,
                          HttpSession session){
        model.addAttribute("cart",detalles);
        model.addAttribute("orden", orden);

        //sesion
        model.addAttribute("sesion",session.getAttribute("idUsuario"));
        return "/usuario/carrito";
    }

    //resumen de orden
    @GetMapping("/order")
    public String orden(Model model,
                        HttpSession session){

        Usuario usuario = usuarioService.findUsuario(Long.parseLong(session.getAttribute("idUsuario").toString())).get();

        model.addAttribute("cart",detalles);
        model.addAttribute("orden", orden);
        model.addAttribute("usuario", usuario);

        model.addAttribute("sesion",session.getAttribute("isUsuario"));

        return "usuario/resumenorden";
    }

    //guardar la orden
    @GetMapping("/saveOrder")
    public  String saveOrder(HttpSession session){
        Date fechaCreacion = new Date();
        orden.setFechaCreacion(fechaCreacion);
        orden.setNumero(iOrdenService.generarNumeroOrden());
        //usuario
        Usuario usuario = usuarioService.findUsuario(Long.parseLong(session.getAttribute("idUsuario").toString())).get();
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
    @GetMapping("/search")
    public String searchProduct(
            @RequestParam(name = "nombre") String nombre,
            Model model,
            HttpSession session
    ) {
        log.info("nombre del producto: {}", nombre);

        List<Producto> productos = productoService.listProductos().stream()
                .filter(p -> p.getNombre().toLowerCase().contains(nombre.toLowerCase()))
                .collect(Collectors.toList());

        model.addAttribute("productos", productos);
        model.addAttribute("sesion", session.getAttribute("idUsuario"));
        return "usuario/home";
    }

}*/
package com.jairo.spring_ecomerce.controller;

import com.jairo.spring_ecomerce.model.DetalleOrden;
import com.jairo.spring_ecomerce.model.Orden;
import com.jairo.spring_ecomerce.model.Producto;
import com.jairo.spring_ecomerce.model.Usuario;
import com.jairo.spring_ecomerce.service.IDetalleOrdenService;
import com.jairo.spring_ecomerce.service.IOrdenService;
import com.jairo.spring_ecomerce.service.IProductoService;
import com.jairo.spring_ecomerce.service.IUsuarioService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Controller
@Slf4j
public class HomeController {

    private final IProductoService productoService;
    private final IUsuarioService usuarioService;
    private final IOrdenService ordenService;
    private final IDetalleOrdenService detalleService;

    // guardamos el carrito en memoria (para demo)
    private List<DetalleOrden> detalles = new ArrayList<>();
    private Orden orden = new Orden();

    public HomeController(IProductoService productoService,
                          IUsuarioService usuarioService,
                          IOrdenService ordenService,
                          IDetalleOrdenService detalleService) {
        this.productoService = productoService;
        this.usuarioService = usuarioService;
        this.ordenService = ordenService;
        this.detalleService = detalleService;
    }

    /** 1) Home público y listado */
    @GetMapping("/")
    public String home(Model model, HttpSession session) {
        model.addAttribute("productos", productoService.listProductos());
        model.addAttribute("sesion", session.getAttribute("idUsuario"));
        return "usuario/home";
    }

    /** 2) Ver detalle de un producto */
    @GetMapping("/productohome/{id}")
    public String productoHome(@PathVariable("id") Long id,
                               Model model,
                               HttpSession session) {
        Producto producto = productoService.getProducto(id)
                .orElseThrow(() -> new NoSuchElementException("Producto no encontrado: " + id));
        model.addAttribute("producto", producto);
        model.addAttribute("sesion", session.getAttribute("idUsuario"));
        return "usuario/productohome";
    }

    /** 3) Añadir al carrito */
    @PostMapping("/cart")
    public String addCart(@RequestParam("id") Long id,
                          @RequestParam("cantidad") Integer cantidad,
                          Model model,
                          HttpSession session) {

        Producto producto = productoService.getProducto(id)
                .orElseThrow();
        DetalleOrden det = new DetalleOrden();
        det.setProducto(producto);
        det.setCantidad(cantidad);
        det.setPrecio(producto.getPrecio());
        det.setTotal(producto.getPrecio() * cantidad);
        det.setNombre(producto.getNombre());

        // no duplicar
        if (detalles.stream().noneMatch(d -> d.getProducto().getId().equals(id))) {
            detalles.add(det);
        }

        orden.setTotal(detalles.stream().mapToDouble(DetalleOrden::getTotal).sum());
        model.addAttribute("cart", detalles);
        model.addAttribute("orden", orden);
        model.addAttribute("sesion", session.getAttribute("idUsuario"));
        return "usuario/carrito";
    }

    /** 4) Mostrar carrito */
    @GetMapping("/getCart")
    public String getCart(Model model, HttpSession session) {
        model.addAttribute("cart", detalles);
        model.addAttribute("orden", orden);
        model.addAttribute("sesion", session.getAttribute("idUsuario"));
        return "usuario/carrito";
    }

    /** 5) Quitar del carrito */
    @GetMapping("/delete/cart/{id}")
    public String deleteFromCart(@PathVariable("id") Long id, Model model, HttpSession session) {
        detalles.removeIf(d -> d.getProducto().getId().equals(id));
        orden.setTotal(detalles.stream().mapToDouble(DetalleOrden::getTotal).sum());
        model.addAttribute("cart", detalles);
        model.addAttribute("orden", orden);
        model.addAttribute("sesion", session.getAttribute("idUsuario"));
        return "usuario/carrito";
    }


    @GetMapping("/order")
    public String orderSummary(Model model, HttpSession session) {
        // carga usuario
        Usuario u = usuarioService.findUsuario(Long.parseLong(session.getAttribute("idUsuario").toString()))
                .orElseThrow();
        model.addAttribute("usuario", u);
        model.addAttribute("cart", detalles);
        model.addAttribute("orden", orden);
        model.addAttribute("sesion", session.getAttribute("idUsuario"));
        return "usuario/resumenorden";
    }

    /** 7) Guardar orden */
    @GetMapping("/saveOrder")
    public String saveOrder(HttpSession session) {
        // genera número, fecha...
        orden.setNumero(ordenService.generarNumeroOrden());
        ordenService.saveOrden(orden);
        detalles.forEach(d -> {
            d.setOrden(orden);
            detalleService.save(d);
        });
        // limpiar
        detalles.clear();
        orden = new Orden();
        return "redirect:/";
    }

    /** 8) Búsqueda pública */
    @GetMapping("/search")
    public String search(@RequestParam("nombre") String nombre, Model model, HttpSession session) {
        List<Producto> encontrados = productoService.listProductos().stream()
                .filter(p -> p.getNombre().toLowerCase().contains(nombre.toLowerCase()))
                .toList();
        model.addAttribute("productos", encontrados);
        model.addAttribute("sesion", session.getAttribute("idUsuario"));
        return "usuario/home";
    }

}
