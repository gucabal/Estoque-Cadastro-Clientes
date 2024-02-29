package br.com.njImports.service;

import br.com.njImports.dto.UsuariosDTO;
import br.com.njImports.model.Usuarios;
import br.com.njImports.repository.IUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private IUsuario repository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenService tokenService;

    @Autowired
    public UsuarioService(IUsuario repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }


    public List<Usuarios> listarUsuarios(){
        List<Usuarios> lista = (List<Usuarios>) repository.findAll();
        return lista;
    }


    public ResponseEntity cadastrarUsuario(Usuarios usuario){

        if(repository.findByCpf(usuario.getCpf()) != null)
           return ResponseEntity.status(422).body("Cpf já possui cadastro. Por favor, tente fazer login ou recupere sua senha.");

        String encoder = this.passwordEncoder.encode(usuario.getSenha());
        usuario.setSenha(encoder);
        Usuarios usuarioNovo = repository.save(usuario);
        return ResponseEntity.status(201).body(usuario);
    }

    public ResponseEntity alterarUsuario(Usuarios usuario){
        if(repository.findByCpf(usuario.getCpf()) == null)
            return ResponseEntity.status(422).body("Usuário não cadastrado");


        String encoder = this.passwordEncoder.encode(usuario.getSenha());
        usuario.setSenha(encoder);
        Usuarios usuarioAlterado = repository.save(usuario);
        return ResponseEntity.status(200).body(usuarioAlterado);
    }

    public ResponseEntity<Object> validarSenha(UsuariosDTO usuario) {

        UsuariosDTO usuarioOptional = repository.findByCpf(usuario.getCpf());

        if (usuarioOptional != null) {
            String senha = usuarioOptional.getSenha();
            Boolean valid = passwordEncoder.matches(usuario.getSenha(), senha);

            if (!valid) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

            }

            return ResponseEntity.status(HttpStatus.OK).body(tokenService.gerartoken(usuario));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
