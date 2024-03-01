package br.com.njImports.controller;
import br.com.njImports.dto.UsuariosDTO;
import br.com.njImports.repository.IUsuario;
import br.com.njImports.model.Usuarios;
import br.com.njImports.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
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
        if(!usuario.getTelefone().matches("(^[0-9]{2})?(\\s|-)?(9?[0-9]{4})-?([0-9]{4}$)")){
            return ResponseEntity.status(422).body(" O campo 'Telefone' deve conter ddd + 9 dígitos");
        }
        return usuarioService.cadastrarUsuario(usuario);
    }

    @PutMapping("/alterar")
    public ResponseEntity alterarUsuario(@Valid @RequestBody Usuarios usuario){
        if(!usuario.getTelefone().matches("(^[0-9]{2})?(\\s|-)?(9?[0-9]{4})-?([0-9]{4}$)")){
            return ResponseEntity.status(422).body(" O campo 'Telefone' deve conter ddd + 9 dígitos");
        }
        return usuarioService.alterarUsuario(usuario);
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
