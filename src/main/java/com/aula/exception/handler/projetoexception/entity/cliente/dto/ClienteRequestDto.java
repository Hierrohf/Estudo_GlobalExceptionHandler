package com.aula.exception.handler.projetoexception.entity.cliente.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record ClienteRequestDto(
        @NotBlank(message = "não pode ser vazio")
        String nome,

        @NotBlank(message = "não pode ser vazio")
        @Email(message = "não está no formato válido")
        String email,

        @Pattern(
                regexp = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}",
                message = "deve estar no formato 000.000.000-00"
        )
        @NotBlank
        String cpf
) {
}
