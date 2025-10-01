package com.aula.exception.handler.projetoexception.exception.business;

public class NomeJaCadastradoException extends RuntimeException{

    public NomeJaCadastradoException(String nome){
        super("O Nome "+ nome +" já está cadastrado no sistema.");
    }
}
