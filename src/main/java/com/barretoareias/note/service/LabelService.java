package com.barretoareias.note.service;

import com.barretoareias.note.dto.LabelDTO;
import com.barretoareias.note.dto.NoteDTO;
import com.barretoareias.note.dto.request.LabelPostRequest;
import com.barretoareias.note.dto.request.LabelPutRequest;
import com.barretoareias.note.exception.LabelAlreadyRegisteredException;
import com.barretoareias.note.exception.LabelNotFoundException;

import java.util.List;

public interface LabelService {
    
    LabelDTO saveLabel(LabelPostRequest request) throws LabelAlreadyRegisteredException;

    LabelDTO findById(Long id) throws LabelNotFoundException;

    LabelDTO findByName(String name) throws LabelNotFoundException;

    LabelDTO updateLabel(LabelPutRequest request) throws LabelNotFoundException;

    List<LabelDTO> findLabels();

    void deleteById(Long id) throws LabelNotFoundException;
}
