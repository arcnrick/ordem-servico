package com.crianto.ordemservico.service;

import com.crianto.ordemservico.model.Cliente;
import com.crianto.ordemservico.repository.ClienteRepository;
import com.crianto.ordemservico.util.Funcoes;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository repository;

    public Cliente create(Cliente model) {

        if (!telefoneValido(model.getTelefone())) {
            model.setTelefone(null);
        }

        return repository.save(model);
    }

    public List<Cliente> findAll() {
        return repository.findAll();
    }

    public Cliente findById(Long id) {
        Optional<Cliente> model = repository.findById(id);

        return model.orElseThrow(() -> new ObjectNotFoundException(
                id, "Cliente não encontrado."));
    }

    public Cliente update(Long id, Cliente model) {
        // valida se existe msm no banco
        Cliente modelOld = findById(id);
        model.setId(modelOld.getId());

        if (!telefoneValido(model.getTelefone())) {
            model.setTelefone(null);
        }

        return repository.save(model);
    }

    public void delete(Long id) {
        // valida se existe msm no banco
        Cliente modelOld = findById(id);

        repository.delete(modelOld);
    }

    public boolean telefoneValido(String str) {
        return Funcoes.somenteNumeros(str);
    }
}
