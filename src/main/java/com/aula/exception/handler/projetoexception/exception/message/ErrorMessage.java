package com.aula.exception.handler.projetoexception.exception.message;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ErrorMessage {

    private Integer status;
    private LocalDateTime timestamp = LocalDateTime.now();
    private List<String> messages;
    private String detalhes;

    public ErrorMessage(Integer status, String messages) {
        this.status = status;
        this.messages = List.of(messages);// transforma em lista de 1 elemento
    }

    public ErrorMessage(Integer status, List<String> messages, String detalhes) {
        this.status = status;
        this.messages = messages;
        this.detalhes = detalhes;
    }

    public ErrorMessage(Integer status, String messages, String detalhes){
        this.status = status;
        this.messages = List.of(messages);
        this.detalhes = detalhes;
    }
}
