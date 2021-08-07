package com.crianto.ordemservico.model;

import com.crianto.ordemservico.enums.TIPO_EQUIPAMENTO;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Table(name = "equipamento")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Equipamento implements Serializable {
    private static final Long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Access(value = AccessType.PROPERTY)
    private Long id;

    @Column(name = "descricao", length = 120)
    @NotBlank(message = "Informe a descrição do equipamento")
    @Size(max = 120, message = "Descrição do equipamento deve ter no máximo 120 caracteres")
    private String descricao;

    @Column(name = "tipoEquipamento", length = 4, columnDefinition = "char(4) default 'TP_1'")
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Informe o tipo do equipamento")
    private TIPO_EQUIPAMENTO tipo_equipamento;

    @ManyToOne
    @JoinColumn(name = "marcaId", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_equipamento_marca"))
    @Valid
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private Marca marca;
}
