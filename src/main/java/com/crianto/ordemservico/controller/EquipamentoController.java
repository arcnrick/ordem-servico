package com.crianto.ordemservico.controller;

import com.crianto.ordemservico.config.RestControllerPath;
import com.crianto.ordemservico.model.Equipamento;
import com.crianto.ordemservico.service.EquipamentoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(path = RestControllerPath.EQUIPAMENTO_PATH)
@Api(tags = "Equipamento", description = "equipamentos")
public class EquipamentoController {

    @Autowired
    private EquipamentoService service;

    @PostMapping()
    @ApiOperation(value = "Cria um novo equipamento")
    public ResponseEntity<Equipamento> create(
            @RequestBody @Valid Equipamento equipamento, HttpServletResponse response) throws Exception {

        Equipamento model = service.create(equipamento);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
                .buildAndExpand(model.getId()).toUri();
        response.setHeader("Location", uri.toASCIIString());

        return ResponseEntity.created(uri).body(model);
    }

    @GetMapping()
    @ApiOperation(value = "Retorna lista de equipamentos")
    public List<Equipamento> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Retorna um equipamento através do identificador")
    public ResponseEntity<Equipamento> findById(
            @ApiParam(value = "Id requerido", required = true) @PathVariable Long id) {
        return ResponseEntity.ok().body(service.findById(id));
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Atualiza um equipamento através do identificador")
    public ResponseEntity<Equipamento> update(
            @ApiParam(value = "Identificador requerido", required = true) @PathVariable Long id,
            @Valid @RequestBody Equipamento equipamento) throws Exception {
        return ResponseEntity.ok(service.update(id, equipamento));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Remove um equipamento através do identificador")
    public void remove(
            @ApiParam(value = "Identificador requerido", required = true) @PathVariable Long id) {
        service.delete(id);
    }
}