package com.barretoareias.note.errorHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler{
    
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
        MethodArgumentNotValidException ex,
        HttpHeaders headers,
        HttpStatus status,
        WebRequest request
    ){
        var errors = new ArrayList<String>();
        for(var error : ex.getBindingResult().getFieldErrors()){
            errors.add(error.getField()+":"+error.getDefaultMessage());
        }
        for(var error : ex.getBindingResult().getGlobalErrors()){
            errors.add(error.getObjectName()+":"+error.getDefaultMessage());
        }
        var a = errors.toArray();
        var errorArray = Arrays.copyOf(a, a.length,String[].class);
        var apiError = new ApiError(HttpStatus.BAD_REQUEST, 
            ex.getLocalizedMessage(),
            errorArray);
        return handleExceptionInternal(ex,apiError,headers,apiError.getStatus(),request); 
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(
        MissingServletRequestParameterException ex, HttpHeaders headers, 
        HttpStatus status, WebRequest request) {
        String error = ex.getParameterName() + " parameter is missing";
        
        ApiError apiError = 
        new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), error);
        return new ResponseEntity<Object>(
        apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler({ ConstraintViolationException.class })
    public ResponseEntity<Object> handleConstraintViolation(
        ConstraintViolationException ex, WebRequest request) {
        List<String> errors = new ArrayList<String>();
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            errors.add(violation.getRootBeanClass().getName() + " " + 
            violation.getPropertyPath() + ": " + violation.getMessage());
        }

        var a = errors.toArray();
        var errorArray = Arrays.copyOf(a, a.length,String[].class);
        ApiError apiError = 
        new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errorArray);
        return new ResponseEntity<Object>(
        apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler({ MethodArgumentTypeMismatchException.class })
    public ResponseEntity<Object> handleMethodArgumentTypeMismatch(
        MethodArgumentTypeMismatchException ex, WebRequest request) {
        String error = 
        ex.getName() + " should be of type " + ex.getRequiredType().getName();

    ApiError apiError = 
      new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), error);
    return new ResponseEntity<Object>(
      apiError, new HttpHeaders(), apiError.getStatus());
}
}
