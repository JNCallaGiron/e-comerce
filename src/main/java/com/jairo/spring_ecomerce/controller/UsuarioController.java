package com.jairo.spring_ecomerce.controller;

import com.jairo.spring_ecomerce.model.Usuario;
import com.jairo.spring_ecomerce.service.IUsuarioService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/usuario")
@Slf4j
public class UsuarioController {

    @Autowired
    private IUsuarioService usuarioService;

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
}
