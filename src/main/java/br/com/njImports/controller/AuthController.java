package br.com.njImports.controller;

import br.com.njImports.dto.UsuariosDTO;
import br.com.njImports.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/autenticacao")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsuarioService usuarioService;


    @PostMapping("/login")
    public ResponseEntity loginUsuario(@Valid @RequestBody UsuariosDTO usuario){
        ResponseEntity<Object> valido = usuarioService.validarSenha(usuario);
        return valido;


    }


}
