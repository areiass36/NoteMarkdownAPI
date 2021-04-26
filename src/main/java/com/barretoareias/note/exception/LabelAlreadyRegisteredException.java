package com.barretoareias.note.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST,
reason = "Label already registered")
public class LabelAlreadyRegisteredException extends Exception{

    private static final long serialVersionUID = 1L;

    public LabelAlreadyRegisteredException(Long id) {
        super(String.format("Label with id %s already registered",id));
    }

    public LabelAlreadyRegisteredException(String name) {
        super(String.format("Label with name %s already registered",name));
    }
    
}
