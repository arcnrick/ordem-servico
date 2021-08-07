package com.crianto.ordemservico.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Table(name = "cliente")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Cliente implements Serializable {
    private static final Long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Access(value = AccessType.PROPERTY)
    private Long id;

    @Column(name = "nome", length = 120)
    @NotBlank(message = "Informe o nome do cliente")
    @Size(max = 120, message = "Nome do cliente deve ter no máximo 120 caracteres")
    private String nome;

    @Column(name = "endereco", length = 200)
    @Size(max = 200, message = "Endereço do cliente deve ter no máximo 200 caracteres")
    private String endereco;

    @Column(name = "telefone", length = 30)
    @Size(max = 30, message = "Telefone do cliente deve ter no máximo 30 caracteres")
    private String telefone;

    @Column(name = "email")
    @Email(message = "Informe um email válido")
    private String email;
}
