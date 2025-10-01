package com.aula.exception.handler.projetoexception.exception.business;

public class ClienteNaoEncontradoException extends RuntimeException {
  public ClienteNaoEncontradoException(Long id){
      super("Cliente com ID " + id + " não foi encontrado.");
  }
  public ClienteNaoEncontradoException(String nome){
      super("Cliente com Nome: "+nome+" não foi encontrado.");
  }
}
