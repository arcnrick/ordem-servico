package com.crianto.ordemservico.dto;

import com.crianto.ordemservico.model.OrdemServicoEstagio;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrdemServicoEstagioDTO {

    private Long id;
    private LocalDateTime inicio;
    private LocalDateTime termino;
    private String detalhamento;

    public OrdemServicoEstagioDTO(OrdemServicoEstagio ordemServicoEstagio) {
        this.id = ordemServicoEstagio.getId();
        this.inicio = ordemServicoEstagio.getInicio();
        this.termino = ordemServicoEstagio.getTermino();
        this.detalhamento = ordemServicoEstagio.getDetalhamento();
    }
}
