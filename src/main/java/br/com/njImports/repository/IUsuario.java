package br.com.njImports.repository;

import br.com.njImports.dto.UsuariosDTO;
import br.com.njImports.model.Usuarios;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUsuario extends CrudRepository<Usuarios, Integer> {
    UsuariosDTO findByCpf(String cpf);
}

