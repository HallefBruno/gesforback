package com.gesforback.exception;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.Data;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 *
 * @author sud
 */
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleAll(Exception ex, WebRequest request) {
        List<String> errors = new ArrayList<>();
        ApiError apiError;
        
        if (ex instanceof NullPointerException) {
            errors.add("Ocorreu um erro no sistema!\nEntre em contato com o adminstrador.");
            apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), errors);
            return handleExceptionInternal(ex,apiError, new HttpHeaders(), apiError.getStatus(),request);
            
        } else if (ex instanceof DataIntegrityViolationException) {
            String msg = ((DataIntegrityViolationException) ex).getMostSpecificCause().getMessage();
            errors.add(msg);
            apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage(), errors);
            return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());            
        } else if(ex instanceof NotFoundRuntimeException) {
            errors.add(ex.getLocalizedMessage());
            apiError = new ApiError(HttpStatus.NOT_FOUND, ex.getMessage(), errors);
            return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
            
        } else if(ex instanceof NonNullRuntimeException) {
            errors.add(ex.getLocalizedMessage());
            apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage(), errors);
            return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
            
        } else if(ex instanceof NegocioException) {
            errors.add(ex.getLocalizedMessage());
            apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage(), errors);
            return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
        }
        
        errors.add("Ocorreu um erro no sistema!\nEntre em contato com o adminstrador.");
        apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), errors);
        return handleExceptionInternal(ex,apiError, new HttpHeaders(), apiError.getStatus(),request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        StringBuilder builder = new StringBuilder();
        builder.append(ex.getMethod());
        builder.append(" método não é compatível com esta solicitação. Os métodos suportados são ");
        ex.getSupportedHttpMethods().forEach(t -> builder.append(t).append(" "));
        ApiError apiError = new ApiError(HttpStatus.METHOD_NOT_ALLOWED,ex.getLocalizedMessage(), builder.toString());
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    public ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex, WebRequest request) {
        String error = ex.getName() + " deve ser do tipo " + ex.getRequiredType().getName();
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), error);
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }
    
    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex,HttpHeaders headers,HttpStatus status, WebRequest request) {
        StringBuilder builder = new StringBuilder();
        builder.append(ex.getContentType());
        builder.append(" tipo de mídia não é compatível. Os tipos de mídia suportados são ");
        ex.getSupportedMediaTypes().forEach(t -> builder.append(t).append(", "));
        ApiError apiError = new ApiError(HttpStatus.UNSUPPORTED_MEDIA_TYPE, ex.getLocalizedMessage(), builder.substring(0, builder.length() - 2));
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), "JSON parse error");
        return handleExceptionInternal(ex, apiError, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<FieldMessage> errors = new ArrayList<>();
        ex.getBindingResult().getFieldErrors().forEach((error) -> {
            errors.add(new FieldMessage(error.getField(), error.getDefaultMessage()));
        });
        return handleExceptionInternal(ex, errors, headers, HttpStatus.BAD_REQUEST, request);
    }
    
    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String error = ex.getParameterName() + " parâmetro está faltando";
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), error);
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }
    
    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String error = "Nenhum manipulador encontrado para " + ex.getHttpMethod() + " " + ex.getRequestURL();
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, ex.getLocalizedMessage(), error);
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @Data
    public class ApiError {

        private HttpStatus status;
        private String message;
        private List<String> errors;
        
        public ApiError(HttpStatus status, String message, List<String> errors) {
            super();
            this.status = status;
            this.message = message;
            this.errors = errors;
        }

        public ApiError(HttpStatus status, String message, String error) {
            super();
            this.status = status;
            this.message = message;
            errors = Arrays.asList(error);
        }

    }
    
    @Data
    public class FieldMessage {
        
        private String field;
        private String message;

        public FieldMessage(String field, String message) {
            this.field = field;
            this.message = message;
        }
    }
}
