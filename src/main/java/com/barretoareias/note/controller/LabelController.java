package com.barretoareias.note.controller;

import javax.validation.Valid;

import com.barretoareias.note.con.StringCon;
import com.barretoareias.note.dto.LabelDTO;
import com.barretoareias.note.dto.request.LabelPostRequest;
import com.barretoareias.note.dto.request.LabelPutRequest;
import com.barretoareias.note.exception.LabelAlreadyRegisteredException;
import com.barretoareias.note.exception.LabelNotFoundException;
import com.barretoareias.note.service.LabelService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping(StringCon.apiPath+"/label")
@RequiredArgsConstructor
public class LabelController {
    
    private final LabelService service;

    @PostMapping
    private LabelDTO saveLabel(@RequestBody @Valid LabelPostRequest request)throws LabelAlreadyRegisteredException{
        return service.saveLabel(request);
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<Void> deleteLabel(@PathVariable Long id) throws LabelNotFoundException{
        service.deleteById(id);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}")
    public LabelDTO getLabelById(@PathVariable Long id) throws LabelNotFoundException{
        return service.findById(id);
    }

    @PutMapping
    public LabelDTO updateLabel(@RequestBody @Valid LabelPutRequest request) throws LabelNotFoundException{
        return service.updateLabel(request);
    }

    @GetMapping
    public ResponseEntity<List<LabelDTO>> getLabels(){
        var list = service.findLabels();
        if(list.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<LabelDTO>>(list,HttpStatus.OK);
    }
    
}
