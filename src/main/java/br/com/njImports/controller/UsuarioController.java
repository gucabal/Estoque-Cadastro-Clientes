package br.com.njImports.controller;
import br.com.njImports.Repository.IUsuario;
import br.com.njImports.model.Usuarios;
import br.com.njImports.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.yaml.snakeyaml.events.Event;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private IUsuario dao;

    @Autowired
    private UsuarioService usuarioService;


    @GetMapping()
    public ResponseEntity listaDeUsuarios(){
        return ResponseEntity.status(200).body(usuarioService.listarUsuarios());

    }

    @PostMapping("/cadastrar")
    public ResponseEntity cadastrarUsuario(@RequestBody Usuarios usuario){
        return ResponseEntity.status(201).body(usuarioService.cadastrarUsuario(usuario));
    }

    @PutMapping("/alterar")
    public ResponseEntity alterarUsuario(@RequestBody Usuarios usuario){
        return ResponseEntity.status(200).body(usuarioService.alterarUsuario(usuario));
    }


}
