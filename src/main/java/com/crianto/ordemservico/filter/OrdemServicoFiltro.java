package com.crianto.ordemservico.filter;

import com.crianto.ordemservico.enums.ESTAGIO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrdemServicoFiltro {

    private Long id;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime dataAbertura;
    private Long clienteId;
    private Long equipamentoId;
    private String problemaApresentado;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime dataConclusao;
    private ESTAGIO estagio;
}