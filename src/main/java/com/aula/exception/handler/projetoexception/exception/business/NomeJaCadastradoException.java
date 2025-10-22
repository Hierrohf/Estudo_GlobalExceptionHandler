package com.aula.exception.handler.projetoexception.exception.business;

import com.aula.exception.handler.projetoexception.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class NomeJaCadastradoException extends ApplicationException {

    public NomeJaCadastradoException(String nome){
        super("O Nome "+ nome +" já está cadastrado no sistema.",
                HttpStatus.CONFLICT);
    }
}
