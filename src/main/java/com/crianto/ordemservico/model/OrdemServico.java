package com.crianto.ordemservico.model;

import com.crianto.ordemservico.enums.ESTAGIO;
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
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ordem_servico")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(exclude = {"estagios"})
@EqualsAndHashCode(exclude = {"estagios"})
public class OrdemServico implements Serializable {
    private static final Long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Access(value = AccessType.PROPERTY)
    private Long id;

    @CreationTimestamp
    @Column(name = "dataAbertura", updatable = false)
    private LocalDateTime dataAbertura;

    @ManyToOne
    @JoinColumn(name = "clienteId", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_ordem_servico_cliente"))
    @Valid
    @NotNull(message = "Informe o cliente")
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "equipamentoId", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_ordem_servico_equipamento"))
    @Valid
    @NotNull(message = "Informe o equipamento")
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private Equipamento equipamento;

    @Column(name = "problemaApresentado", length = 500)
    @Size(max = 500, message = "Problema apresentado deve ter no máximo 500 caracteres")
    @NotNull(message = "Informe o problema apresentado")
    private String problemaApresentado;

    @Column(name = "dataConclusao", updatable = false)
    private LocalDateTime dataConclusao;

    @OneToMany(mappedBy = "ordemServico", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<OrdemServicoEstagio> estagios = new ArrayList<>();

    @Column(name = "estagio", length = 20, columnDefinition = "char(20) default 'AGUARDANDO_INICIO'")
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Informe o estágio")
    private ESTAGIO estagio;
}