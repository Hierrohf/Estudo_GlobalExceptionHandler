package com.aula.exception.handler.projetoexception.exception.business;

public class EmailJaCadastradoException extends RuntimeException{

    public EmailJaCadastradoException(String email){
        super("O Email: "+ email +" já está cadastrado no sistema.");
    }
}
