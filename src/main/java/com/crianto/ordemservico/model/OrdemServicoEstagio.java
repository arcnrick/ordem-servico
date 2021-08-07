package com.crianto.ordemservico.model;

import com.crianto.ordemservico.enums.ESTAGIO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "ordem_servico_estagio")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class OrdemServicoEstagio implements Serializable {
    private static final Long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Access(value = AccessType.PROPERTY)
    private Long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "ordemServicoId", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_ordem_servico_estagio_ordem_servico"))
    @Valid
    @NotNull(message = "Informe o ID da Ordem de Serviço")
    private OrdemServico ordemServico;

    @CreationTimestamp
    @Column(name = "inicio", updatable = false)
    private LocalDateTime inicio;

    @Column(name = "termino", updatable = false)
    private LocalDateTime termino;

    @Column(name = "detalhamento", length = 500)
    @Size(max = 500, message = "Detalhamento deve ter no máximo 500 caracteres")
    @NotNull(message = "Informe o detalhamento")
    private String detalhamento;
}