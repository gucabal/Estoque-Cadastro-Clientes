package br.com.njImports.service;

import br.com.njImports.Repository.IUsuario;
import br.com.njImports.model.Usuarios;
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
    public UsuarioService(IUsuario repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }


    public List<Usuarios> listarUsuarios(){
        List<Usuarios> lista = (List<Usuarios>) repository.findAll();
        return lista;
    }


    public Usuarios cadastrarUsuario(Usuarios usuario){
        String encoder = this.passwordEncoder.encode(usuario.getSenha());
        usuario.setSenha(encoder);
        Usuarios usuarioNovo = repository.save(usuario);
        return usuarioNovo;
    }

    public Usuarios alterarUsuario(Usuarios usuario){
        String encoder = this.passwordEncoder.encode(usuario.getSenha());
        usuario.setSenha(encoder);
        Usuarios usuarioAlterado = repository.save(usuario);
        return usuarioAlterado;
    }

    public ResponseEntity<Object> validarSenha(Usuarios usuario) {

        Optional<Usuarios> usuarioOptional = repository.findByCpf(usuario.getCpf());

        if (usuarioOptional.isPresent()) {
            Usuarios usuarioEncontrado = usuarioOptional.get();

            String senha = usuarioEncontrado.getSenha();
            Boolean valid = passwordEncoder.matches(usuario.getSenha(), senha);

            if (!valid) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
