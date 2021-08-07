package com.crianto.ordemservico.repository;


import com.crianto.ordemservico.dto.OrdemServicoDTO;
import com.crianto.ordemservico.filter.OrdemServicoFiltro;
import com.crianto.ordemservico.model.OrdemServico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

public class OrdemServicoRepositoryImpl implements OrdemServicoRepositoryQuery {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public Page<OrdemServicoDTO> findByFilterDTO(Pageable pageable, OrdemServicoFiltro filtro) {
        CriteriaBuilder cb = manager.getCriteriaBuilder();
        CriteriaQuery<OrdemServicoDTO> criteriaQuery = cb.createQuery(OrdemServicoDTO.class);
        Root<OrdemServico> root = criteriaQuery.from(OrdemServico.class);
        criteriaQuery.select(cb.construct(OrdemServicoDTO.class, root)).groupBy(root.get("id"));

        Predicate[] predicates = createRestrictions(filtro, cb, root);
        criteriaQuery.where(predicates);
        criteriaQuery.orderBy(orderList(cb, root));

        // Cria a SQL query
        TypedQuery<OrdemServicoDTO> query = manager.createQuery(criteriaQuery);
        adicionarRestricoesDePaginacao(query, pageable);
        return new PageImpl<>(query.getResultList(), pageable, total(filtro));
    }

    private List<Order> orderList(CriteriaBuilder cb, Root<OrdemServico> root) {
        List<Order> ord = new ArrayList<>();
        ord.add(cb.desc(root.get("id")));
        return ord;
    }

    private Long total(OrdemServicoFiltro filtro) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
        Root<OrdemServico> root = criteria.from(OrdemServico.class);
        Predicate[] predicates = createRestrictions(filtro, builder, root);
        criteria.where(predicates);
        criteria.select(builder.count(root));
        return manager.createQuery(criteria).getSingleResult();
    }

    private void adicionarRestricoesDePaginacao(TypedQuery<OrdemServicoDTO> query, Pageable pageable) {
        int paginaAtual = pageable.getPageNumber();
        int totalRegistrosPorPagina = pageable.getPageSize();
        int primeiroRegistroDaPagina = paginaAtual * totalRegistrosPorPagina;
        query.setFirstResult(primeiroRegistroDaPagina);
        query.setMaxResults(totalRegistrosPorPagina);
    }

    private Predicate[] createRestrictions(OrdemServicoFiltro filtro, CriteriaBuilder cb, Root<OrdemServico> root) {
        List<Predicate> predicates = new ArrayList<>();
        Predicate condition;

        if (filtro.getId() != null && filtro.getId() > 0L) {
            condition = cb.equal(root.get("id"), filtro.getId());
            predicates.add(condition);
        }

        if (filtro.getDataAbertura() != null) {
            condition = cb.equal(root.get("dataAbertura"), filtro.getDataAbertura());
            predicates.add(condition);
        }

        if (filtro.getClienteId() != null && filtro.getClienteId() > 0L) {
            condition = cb.equal(root.get("clienteId"), filtro.getClienteId());
            predicates.add(condition);
        }

        if (filtro.getEquipamentoId() != null && filtro.getEquipamentoId() > 0L) {
            condition = cb.equal(root.get("equipamentoId"), filtro.getEquipamentoId());
            predicates.add(condition);
        }

        if (!StringUtils.isEmpty(filtro.getProblemaApresentado())) {
            condition = cb.like(root.get("problemaApresentado"), "%" + filtro.getProblemaApresentado().trim().toUpperCase() + "%");
            predicates.add(condition);
        }

        if (filtro.getDataConclusao() != null) {
            condition = cb.equal(root.get("dataConclusao"), filtro.getDataConclusao());
            predicates.add(condition);
        }

        if (!StringUtils.isEmpty(filtro.getEstagio())) {
            condition = cb.equal(root.get("estagio"), filtro.getEstagio());
            predicates.add(condition);
        }

        return predicates.toArray(new Predicate[predicates.size()]);
    }
}