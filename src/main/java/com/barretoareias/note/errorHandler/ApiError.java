package com.barretoareias.note.errorHandler;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;
import lombok.Data;

@Data
public class ApiError {
    
    private HttpStatus status;
    private String message;
    private List<String> errors;

    public ApiError(HttpStatus status, String message, String... errors){
        this.status = status;
        this.message = message;
        this.errors = Arrays.asList(errors);
    }
}
