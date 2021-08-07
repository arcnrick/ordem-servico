package com.crianto.ordemservico.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Table(name = "marca")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Marca implements Serializable {
    private static final Long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Access(value = AccessType.PROPERTY)
    private Long id;

    @Column(name = "nome", length = 120)
    @NotBlank(message = "Informe o nome da marca")
    @Size(max = 120, message = "Nome da marca deve ter no máximo 120 caracteres")
    private String nome;
}
