package com.aula.exception.handler.projetoexception.entity.cliente.dto;


public record ClienteResponseDto(
        Long id,
        String nome,
        String email,
        String cpf
) {

}
