package com.iot.usach.incubadora.controller;

import com.iot.usach.incubadora.entity.Usuario;
import com.iot.usach.incubadora.entity.request.AddUsuarioRequest;
import com.iot.usach.incubadora.repository.UsuarioRepository;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    private UsuarioRepository usuarioRepository;

    public UsuarioController(UsuarioRepository usuarioRepository){
        this.usuarioRepository = usuarioRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Usuario> findAllUsuarios() {
        return usuarioRepository.findAll();
    }

    @RequestMapping(method = RequestMethod.POST)
    public void addUsuario(@RequestBody AddUsuarioRequest addUsuarioRequest) {
        Usuario usuario = new Usuario();
        usuario.setNombre(addUsuarioRequest.getNombre());
        usuarioRepository.save(usuario);
    }

}
