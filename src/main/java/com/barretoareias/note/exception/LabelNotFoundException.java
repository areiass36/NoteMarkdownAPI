package com.barretoareias.note.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND,
reason = "Could not find label")
public class LabelNotFoundException extends Exception{

    public LabelNotFoundException(String name) {
        super(String.format("Label with name %s was not found",name));
    }

    public LabelNotFoundException(Long id) {
        super(String.format("Label with id %s was not found",id));
    }

    public LabelNotFoundException(){
        super(String.format("Label was not found"));
    }

    private static final long serialVersionUID = 1L;
    
}
