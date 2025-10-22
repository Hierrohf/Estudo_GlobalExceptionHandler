package com.aula.exception.handler.projetoexception.util;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.ArrayList;

public class FormatError {

    private FormatError(){
        throw new UnsupportedOperationException("Classe utilitária, não deve ser instanciada!");
    }

//    public static String formatError(String msg){
//        return String.format(
//                "'%s' Verifique os dados enviados e tente novamente.", msg);
//
//    }

    public static String formatErrorDetalhe(HttpServletRequest request){
        return String.format("URI: '%s' | Método: '%s' | Query: '%s'.",
                request.getRequestURI(),
                request.getMethod(),
                request.getQueryString() != null ? request.getQueryString() : "Nenhuma");
    }

    public static ArrayList<String> formatErrorBadRequest(Exception ex){
        var errorMessages = new ArrayList<String>();

        switch (ex) {
            case MethodArgumentNotValidException methodArgumentNotValidEx ->
                methodArgumentNotValidEx.getBindingResult().getFieldErrors().forEach(e ->
                        errorMessages.add(String.format("O Campo: '%s' '%s'.",
                                e.getField(),
                                e.getDefaultMessage())));
            case ConstraintViolationException constraintViolationEx ->
                constraintViolationEx.getConstraintViolations().forEach(e ->
                        errorMessages.add(String.format("O parâmetro: '%s' '%s'.",
                                e.getPropertyPath(),
                                e.getMessage())));
            case IllegalArgumentException illegalArgumentEx ->
                errorMessages.add("Um ou mais Parâmetros informados são inválidos.");
            case DataIntegrityViolationException dataIntegrityViolationEx ->
                errorMessages.add("A operação não pôde ser concluída pois viola regras de integridade do banco de dados.");
            case HttpMessageNotReadableException httpMessageNotReadableEx ->
                errorMessages.add("O corpo da requisição está inválido ou em formato incorreto.");

            default -> errorMessages.add("Ocorreu um erro inesperado durante a validação.");
        }
        return errorMessages;
    }

    public static String formatErrorNotFound(Exception ex){
        String errorMsg;

        switch (ex){
            case NoHandlerFoundException noHandlerFoundEx ->
                errorMsg = String.format("Recurso não encontrado: método '%s' não existe para o caminho '%s'.",
                        noHandlerFoundEx.getHttpMethod(),
                        noHandlerFoundEx.getRequestURL());
            case EntityNotFoundException entityNotFoundEx ->
                errorMsg = entityNotFoundEx.getMessage() != null ?
                        entityNotFoundEx.getMessage() : "A entidade solicitada não foi encontrada.";
            case EmptyResultDataAccessException emptyResultDataAccessEx ->
                errorMsg = emptyResultDataAccessEx.getMessage() != null ?
                        emptyResultDataAccessEx.getMessage() : "Nenhum registro foi encontrado para a operação solicitada.";
            default -> errorMsg = "A requisição enviada não pôde ser processada. " +
                    "Verifique o método HTTP e o tipo de conteúdo utilizados e tente novamente.";
        }
        return errorMsg;
    }

    public static String formatErrorHttpProtocol(Exception ex){
        String errorMsg;
        String suported;

        switch (ex){
            case HttpRequestMethodNotSupportedException methodNotAllowedEx ->{
                suported = methodNotAllowedEx.getSupportedMethods() != null ?
                        String.join(", ", methodNotAllowedEx.getSupportedMethods()) : "Nenhum Metodo suportado";
                errorMsg = String.format(
                        "Método HTTP não suportado: '%s' \n" +
                                "Métodos suportados '%s'",
                        methodNotAllowedEx.getMethod(),
                        suported);
            }
            case HttpMediaTypeNotSupportedException mediaTypeNotSupportedEx ->{
                suported = String.join(", ",
                        mediaTypeNotSupportedEx.getSupportedMediaTypes().stream().map(Object::toString).toList());
                errorMsg = String.format("Content-Type não suportado: '%s' \n" +
                                "Content-Types suportados: '%s'",
                        mediaTypeNotSupportedEx.getContentType(),
                        suported);

            }
            default -> errorMsg = "A requisição enviada não pôde ser processada. " +
                    "Verifique o método HTTP e o tipo de conteúdo utilizados e tente novamente.";

        }
        return errorMsg;
    }
}
