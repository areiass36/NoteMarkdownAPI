package com.barretoareias.note.controller;

import com.barretoareias.note.con.StringCon;
import com.barretoareias.note.dto.NoteDTO;
import com.barretoareias.note.dto.request.NotePatchRequest;
import com.barretoareias.note.dto.request.NotePostRequest;
import com.barretoareias.note.dto.request.NotePutRequest;
import com.barretoareias.note.entity.Label;
import com.barretoareias.note.entity.Note;
import com.barretoareias.note.repository.NoteRepository;
import com.barretoareias.note.exception.LabelNotFoundException;
import com.barretoareias.note.exception.NoteAlreadyRegisteredException;
import com.barretoareias.note.exception.NoteNotFoundException;
import com.barretoareias.note.service.NoteService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import java.util.List;

import javax.validation.Valid;

@RestController
@RequestMapping(StringCon.apiPath+"/note")
@RequiredArgsConstructor
public class NoteController {
    
    private final NoteService service;

    @DeleteMapping("/{id}")
    private ResponseEntity<Void> deleteById(@PathVariable Long id) throws NoteNotFoundException{
        service.deleteById(id);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

    @PostMapping
    private NoteDTO saveNote(@RequestBody @Valid NotePostRequest request) throws NoteAlreadyRegisteredException, LabelNotFoundException{
       return service.saveNote(request);
    }

    @PutMapping
    private NoteDTO updateNote(@RequestBody @Valid NotePutRequest request) throws NoteNotFoundException, LabelNotFoundException, NoteAlreadyRegisteredException{
        return service.updateNote(request);
    }

    @PatchMapping
    private NoteDTO patchNote(@RequestBody @Valid NotePatchRequest request) throws NoteNotFoundException, LabelNotFoundException, NoteAlreadyRegisteredException{
        return service.patchNote(request);
    }

    @GetMapping("/{id}")
    private NoteDTO findById(@PathVariable Long id) throws NoteNotFoundException{
        return service.findById(id);
    }

    @GetMapping
    private ResponseEntity<List<NoteDTO>> findAll(){
        var list = service.findNotes();
        if(list.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(list,HttpStatus.OK);
    }
}
