package br.com.njImports.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.hibernate.validator.constraints.br.CPF;


import java.io.Serializable;

@Data
@Entity
@Table(name= "usuario")
public class Usuarios implements Serializable {


    @Size(min = 11, max = 11, message = "CPF informado é inválido! CPF deve ser númerico é conter 11 digitos." )
    @CPF(message = "CPF informado é inválido! CPF deve ser númerico é conter 11 digitos.")
    @NotBlank(message = "O campo 'CPF' é Obrigatório!")
    @Id
    @Column(name = "cpf", length = 11, nullable = true)
    private String cpf;

    @Size(min = 1, max = 200, message = "Nome informado é inválido!" )
    @NotBlank(message = "O campo 'Nome' é Obrigatório!")
    @Column(name = "nome_completo", length = 200, nullable = true)
    private String nome_completo;

    @Size(max = 100, message = "Email informado é inválido!" )
    @Email(message = "Email informado é inválido!")
    @NotBlank(message = "O campo 'Email' é Obrigatório!")
    @Column(name = "email", length = 100, nullable = true)
    private String email;

    @NotBlank(message = "O campo 'Senha' é Obrigatório!")
    @Column(name = "senha", columnDefinition = "TEXT", nullable = true)
    private String senha;

    @Size(max = 15, message = "Numero de telefone informado é inválido!" )
    @NotBlank(message = "O campo 'Telefone' é Obrigatório!")
    @Column(name = "telefone", length = 15, nullable = true)
    private String telefone;





}
