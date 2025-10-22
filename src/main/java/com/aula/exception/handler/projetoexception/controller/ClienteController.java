package com.aula.exception.handler.projetoexception.controller;

import com.aula.exception.handler.projetoexception.entity.cliente.dto.ClienteCriadoResponseDto;
import com.aula.exception.handler.projetoexception.entity.cliente.dto.ClienteRequestDto;
import com.aula.exception.handler.projetoexception.entity.cliente.dto.ClienteResponseDto;
import com.aula.exception.handler.projetoexception.entity.cliente.mapper.ClienteMapper;
import com.aula.exception.handler.projetoexception.entity.cliente.service.ClienteServiceImpl;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteServiceImpl clienteService;

    public ClienteController(ClienteServiceImpl clienteService) {
        this.clienteService = clienteService;
    }

    @PostMapping
    public ResponseEntity<ClienteCriadoResponseDto> criar(@RequestBody @Valid ClienteRequestDto dto, UriComponentsBuilder builder){
        var clienteSalvo = clienteService.salvarCliente(dto);
        var uri = builder.path("/clientes/{id}").buildAndExpand(clienteSalvo.id()).toUri();
        var response = ClienteMapper.clienteCriadoResponseDto(clienteSalvo, uri);
        return ResponseEntity.created(uri).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponseDto> buscarPorId(@PathVariable @Min(0) Long id){
        var response = clienteService.buscarPorId(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<ClienteResponseDto>> buscarTodos(){
        var response = clienteService.buscarTodos();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ClienteResponseDto> deletarPorId(@PathVariable @Min(0) Long id){
        clienteService.deletarCliente(id);
        return ResponseEntity.noContent().build();
    }

}
