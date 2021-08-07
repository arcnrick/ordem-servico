package com.crianto.ordemservico.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrdemServicoEstagioRequestDTO {

    private Long id;
    private String terminou;
    private String detalhamento;
}
