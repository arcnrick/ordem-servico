package com.crianto.ordemservico.service;

/**
 * Classe Service que contém todos os métodos utilizados pela Controller...
 * nela fazemos uso alguns métodos privados, Facade, funcoes e validações
 * antes de persistir a OS e seus estágios
 *
 * @autor Rick
 * @version 1.00
 * @since 07/08/2021
 */

import com.crianto.ordemservico.dto.OrdemServicoDTO;
import com.crianto.ordemservico.dto.OrdemServicoEstagioRequestDTO;
import com.crianto.ordemservico.dto.OrdemServicoRequestDTO;
import com.crianto.ordemservico.enums.ESTAGIO;
import com.crianto.ordemservico.filter.OrdemServicoFiltro;
import com.crianto.ordemservico.model.OrdemServico;
import com.crianto.ordemservico.model.OrdemServicoEstagio;
import com.crianto.ordemservico.repository.OrdemServicoRepository;
import org.hibernate.ObjectNotFoundException;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrdemServicoService {

    @Autowired
    private OrdemServicoRepository repository;

    @Autowired
    private EquipamentoService equipamentoService;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private OrdemServicoEstagioService ordemServicoEstagioService;

    /** Método que cria uma ordem de serviço através de uma request e retorna um objeto modal
     * @return OrdemServico
     * @param requestDTO request enviado pelo client com dados de cabeçalho e lista de estágios
     * */
    public OrdemServico create(OrdemServicoRequestDTO requestDTO) throws Exception {

        OrdemServico model = new OrdemServico();

        PropertyMap<OrdemServicoRequestDTO, OrdemServico> propertyMap = new PropertyMap<OrdemServicoRequestDTO, OrdemServico>() {
            @Override
            protected void configure() {
                skip(destination.getId());
                skip(destination.getCliente());
                skip(destination.getEquipamento());
                skip(destination.getDataConclusao());
                skip(destination.getEstagios());
            }
        };

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addMappings(propertyMap);
        modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
        modelMapper.map(requestDTO, model);

        if (requestDTO.getEquipamentoId() != null && requestDTO.getEquipamentoId() > 0L) {
            model.setEquipamento(equipamentoService.findById(requestDTO.getEquipamentoId()));
        } else {
            throw new Exception("Informe o equipamento.");
        }

        if (requestDTO.getClienteId() != null && requestDTO.getClienteId() > 0L) {
            model.setCliente(clienteService.findById(requestDTO.getClienteId()));
        } else {
            throw new Exception("Informe o cliente.");
        }

        if (validaExistenciaEstagios(requestDTO)) {
            List<OrdemServicoEstagio> lista = new ArrayList<>();

            for (OrdemServicoEstagioRequestDTO ordemServicoEstagioRequestDTO : requestDTO.getEstagios()) {

                OrdemServicoEstagio ordemServicoEstagio = ordemServicoEstagioService.create(ordemServicoEstagioRequestDTO);
                ordemServicoEstagio.setOrdemServico(model);
                lista.add(ordemServicoEstagio);

            }

            model.setEstagios(lista);
        } else {
            throw new Exception("Informe ao menos uma etapa realizada.");
        }

        if (requestDTO.getProblemaApresentado() == null || requestDTO.getProblemaApresentado().trim().equals("")) {
            throw new Exception("Informe o problema.");
        } else {
            model.setProblemaApresentado(requestDTO.getProblemaApresentado().toUpperCase());
        }

        if (requestDTO.getEstagio() != null) {
            if (estagioValido(requestDTO.getEstagio()).equals("")) {
                throw new Exception("Informe um estágio válido.");
            }

            model.setEstagio(ESTAGIO.toEnum(requestDTO.getEstagio()));

            if (informadoEstagioFinalizacao(model.getEstagio())) {
                model.setDataConclusao(LocalDateTime.now());
            }
        } else {
            throw new Exception("Informe o estágio.");
        }

        return repository.save(model);
    }

    /** Método que valida se existe ao menos uma etapa (estágio) na OS
     * @return boolean
     * @param requestDTO contém informação de lista de etapas já realizadas
     * */
    private boolean validaExistenciaEstagios(OrdemServicoRequestDTO requestDTO) {
        return requestDTO.getEstagios() != null && requestDTO.getEstagios().size() > 0;
    }

    /** Método que lista todas as ordens de serviço e as devolve em formato de objeto
     * @return List<OrdemServico>
     * */
    public List<OrdemServico> findAll() {
        return repository.findAll();
    }

    /** Método que retorna um objeto de ordem de serviço através do seu identificador
     * @return OrdemServico
     * @param id identificador informado pelo client que servirá de filtro na busca
     * */
    public OrdemServico findById(Long id) {
        Optional<OrdemServico> model = repository.findById(id);

        return model.orElseThrow(() -> new ObjectNotFoundException(
                id, "OS não encontrada."));
    }

    /** Método que faz atualização em um objeto de ordem de serviço.... as informações
     *      são validadas antes de persistir os dados
     * @return OrdemServico
     * @param id identificador informado pelo client para verificar existência da OS
     * @param requestDTO corpo com as alterações enviado pelo client
     * */
    public OrdemServico update(Long id, OrdemServicoRequestDTO requestDTO) throws Exception {
        OrdemServico modelOld = findById(id);

        verificaEstagioOS(modelOld);

        PropertyMap<OrdemServicoRequestDTO, OrdemServico> propertyMap = new PropertyMap<OrdemServicoRequestDTO, OrdemServico>() {
            @Override
            protected void configure() {
                skip(destination.getId());
                skip(destination.getCliente());
                skip(destination.getEquipamento());
                skip(destination.getDataConclusao());
                skip(destination.getEstagios());
            }
        };

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addMappings(propertyMap);
        modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
        modelMapper.map(requestDTO, modelOld);

        if (requestDTO.getEquipamentoId() != null && requestDTO.getEquipamentoId() > 0L) {
            modelOld.setEquipamento(equipamentoService.findById(requestDTO.getEquipamentoId()));
        } else {
            throw new Exception("Informe o equipamento.");
        }

        if (requestDTO.getClienteId() != null && requestDTO.getClienteId() > 0L) {
            modelOld.setCliente(clienteService.findById(requestDTO.getClienteId()));
        } else {
            throw new Exception("Informe o cliente.");
        }

        if (validaExistenciaEstagios(requestDTO)) {
            List<OrdemServicoEstagio> lista = new ArrayList<>();

            for (OrdemServicoEstagioRequestDTO ordemServicoEstagioRequestDTO : requestDTO.getEstagios()) {
                OrdemServicoEstagio ordemServicoEstagio;

                if (ordemServicoEstagioRequestDTO.getId() != null && ordemServicoEstagioRequestDTO.getId() > 0L) {
                    Optional<OrdemServicoEstagio> ordemServicoEstagioDoBD = ordemServicoEstagioService.findById(ordemServicoEstagioRequestDTO.getId());

                    if (ordemServicoEstagioDoBD.isPresent()) {
                        ordemServicoEstagio = ordemServicoEstagioService.update(ordemServicoEstagioDoBD.get(), ordemServicoEstagioRequestDTO);
                    } else {
                        ordemServicoEstagio = ordemServicoEstagioService.create(ordemServicoEstagioRequestDTO);
                    }
                } else {
                    ordemServicoEstagio = ordemServicoEstagioService.create(ordemServicoEstagioRequestDTO);
                }

                ordemServicoEstagio.setOrdemServico(modelOld);
                lista.add(ordemServicoEstagio);
            }

            modelOld.setEstagios(lista);
        } else {
            throw new Exception("Informe ao menos uma etapa realizada.");
        }

        if (requestDTO.getEstagio() != null) {
            if (estagioValido(requestDTO.getEstagio()).equals("")) {
                throw new Exception("Informe um estágio válido.");
            }

            modelOld.setEstagio(ESTAGIO.toEnum(requestDTO.getEstagio()));

            if (informadoEstagioFinalizacao(modelOld.getEstagio())) {
                modelOld.setDataConclusao(LocalDateTime.now());
            }
        } else {
            throw new Exception("Informe o estágio.");
        }


        if (requestDTO.getProblemaApresentado() == null || requestDTO.getProblemaApresentado().trim().equals("")) {
            throw new Exception("Informe o problema.");
        } else {
            modelOld.setProblemaApresentado(requestDTO.getProblemaApresentado().toUpperCase());
        }

        return repository.save(modelOld);
    }

    /** Método que faz verifica necessidade de colocar data de conclusão na OS
     * @return boolean
     * @param estagio é o estágio atual da OS
     * */
    private boolean informadoEstagioFinalizacao(ESTAGIO estagio) {
        return (estagio.equals(ESTAGIO.CANCELADO) || estagio.equals(ESTAGIO.CONCLUIDO));
    }

    /** Método que verifica se a OS permite alguma alteração, com base no estágio atual
     *      é feita validação para permitir ou não a alteração
     * @param modelOld OS carregada no banco de dados
     * */
    private void verificaEstagioOS(OrdemServico modelOld) throws Exception {
        if (modelOld.getDataConclusao() != null) {
            throw new Exception("OS já finalizada... Alteração não permitida.");
        }
    }

    /** Método que exclui OS do banco de dados... antes será verificada se a mesam existe
     *      através do identificador
     * @param id identificador utilizado para verificar existência da OS e também para excluí-la
     * */
    public void delete(Long id) {
        // valida se existe msm no banco
        OrdemServico modelOld = findById(id);

        repository.delete(modelOld);
    }

    /** Método que valida se o estágio que foi informado pelo client é existente dentre os enums
     * @param estagioStr estágio informado pelo cliente na requisição / envio
     * */
    public String estagioValido(String estagioStr) {
        for (ESTAGIO sn : ESTAGIO.values()) {
            if (sn.getEstagio().equals(estagioStr)) {
                return estagioStr;
            }
        }

        return "";
    }

    /** Método que traz lista de ordem de serviço através de filtros informados pelo client o retorno
     *      contido não será um objeto completo, mas sim uma lista páginada com resumo das OSs
     * @return Page<OrdemServicoDTO>
     * @param filtro filtros informados pelo client para diminuir retorno
     * @param pageable paginação já configurada que mostrará à implementação como tratar o page
     * */
    public Page<OrdemServicoDTO> findByFilterDTO(Pageable pageable, OrdemServicoFiltro filtro) {
        return repository.findByFilterDTO(pageable, filtro);
    }
}
