package com.aula.exception.handler.projetoexception.exception.message;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ErrorMessage {

    private Integer status;
    private LocalDateTime timestamp = LocalDateTime.now();
    private List<String> Messages;

    public ErrorMessage(Integer status, String Messages) {
        this.status = status;
        this.Messages = List.of(Messages);// transforma em lista de 1 elemento
    }

    public ErrorMessage(Integer status, List<String> Messages) {
        this.status = status;
        this.Messages = Messages;
    }
}
