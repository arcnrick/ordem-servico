package com.crianto.ordemservico.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import static junit.framework.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class EquipamentoServiceTest {

    @InjectMocks
    private EquipamentoService service;

    @Test
    public void tipoEquipamentoDeveSerValido() {

        String tipo = "TP_1";

        String resp = service.tipoValido(tipo);

        assertEquals(tipo, resp);
    }
}
