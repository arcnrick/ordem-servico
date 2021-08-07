package com.crianto.ordemservico.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class ClienteServiceTest {

    @InjectMocks
    private ClienteService service;

    @Test
    public void telefoneDeveTerSomenteNumeros() {
        String telefone = "65498787";

        assertTrue(service.telefoneValido(telefone));
    }

    @Test
    public void telefoneNaoDeveTerSomenteNumeros() {
        String telefone = "65498787A";

        assertFalse(service.telefoneValido(telefone));
    }
}
