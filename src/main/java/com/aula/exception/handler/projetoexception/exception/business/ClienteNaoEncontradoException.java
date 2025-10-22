package com.aula.exception.handler.projetoexception.exception.business;

import com.aula.exception.handler.projetoexception.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class ClienteNaoEncontradoException extends ApplicationException {

    public ClienteNaoEncontradoException(Long id){
      super("Cliente com ID " + id + " não foi encontrado.",
              HttpStatus.NOT_FOUND);
  }
  public ClienteNaoEncontradoException(String nome){
      super("Cliente com Nome: "+nome+" não foi encontrado.",
              HttpStatus.NOT_FOUND);
  }
}
