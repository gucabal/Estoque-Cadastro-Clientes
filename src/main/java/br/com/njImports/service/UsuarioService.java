package br.com.njImports.service;

import br.com.njImports.Repository.IUsuario;
import br.com.njImports.model.Usuarios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private IUsuario repository;

    public List<Usuarios> listarUsuarios(){
        List<Usuarios> lista = repository.findAll();
        return lista;
    }


    public Usuarios cadastrarUsuario(Usuarios usuario){
        Usuarios usuarioNovo = repository.save(usuario);
        return usuarioNovo;
    }

    public Usuarios alterarUsuario(Usuarios usuario){
        Usuarios usuarioAlterado = repository.save(usuario);
        return usuarioAlterado;
    }

}
