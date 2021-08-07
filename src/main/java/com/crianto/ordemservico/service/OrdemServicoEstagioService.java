package com.crianto.ordemservico.service;

import com.crianto.ordemservico.dto.OrdemServicoEstagioRequestDTO;
import com.crianto.ordemservico.model.OrdemServicoEstagio;
import com.crianto.ordemservico.repository.OrdemServicoEstagioRepository;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class OrdemServicoEstagioService {

    @Autowired
    private OrdemServicoEstagioRepository repository;

    public OrdemServicoEstagio create(OrdemServicoEstagioRequestDTO requestDTO) throws Exception {

        OrdemServicoEstagio model = new OrdemServicoEstagio();

        PropertyMap<OrdemServicoEstagioRequestDTO, OrdemServicoEstagio> propertyMap = new PropertyMap<OrdemServicoEstagioRequestDTO, OrdemServicoEstagio>() {
            @Override
            protected void configure() {
                skip(destination.getId());
                skip(destination.getOrdemServico());
                skip(destination.getTermino());
            }
        };

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addMappings(propertyMap);
        modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
        modelMapper.map(requestDTO, model);

        if (requestDTO.getDetalhamento() != null && !requestDTO.getDetalhamento().trim() .equals("")) {
            model.setDetalhamento(requestDTO.getDetalhamento().trim());
        } else {
            throw new Exception("Informe o detalhamento.");
        }

        if (requestDTO.getTerminou() != null && requestDTO.getTerminou().equals("S")) {
            model.setTermino(LocalDateTime.now());
        }

        return model;
    }


    public OrdemServicoEstagio update(OrdemServicoEstagio modelOld, OrdemServicoEstagioRequestDTO requestDTO) throws Exception {

        PropertyMap<OrdemServicoEstagioRequestDTO, OrdemServicoEstagio> propertyMap = new PropertyMap<OrdemServicoEstagioRequestDTO, OrdemServicoEstagio>() {
            @Override
            protected void configure() {
                skip(destination.getId());
                skip(destination.getOrdemServico());
                skip(destination.getTermino());
            }
        };

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addMappings(propertyMap);
        modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
        modelMapper.map(requestDTO, modelOld);

        if (requestDTO.getDetalhamento() != null && !requestDTO.getDetalhamento().trim().equals("")) {
            modelOld.setDetalhamento(requestDTO.getDetalhamento().trim());
        } else {
            throw new Exception("Informe o detalhamento.");
        }

        if (requestDTO.getTerminou() != null && requestDTO.getTerminou().equals("S")) {
            modelOld.setTermino(LocalDateTime.now());
        }

        return modelOld;
    }

    public Optional<OrdemServicoEstagio> findById(Long id) {
        return repository.findById(id);
    }
}
