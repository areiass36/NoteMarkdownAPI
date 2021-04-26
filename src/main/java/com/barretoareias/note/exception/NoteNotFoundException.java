package com.barretoareias.note.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND,
reason = "Could not find note")
public class NoteNotFoundException extends Exception{

    private static final long serialVersionUID = 1L;

    public NoteNotFoundException(String title) {
        super(String.format("Note with title %s was not found",title));
    }

    public NoteNotFoundException(Long id) {
        super(String.format("Note with id %s was not found",id));
    }
    
}
