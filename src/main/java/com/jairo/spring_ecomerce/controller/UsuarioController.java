package com.jairo.spring_ecomerce.controller;

import com.jairo.spring_ecomerce.model.Orden;
import com.jairo.spring_ecomerce.model.Usuario;
import com.jairo.spring_ecomerce.service.IOrdenService;
import com.jairo.spring_ecomerce.service.IUsuarioService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.swing.event.HyperlinkEvent;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/usuario")
@Slf4j
public class UsuarioController {

    @Autowired
    private IUsuarioService usuarioService;

    @Autowired
    private IOrdenService ordenService;

    //mostrar la pagina de registro
    @GetMapping("/registro")
    public  String create(){
        return"usuario/registro";
    }

    //hacer guardado de usuario
    @PostMapping("/save")
    public String save(Usuario usuario){
        log.info("usaurio registro{}", usuario);

        usuario.setTipo("USER");
        usuarioService.saveUsuario(usuario);

        return "redirect:/";
    }

    @GetMapping("/login")
    public String login(){
        return "usuario/login";
    }

    @PostMapping("/acceder")
    public String acceder(Usuario usaurio,
                          HttpSession session){
        Optional<Usuario> user = usuarioService.findByEmail(usaurio.getEmail());

        if(user.isPresent()){
            session.setAttribute("idUsuario", user.get().getId());
            if(user.get().getTipo().equals("ADMIN")){
                return "redirect:/administrador";
            }else{
                return "redirect:/";
            }
        }else {
            log.info("usuario no existe");
        }

        return"redirect:/";
    }

    @GetMapping("/compras")
    public String obtenerCompras(HttpSession session,
                                 Model model){
        model.addAttribute("sesion",session.getAttribute("idUsuario"));
        Usuario usuario = usuarioService.findUsuario(Long.parseLong(session.getAttribute("idUsuario").toString())).get();
        List<Orden>ordenes=ordenService.findByUsuario(usuario);

        model.addAttribute("ordenes", ordenes);
        return "usuario/compras";
    }

    @GetMapping("/detalle/{id}")
    public String detalleCompra(@PathVariable Long id,
                                HttpSession session,
                                Model model){

        Optional<Orden> orden = ordenService.findById(id);
        model.addAttribute("detalles",orden.get().getDetalles());
        //sesion
        model.addAttribute("sesion", session.getAttribute("idUsuario"));
        return"usuario/detallecompra";
    }

    @GetMapping("/cerrar")
    public String cerrarSesion(HttpSession session){
        session.removeAttribute("idUsuario");


        return"redirect:/";
    }
}
