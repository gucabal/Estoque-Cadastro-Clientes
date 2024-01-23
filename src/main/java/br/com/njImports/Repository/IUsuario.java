package br.com.njImports.Repository;

import br.com.njImports.model.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface IUsuario extends CrudRepository<Usuarios, Integer> {
    Optional<Usuarios> findByCpf(String cpf);
}

