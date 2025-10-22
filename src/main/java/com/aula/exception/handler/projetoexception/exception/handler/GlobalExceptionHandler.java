package com.aula.exception.handler.projetoexception.exception.handler;

import com.aula.exception.handler.projetoexception.exception.ApplicationException;
import com.aula.exception.handler.projetoexception.exception.message.ErrorMessage;
import com.aula.exception.handler.projetoexception.util.FormatError;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.ArrayList;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ErrorMessage> handlerApplicationException(ApplicationException ex, HttpServletRequest request) {
        var status = ex.getStatus();
        var message = ex.getMessage();
        var detalhes = FormatError.formatErrorDetalhe(request);

        return ResponseEntity.status(status).body(new ErrorMessage(status.value(), message, detalhes));
    }

    @ExceptionHandler({
            MethodArgumentNotValidException.class,//estoura nas validacoes do dto
            ConstraintViolationException.class,//estoura na validacao do @Valid nos parametros dos metodos
            IllegalArgumentException.class,//estoura quando um metodo recebe um argumento invalido em seu parametro
            DataIntegrityViolationException.class,//quebra de integridade no banco tipo coluna unique ou FK etc
            HttpMessageNotReadableException.class//corpo da requisicao invalido (JSON mal formatado)
    })
    public ResponseEntity<ErrorMessage> handleBadRequest(Exception ex, HttpServletRequest request) {
        var status = HttpStatus.BAD_REQUEST;
        var detalhes = FormatError.formatErrorDetalhe(request);
        var errorMessages = new ArrayList<String>(FormatError.formatErrorBadRequest(ex));

        return ResponseEntity.status(status).body(new ErrorMessage(status.value(), errorMessages, detalhes));
    }

    @ExceptionHandler({
            NoHandlerFoundException.class,//rota nao encontrada
            EntityNotFoundException.class,//entidade nao existe no banco
            EmptyResultDataAccessException.class})
//acesso a registro no banco inexistente | acessar, atualizar ou deletar um registro inexistente
    public ResponseEntity<ErrorMessage> handelerNotFound(Exception ex, HttpServletRequest request) {//HttpServletRequest: representa a requisicao http que chegouno seu servidor cm todas info que o cliente mandou
        var status = HttpStatus.NOT_FOUND;
        String errorMsg = FormatError.formatErrorNotFound(ex);
        var detalhes = FormatError.formatErrorDetalhe(request);

        return ResponseEntity.status(status).body(new ErrorMessage(status.value(), errorMsg, detalhes));
    }


    @ExceptionHandler({
            HttpRequestMethodNotSupportedException.class,//cliente tentou acessar um recurso com um metodo que o endpont nao suporta
            HttpMediaTypeNotSupportedException.class//tipo de midia(content-type) que o servidor nao aceita
    })
    public ResponseEntity<ErrorMessage> handleHttpProtocolErrors(Exception ex, HttpServletRequest request) {
        var status = HttpStatus.BAD_REQUEST;
        String errorMsg = FormatError.formatErrorHttpProtocol(ex);
        var detalhes = FormatError.formatErrorDetalhe(request);

        return ResponseEntity.status(status).body(new ErrorMessage(status.value(), errorMsg, detalhes));
    }

    @ExceptionHandler(Exception.class)//exception generica captura qualquer erro nao tratado
    public ResponseEntity<ErrorMessage> handlerException(Exception ex, HttpServletRequest request) {
        log.error("erro inesperado, ", ex);//n pode ir para producao, isso mostra o log d erro para q o dev possa rastrear a exception
        var status = HttpStatus.INTERNAL_SERVER_ERROR;
        var detalhes = FormatError.formatErrorDetalhe(request);

        return ResponseEntity.status(status).body(new ErrorMessage(status.value(),
                "Ocorreu um erro inesperado. Tente novamente mais tarde.", detalhes));
    }
}
    /*utilizar o @ExceptionHandler passando a class de erro MethodArgumentNotValidException
     * o metodo deve ser publico
     * devolver um ResponseEntity do tipo ErrorMessage
     * escolher nome do metodo
     * passar nos parametros o erro MethodArgumentNotValidException
     * {
     * criar um var status pra receber o status do erro
     * passar o HttpStatus correto para a var status
     * criar uma array de string para guardar as mensagens errorMessages para retornar para o cliente
     * criar um var list "fieldErrors" para receber as msg de erro
     * chamar o objeto de erro "BindingResult" e pegar somente os campos|atributos
     * passar o retorno para o fieldErrors
     *
     * percorrer o fieldErrors para pegar cada campo e msg de erro do campo
     * criar uma var string pra montar a msg de erro para o cliente
     * montar a string
     * pegar o campo com .getField()
     * pegar a msg de erro padrao com .getDefaultMessage()
     * }*/


