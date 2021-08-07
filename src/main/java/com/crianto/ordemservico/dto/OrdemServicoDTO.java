package com.crianto.ordemservico.dto;

import com.crianto.ordemservico.model.OrdemServico;
import com.crianto.ordemservico.model.OrdemServicoEstagio;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrdemServicoDTO {

    private Long id;
    private LocalDateTime dataAbertura;
    private Long clienteId;
    private String clienteNome;
    private Long equipamentoId;
    private String equipamentoDescricao;
    private String problemaApresentado;
    private LocalDateTime dataConclusao;
    private List<OrdemServicoEstagioDTO> estagios = new ArrayList<>();
    private String estagio;

    public OrdemServicoDTO(OrdemServico ordemServico) {
        this.id = ordemServico.getId();
        this.dataAbertura = ordemServico.getDataAbertura();
        if (ordemServico.getCliente() != null) {
            this.clienteId = ordemServico.getCliente().getId();
            this.clienteNome = ordemServico.getCliente().getNome();
        }
        if (ordemServico.getEquipamento() != null) {
            this.equipamentoId = ordemServico.getEquipamento().getId();
            this.equipamentoDescricao = ordemServico.getEquipamento().getDescricao();
        }
        this.problemaApresentado = ordemServico.getProblemaApresentado();
        this.dataConclusao = ordemServico.getDataConclusao();
        this.estagios = modelToDTO(ordemServico.getEstagios());
        this.estagio = ordemServico.getEstagio().getEstagio();
    }

    private List<OrdemServicoEstagioDTO> modelToDTO(List<OrdemServicoEstagio> ordemServicoEstagios) {
        return ordemServicoEstagios.stream().map(OrdemServicoEstagioDTO::new).collect(Collectors.toList());
    }
}
