package com.crianto.ordemservico.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import static junit.framework.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class OrdemServicoServiceTest {

    @InjectMocks
    private OrdemServicoService service;

    @Test
    public void estagioDeveSerValido() {

        String estagio = "CONCLUIDO";

        String resp = service.estagioValido(estagio);

        assertEquals(estagio, resp);
    }
}
