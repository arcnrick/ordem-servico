package com.crianto.ordemservico.service;

import com.crianto.ordemservico.model.Marca;
import com.crianto.ordemservico.repository.MarcaRepository;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MarcaService {

    @Autowired
    private MarcaRepository repository;

    public Marca create(Marca model) {
        return repository.save(model);
    }

    public List<Marca> findAll() {
        return repository.findAll();
    }

    public Marca findById(Long id) {
        Optional<Marca> model = repository.findById(id);

        return model.orElseThrow(() -> new ObjectNotFoundException(
                id, "Marca não encontrada."));
    }

    public Marca update(Long id, Marca model) {
        // valida se existe msm no banco
        Marca modelOld = findById(id);
        model.setId(modelOld.getId());
        return repository.save(model);
    }

    public void delete(Long id) {
        // valida se existe msm no banco
        Marca modelOld = findById(id);

        repository.delete(modelOld);
    }
}
