package com.aula.exception.handler.projetoexception.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
@Getter
public abstract class ApplicationException extends RuntimeException{

    private final HttpStatus status;

    protected ApplicationException(String mensagem, HttpStatus status){
        super(mensagem);
        this.status = status;
    }


}
/*essa class tem a funcao de centralizar o tratamento/comportamento de nossas custom exceptions e ao invez de fazer as
* herdar de RuntimeException ela herda de nossa class ApplicationException padronizando */

/*Essa class e uma base generica para todas as custom exceptions da aplicacao ela cria um modelo padrao de erro que todas
* vao seguir*/