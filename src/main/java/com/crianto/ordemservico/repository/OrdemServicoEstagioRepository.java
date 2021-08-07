package com.crianto.ordemservico.repository;

import com.crianto.ordemservico.model.OrdemServicoEstagio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdemServicoEstagioRepository extends JpaRepository<OrdemServicoEstagio, Long> {

}
