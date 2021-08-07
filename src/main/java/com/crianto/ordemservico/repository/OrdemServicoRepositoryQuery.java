package com.crianto.ordemservico.repository;

import com.crianto.ordemservico.dto.OrdemServicoDTO;
import com.crianto.ordemservico.filter.OrdemServicoFiltro;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

public interface OrdemServicoRepositoryQuery {

    @Transactional(readOnly = true)
    Page<OrdemServicoDTO> findByFilterDTO(Pageable pageable, OrdemServicoFiltro filtro);
}
