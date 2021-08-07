package com.crianto.ordemservico.service;

import com.crianto.ordemservico.enums.TIPO_EQUIPAMENTO;
import com.crianto.ordemservico.model.Cliente;
import com.crianto.ordemservico.model.Equipamento;
import com.crianto.ordemservico.repository.ClienteRepository;
import com.crianto.ordemservico.repository.EquipamentoRepository;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EquipamentoService {

    @Autowired
    private EquipamentoRepository repository;

    @Autowired
    private MarcaService marcaService;


    public Equipamento create(Equipamento model) throws Exception {

        if (tipoValido(model.getTipo_equipamento().getTipoEquipamento()).equals("")) {
            throw new Exception("Tipo de equipamento não informado");
        }

        if (model.getMarca() != null) {
            model.setMarca(marcaService.findById(model.getMarca().getId()));
        } else {
            model.setMarca(null);
        }

        return repository.save(model);
    }

    public List<Equipamento> findAll() {
        return repository.findAll();
    }

    public Equipamento findById(Long id) {
        Optional<Equipamento> model = repository.findById(id);

        return model.orElseThrow(() -> new ObjectNotFoundException(
                id, "Equipamento não encontrado."));
    }

    public Equipamento update(Long id, Equipamento model) throws Exception {
        // valida se existe msm no banco
        Equipamento modelOld = findById(id);
        model.setId(modelOld.getId());

        if (tipoValido(model.getTipo_equipamento().getTipoEquipamento()).equals("")) {
            throw new Exception("Tipo de equipamento não informado");
        }

        if (model.getMarca() != null) {
            model.setMarca(marcaService.findById(model.getMarca().getId()));
        } else {
            model.setMarca(null);
        }

        return repository.save(model);
    }

    public void delete(Long id) {
        // valida se existe msm no banco
        Equipamento modelOld = findById(id);

        repository.delete(modelOld);
    }

    public String tipoValido(String tipoStr) {
        for (TIPO_EQUIPAMENTO sn : TIPO_EQUIPAMENTO.values()) {
            if (sn.getTipoEquipamento().equals(tipoStr)) {
                return tipoStr;
            }
        }

        return "";
    }
}
