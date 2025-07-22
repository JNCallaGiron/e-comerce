package com.jairo.spring_ecomerce.controller;

import com.jairo.spring_ecomerce.model.Orden;
import com.jairo.spring_ecomerce.model.Usuario;
import com.jairo.spring_ecomerce.service.IOrdenService;
import com.jairo.spring_ecomerce.service.IUsuarioService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

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
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        usuarioService.saveUsuario(usuario);
        return "redirect:/";
    }

    @GetMapping("/login")
    public String login(){
        return "usuario/login";
    }


    //
    @GetMapping("/acceder")
    public String acceder(HttpSession session) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        Optional<Usuario> user = usuarioService.findByEmail(email);

        if (user.isPresent()) {
            session.setAttribute("idUsuario", user.get().getId());

            if ("ADMIN".equalsIgnoreCase(user.get().getTipo())) {
                return "redirect:/administrador";
            } else {
                return "redirect:/";
            }
        }

        return "redirect:/usuario/login?error";
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
