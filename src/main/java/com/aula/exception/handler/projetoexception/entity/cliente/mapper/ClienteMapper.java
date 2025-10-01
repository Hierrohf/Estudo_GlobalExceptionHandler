package com.aula.exception.handler.projetoexception.entity.cliente.mapper;


import com.aula.exception.handler.projetoexception.entity.cliente.Cliente;
import com.aula.exception.handler.projetoexception.entity.cliente.dto.ClienteCriadoResponseDto;
import com.aula.exception.handler.projetoexception.entity.cliente.dto.ClienteRequestDto;
import com.aula.exception.handler.projetoexception.entity.cliente.dto.ClienteResponseDto;

import java.net.URI;

public class ClienteMapper {

    public static ClienteResponseDto toDto(Cliente cliente){
        return new ClienteResponseDto(cliente.getId(), cliente.getNome(), cliente.getEmail(), cliente.getCpf());
    }

    public static Cliente toCliente(ClienteRequestDto dto){
        return new Cliente(dto.nome(), dto.email(), dto.cpf());
    }

    public static ClienteCriadoResponseDto clienteCriadoResponseDto(ClienteResponseDto dto, URI uri){
        return new ClienteCriadoResponseDto(dto.id(), dto.nome(), dto.email(), dto.cpf(), uri);
    }
}
