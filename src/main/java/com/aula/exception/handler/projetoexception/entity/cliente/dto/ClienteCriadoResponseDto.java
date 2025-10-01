package com.aula.exception.handler.projetoexception.entity.cliente.dto;

import java.net.URI;

public record ClienteCriadoResponseDto(
        Long id,
        String nome,
        String email,
        String cpf,
        URI uri
) {
}
