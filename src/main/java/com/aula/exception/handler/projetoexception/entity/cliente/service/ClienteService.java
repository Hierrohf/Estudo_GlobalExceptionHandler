package com.aula.exception.handler.projetoexception.entity.cliente.service;

import com.aula.exception.handler.projetoexception.entity.cliente.dto.ClienteRequestDto;
import com.aula.exception.handler.projetoexception.entity.cliente.dto.ClienteResponseDto;

import java.util.List;

public interface ClienteService {
    ClienteResponseDto salvarCliente(ClienteRequestDto dto);
    ClienteResponseDto buscarPorId(Long id);
    List<ClienteResponseDto> buscarTodos();
    ClienteResponseDto deletarCliente(Long id);
}
