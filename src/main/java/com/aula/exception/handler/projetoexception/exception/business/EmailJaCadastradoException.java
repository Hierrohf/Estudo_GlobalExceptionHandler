package com.aula.exception.handler.projetoexception.exception.business;

import com.aula.exception.handler.projetoexception.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class EmailJaCadastradoException extends ApplicationException {

    public EmailJaCadastradoException(String email){
        super("O Email: "+ email +" já está cadastrado no sistema.",
                HttpStatus.CONFLICT);
    }
}
