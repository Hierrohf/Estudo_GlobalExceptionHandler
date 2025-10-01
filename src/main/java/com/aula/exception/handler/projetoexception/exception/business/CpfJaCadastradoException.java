package com.aula.exception.handler.projetoexception.exception.business;

public class CpfJaCadastradoException extends RuntimeException{

    public CpfJaCadastradoException(String cpf){
        super("O CPF: "+ cpf +" já está cadastrado no sistema.");
    }
}
