package com.crianto.ordemservico.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrdemServicoRequestDTO {

    private Long clienteId;
    private Long equipamentoId;
    private String problemaApresentado;
    private List<OrdemServicoEstagioRequestDTO> estagios = new ArrayList<>();
    private String estagio;
}
