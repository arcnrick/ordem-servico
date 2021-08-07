package com.crianto.ordemservico.controller;

import com.crianto.ordemservico.config.RestControllerPath;
import com.crianto.ordemservico.dto.OrdemServicoDTO;
import com.crianto.ordemservico.dto.OrdemServicoRequestDTO;
import com.crianto.ordemservico.filter.OrdemServicoFiltro;
import com.crianto.ordemservico.model.OrdemServico;
import com.crianto.ordemservico.service.OrdemServicoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(path = RestControllerPath.OS_PATH)
@Api(tags = "Ordem de Serviço", description = "ordens de serviço")
public class OrdemServicoController {

    @Autowired
    private OrdemServicoService service;

    @PostMapping()
    @ApiOperation(value = "Cria uma nova OS")
    public ResponseEntity<OrdemServico> create(
            @RequestBody @Valid OrdemServicoRequestDTO ordemServico, HttpServletResponse response) throws Exception {

        OrdemServico model = service.create(ordemServico);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
                .buildAndExpand(model.getId()).toUri();
        response.setHeader("Location", uri.toASCIIString());

        return ResponseEntity.created(uri).body(model);
    }

    @GetMapping()
    @ApiOperation(value = "Retorna lista de OSs")
    public List<OrdemServico> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Retorna uma OS através do identificador")
    public ResponseEntity<OrdemServico> findById(
            @ApiParam(value = "Id requerido", required = true) @PathVariable Long id) {
        return ResponseEntity.ok().body(service.findById(id));
    }

    @GetMapping("/filterDTO")
    @ApiOperation(value = "Retorna lista de OSs filtrada")
    public Page<OrdemServicoDTO> findByFilterDTO(
            @PageableDefault(page = 0, value = Integer.MAX_VALUE) Pageable pageable, OrdemServicoFiltro filtro) {
        return service.findByFilterDTO(pageable, filtro);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Atualiza uma OS através do identificador")
    public ResponseEntity<OrdemServico> update(
            @ApiParam(value = "Identificador requerido", required = true) @PathVariable Long id,
            @Valid @RequestBody OrdemServicoRequestDTO ordemServico) throws Exception {
        return ResponseEntity.ok(service.update(id, ordemServico));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Remove uma OS através do identificador")
    public void remove(
            @ApiParam(value = "Identificador requerido", required = true) @PathVariable Long id) {
        service.delete(id);
    }
}