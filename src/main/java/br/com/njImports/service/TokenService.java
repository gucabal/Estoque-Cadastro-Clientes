package br.com.njImports.service;

import br.com.njImports.dto.UsuariosDTO;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;

import java.time.LocalDateTime;
import java.time.ZoneOffset;


@Service
public class TokenService {


    public String gerartoken(UsuariosDTO usuariosDTO) {
        return JWT.create().
                withIssuer("Usuarios").
                withSubject(usuariosDTO.getCpf()).
                withExpiresAt(LocalDateTime.now().plusMinutes(10).toInstant(ZoneOffset.of("-03:00"))
                ).sign(Algorithm.HMAC256("criarstringsecreta"));
    }

    public String getSubject(String token) {
        return JWT.require(Algorithm.HMAC256("criarstringsecreta"))
                .withIssuer("Usuarios")
                .build().verify(token).getSubject();
    }
}
