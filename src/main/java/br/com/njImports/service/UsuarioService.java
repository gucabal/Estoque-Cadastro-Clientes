package br.com.njImports.service;

import br.com.njImports.dto.UsuariosDTO;
import br.com.njImports.model.Usuarios;
import br.com.njImports.repository.IUsuario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    private final IUsuario repository;
    private final PasswordEncoder passwordEncoder;
    private final Logger logger = LoggerFactory.getLogger(UsuarioService.class);

    @Autowired
    private TokenService tokenService;

    @Autowired
    public UsuarioService(IUsuario repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }


    public List<Usuarios> listarUsuarios() {
        logger.info("Listando Usuários");
        List<Usuarios> lista = (List<Usuarios>) repository.findAll();
        return lista;
    }


    public ResponseEntity cadastrarUsuario(Usuarios usuario) {
        logger.info("Cadastrando usuário!");

        if (repository.findByCpf(usuario.getCpf()) != null) {
            logger.info("Cpf já possui cadastro.");
            return ResponseEntity.status(422).body("Cpf já possui cadastro. Por favor, tente fazer login ou recupere sua senha.");
        }

        String encoder = this.passwordEncoder.encode(usuario.getSenha());
        usuario.setSenha(encoder);
        Usuarios usuarioNovo = repository.save(usuario);
        logger.info("Usuário cadastrado!");
        return ResponseEntity.status(201).body(usuario);
    }

    public ResponseEntity alterarUsuario(Usuarios usuario) {
        logger.info("Alterando Usuário!");

        if (repository.findByCpf(usuario.getCpf()) == null)
            return ResponseEntity.status(422).body("Usuário não cadastrado.");
        logger.info("Usuário não cadastrado!");


        String encoder = this.passwordEncoder.encode(usuario.getSenha());
        usuario.setSenha(encoder);
        Usuarios usuarioAlterado = repository.save(usuario);
        logger.info("Usuário Alterado!");
        return ResponseEntity.status(200).body(usuarioAlterado);

    }

    public ResponseEntity<Object> validarSenha(UsuariosDTO usuario) {
        logger.info("Validando senha.");

        UsuariosDTO usuarioOptional = repository.findByCpf(usuario.getCpf());

        if (usuarioOptional != null) {
            String senha = usuarioOptional.getSenha();
            Boolean valid = passwordEncoder.matches(usuario.getSenha(), senha);

            if (!valid) {
                logger.info("Usuário não autorizado!");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            logger.info("Usuário autorizado!");
            return ResponseEntity.status(HttpStatus.OK).body(tokenService.gerartoken(usuario));
        } else {
            logger.info("Usuário não autorizado!");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }


}
