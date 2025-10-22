package com.aula.exception.handler.projetoexception.exception.business;

import com.aula.exception.handler.projetoexception.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class CpfJaCadastradoException extends ApplicationException {

    public CpfJaCadastradoException(String cpf){
        super("O CPF: "+ cpf +" já está cadastrado no sistema.",
                HttpStatus.CONFLICT);
    }
}
