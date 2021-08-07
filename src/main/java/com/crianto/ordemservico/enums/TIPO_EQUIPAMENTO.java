package com.crianto.ordemservico.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum TIPO_EQUIPAMENTO {

    @JsonProperty("TP_1")
    TP_1("TP_1"),
    @JsonProperty("TP_2")
    TP_2("TP_2"),
    @JsonProperty("TP_3")
    TP_3("TP_3");

    private final String tipoEquipamento;

    TIPO_EQUIPAMENTO(String tipoEquipamento) {
        this.tipoEquipamento = tipoEquipamento;
    }

    public String getTipoEquipamento() {
        return tipoEquipamento;
    }

    public static TIPO_EQUIPAMENTO toEnum(String str) {
        if (str == null) {
            return null;
        }

        for (TIPO_EQUIPAMENTO x : TIPO_EQUIPAMENTO.values()) {
            if (str.equals(x.getTipoEquipamento())) {
                return x;
            }
        }

        throw new IllegalArgumentException("Tipo de equipamento inválido: " + str);
    }
}