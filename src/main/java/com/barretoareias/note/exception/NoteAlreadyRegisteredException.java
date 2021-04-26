package com.barretoareias.note.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST,
reason = "Note already registered")
public class NoteAlreadyRegisteredException extends Exception{

    private static final long serialVersionUID = 1L;

    public NoteAlreadyRegisteredException(Long id) {
        super(String.format("Note with id %s already registered",id));
    }

    public NoteAlreadyRegisteredException(String title) {
        super(String.format("Note with tile %s already registered",title));
    }
    
}
