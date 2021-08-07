package com.crianto.ordemservico.controller;

import com.crianto.ordemservico.config.RestControllerPath;
import com.crianto.ordemservico.model.Marca;
import com.crianto.ordemservico.service.MarcaService;
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
@RequestMapping(path = RestControllerPath.MARCA_PATH)
@Api(tags = "Marca", description = "marcas")
public class MarcaController {

    @Autowired
    private MarcaService service;

    @PostMapping()
    @ApiOperation(value = "Cria uma nova marca")
    public ResponseEntity<Marca> create(
            @RequestBody @Valid Marca marca, HttpServletResponse response) {

        Marca model = service.create(marca);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
                .buildAndExpand(model.getId()).toUri();
        response.setHeader("Location", uri.toASCIIString());

        return ResponseEntity.created(uri).body(model);
    }

    @GetMapping()
    @ApiOperation(value = "Retorna lista de marcas")
    public List<Marca> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Retorna uma marca através do identificador")
    public ResponseEntity<Marca> findById(
            @ApiParam(value = "Id requerido", required = true) @PathVariable Long id) {
        return ResponseEntity.ok().body(service.findById(id));
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Atualiza uma marca através do identificador")
    public ResponseEntity<Marca> update(
            @ApiParam(value = "Identificador requerido", required = true) @PathVariable Long id,
            @Valid @RequestBody Marca marca) {
        return ResponseEntity.ok(service.update(id, marca));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Remove uma marca através do identificador")
    public void remove(
            @ApiParam(value = "Identificador requerido", required = true) @PathVariable Long id) {
        service.delete(id);
    }
}