//    @ExceptionHandler(MethodArgumentNotValidException.class)//quando viola as regras do requestDto
//    public ResponseEntity<ErrorMessage> handlerArgumentoInvalido(MethodArgumentNotValidException ex){
//        var status = HttpStatus.BAD_REQUEST;
//        var errorMessages = new ArrayList<String>();
//        var fieldErrors = ex.getBindingResult().getFieldErrors();
//
//        fieldErrors.forEach(e -> {
//            var msg = "O campo: " + e.getField() + " " + e.getDefaultMessage();
//            errorMessages.add(msg);
//        });
//        return ResponseEntity.status(status).body(new ErrorMessage(status.value(), errorMessages));
//    }

//    @ExceptionHandler(NoHandlerFoundException.class)//quando o recurso nao e encontrado
//    public ResponseEntity<ErrorMessage> handlerNoHandlerFound(NoHandlerFoundException ex){
//        var status = HttpStatus.NOT_FOUND;
//        return ResponseEntity.status(status).body(new ErrorMessage(status.value(), "Recurso não encontrado"));
//    }

    /*
     * o erro NoHandlerFoundException nao dispara por padrao no spring quando bate em uma rota que nao existe,
     * o spring por padrao retorna direto o erro 404 padrao no container (tomcat).
     * entao se a exception acontecer ela nao vai cair no meu metodo
     * pra esse codigo rodar, precisa habilitar o spring pra lancar a exception ao inves de devolver o 404 automatico.
     * .properties:
     *     spring.mvc.throw-exception-if-no-handler-found=true -> faz o spring lancar o NoHandlerFoundException
     *     spring.web.resources.add-mappings=false -> evita que ele tente servir arquivos estaticos
     */


//    @ExceptionHandler(ConstraintViolationException.class)//violacao dos parametros passados na url
//    public ResponseEntity<ErrorMessage> handlerConstraintViolation(ConstraintViolationException ex){
//        HttpStatus status = HttpStatus.BAD_REQUEST;
//        var errorMessages = new ArrayList<String>();
//        var fieldErrors = ex.getConstraintViolations();
//
//        fieldErrors.forEach(e -> {
//            String msg = "O parâmetro " + e.getPropertyPath() + " " + e.getMessage();
//            errorMessages.add(msg);
//        });
//        return ResponseEntity.status(status).body(new ErrorMessage(status.value(), errorMessages));
//    }

//    @ExceptionHandler(DataIntegrityViolationException.class)//quando ocorre quebra de integridade no banco como colunas que sao unique
//    public ResponseEntity<ErrorMessage> handlerDataIntegrityViolation(DataIntegrityViolationException ex){
//        var status = HttpStatus.BAD_REQUEST; // ou HttpStatus.CONFLICT dependendo da regra
//        return ResponseEntity.status(status).body(new ErrorMessage(status.value(), "Erro de integridade"));
//    }

//    @ExceptionHandler(EmptyResultDataAccessException.class)//quando tenta acessar algo que nao existe no banco
//    public ResponseEntity<ErrorMessage> handlerEmptyResultDataAccess(EmptyResultDataAccessException ex){
//        var status = HttpStatus.BAD_REQUEST;
//        return ResponseEntity.status(status).body(new ErrorMessage(status.value(),
//                "Recurso não encontrado.\nNenhum registro foi encontrado com o ID informado."));
//    }

