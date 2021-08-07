package com.crianto.ordemservico.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum ESTAGIO {

    @JsonProperty("AGUARDANDO_INICIO")
    AGUARDANDO_INICIO("AGUARDANDO_INICIO"),
    @JsonProperty("EM_ATENDIMENTO")
    EM_ATENDIMENTO("EM_ATENDIMENTO"),
    @JsonProperty("PAUSADO")
    PAUSADO("PAUSADO"),
    @JsonProperty("CANCELADO")
    CANCELADO("CANCELADO"),
    @JsonProperty("CONCLUIDO")
    CONCLUIDO("CONCLUIDO");

    private final String estagio;

    ESTAGIO(String estagio) {
        this.estagio = estagio;
    }

    public String getEstagio() {
        return estagio;
    }

    public static ESTAGIO toEnum(String str) {
        if (str == null) {
            return null;
        }

        for (ESTAGIO x : ESTAGIO.values()) {
            if (str.equals(x.getEstagio())) {
                return x;
            }
        }

        throw new IllegalArgumentException("Estágio de ordem de serviço inválido: " + str);
    }
}