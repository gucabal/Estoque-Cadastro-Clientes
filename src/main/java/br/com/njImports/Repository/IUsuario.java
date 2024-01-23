package br.com.njImports.Repository;

import br.com.njImports.model.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface IUsuario extends JpaRepository<Usuarios, Integer> {
}