//    @ExceptionHandler(Ent        var detalhes = FormatError.formatErrorDetalhe(request);
//ityNotFoundException.class)//quando tenta acessar uma entidade que nao existe no banco
//    public ResponseEntity<ErrorMessage> handlerEntityNotFound(EntityNotFoundException ex){
//        var status = HttpStatus.NOT_FOUND;
//        return ResponseEntity.status(status).body(new ErrorMessage(status.value(),
//                "Recurso não encontrado. " + ex.getMessage()));
//    }

//    @ExceptionHandler(HttpMessageNotReadableException.class)//quando ocorre problema ao desserializar o json enviado pelo cliente
//    public ResponseEntity<ErrorMessage> handlerHttpMessageNotReadable(HttpMessageNotReadableException ex){
//        var status = HttpStatus.BAD_REQUEST;
//        return ResponseEntity.status(status).body(new ErrorMessage(status.value(),
//                "Requisição inválida. Verifique o corpo da mensagem."));
//    }

//    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)//quando o cliente chama a api com o metodo HTTP errado
//    public ResponseEntity<ErrorMessage> handlerHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex) {
//        var status = HttpStatus.METHOD_NOT_ALLOWED;
//        var supported = ex.getSupportedMethods() != null ? String.join(", ", ex.getSupportedMethods()) : "Nenhum";
//        var errorMessage = "Metodo HTTP não suportado: " + ex.getMethod() + ". Métodos suportados: " + supported;
//        return ResponseEntity.status(status).body(new ErrorMessage(status.value(), errorMessage));
//    }

//    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)//quando o cliente envia um Content-Type nao suportado
//    public ResponseEntity<ErrorMessage> handlerHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex) {
//        var status = HttpStatus.UNSUPPORTED_MEDIA_TYPE;
//        var errorMessage = "Content-Type não suportado: " + ex.getContentType()
//                + ". Content-Types suportados: " + ex.getSupportedMediaTypes();
//        return ResponseEntity.status(status).body(new ErrorMessage(status.value(), errorMessage));
//    }

//    //ps:n vou implementar security por agr
//    @ExceptionHandler(AccessDeniedException.class)//usuario autenticado mas sem permissao para acessar o recurso
//    public ResponseEntity<ErrorMessage> handlerAccessDenied(AccessDeniedException ex, HttpServletRequest request) {
//        var status = HttpStatus.FORBIDDEN;
//        return ResponseEntity.status(status).body(new ErrorMessage(status.value(),
//                "Você não tem permissão para acessar este recurso."));
//    }


/*BindingResult: e um objeto que existe dentro de um erro, e como se fosse uma
 * caixa onde o spring guarda todos os erros de validacao
 * entao quando voce faz ex.getBindingResult(); voce esta chamando o objeto do erro
 *
 * DIFERENCA ENTRE getAllErrors() e getFieldErrors()
 * getAllErrors() -> pega todos os erros (pega tanto os de campo quanto os de validacao global)
 * getFieldErrors() -> pega so os erros nos atributos (ex: email, nome, idade)
 *
 * EXPLICACAO:
 * var fieldErrors = ex.getBindingResult().getFieldErrors()
 * # ( var fieldErrors ) : cria uma lista do tipo FieldError
 * # ( ex.getBindingResult() ): chama o objeto de erro (BindingResult)
 * # ( .getFieldErrors() ) : pega apenas os atributos do erro
 * e tudo isso e jogado para a lista ( var fieldErrors )
 */

/*EXPLICACAO
 *    fieldErrors.forEach(e -> {
 *        var msg = "O campo " + e.getField() + " " + e.getDefaultMessage();
 *        errorMessages.add(msg);
 *    });
 *    return ResponseEntity.status(status).body(new ErrorMessage(status.value(), errorMessages));
 */

/*# ( fieldErrors.forEach(e ->) ) : e uma expressao lambda que usa o forEach na lista
 * fieldErrors
 *
 * # ( var msg = "O campo " + e.getField() + " " + e.getDefaultMessage(); )
 * # ( var msg = ) : cria uma string
 * # ( "O campo " + e.getField() + " " + e.getDefaultMessage(); ) : constroi a string
 * e.getField() pega o campo|atributo (ex: nome, email)
 * e.getDefaultMessage() pega a msg de erro do campo
 */
