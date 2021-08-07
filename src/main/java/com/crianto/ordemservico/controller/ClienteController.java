package com.crianto.ordemservico.controller;

import com.crianto.ordemservico.config.RestControllerPath;
import com.crianto.ordemservico.model.Cliente;
import com.crianto.ordemservico.service.ClienteService;
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
@RequestMapping(path = RestControllerPath.CLIENTE_PATH)
@Api(tags = "Cliente", description = "clientes")
public class ClienteController {

    @Autowired
    private ClienteService service;

    @PostMapping()
    @ApiOperation(value = "Cria um novo cliente")
    public ResponseEntity<Cliente> create(
            @RequestBody @Valid Cliente cliente, HttpServletResponse response) {

        Cliente model = service.create(cliente);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
                .buildAndExpand(model.getId()).toUri();
        response.setHeader("Location", uri.toASCIIString());

        return ResponseEntity.created(uri).body(model);
    }

    @GetMapping()
    @ApiOperation(value = "Retorna lista de clientes")
    public List<Cliente> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Retorna um cliente através do identificador")
    public ResponseEntity<Cliente> findById(
            @ApiParam(value = "Id requerido", required = true) @PathVariable Long id) {
        return ResponseEntity.ok().body(service.findById(id));
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Atualiza um cliente através do identificador")
    public ResponseEntity<Cliente> update(
            @ApiParam(value = "Identificador requerido", required = true) @PathVariable Long id,
            @Valid @RequestBody Cliente cliente) {
        return ResponseEntity.ok(service.update(id, cliente));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Remove um cliente através do identificador")
    public void remove(
            @ApiParam(value = "Identificador requerido", required = true) @PathVariable Long id) {
        service.delete(id);
    }
}