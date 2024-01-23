package br.com.njImports.controller;
import br.com.njImports.Repository.IUsuario;
import br.com.njImports.model.Usuarios;
import br.com.njImports.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.yaml.snakeyaml.events.Event;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public ResponseEntity cadastrarUsuario(@Valid @RequestBody Usuarios usuario){
        return ResponseEntity.status(201).body(usuarioService.cadastrarUsuario(usuario));
    }

    @PutMapping("/alterar")
    public ResponseEntity alterarUsuario(@Valid @RequestBody Usuarios usuario){
        return ResponseEntity.status(200).body(usuarioService.alterarUsuario(usuario));
    }


    @PostMapping("/login")
    public ResponseEntity loginUsuario(@Valid @RequestBody Usuarios usuario){
        ResponseEntity<Object> valido = usuarioService.validarSenha(usuario);
        return valido;


    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationException(MethodArgumentNotValidException ex){
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) ->{
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;

    }


}
