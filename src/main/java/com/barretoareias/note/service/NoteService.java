package com.barretoareias.note.service;

import com.barretoareias.note.dto.NoteDTO;
import com.barretoareias.note.dto.request.NotePatchRequest;
import com.barretoareias.note.dto.request.NotePostRequest;
import com.barretoareias.note.dto.request.NotePutRequest;
import com.barretoareias.note.entity.Label;
import com.barretoareias.note.exception.LabelNotFoundException;
import com.barretoareias.note.exception.NoteAlreadyRegisteredException;
import com.barretoareias.note.exception.NoteNotFoundException;

import java.util.List;
import java.util.Set;

public interface NoteService {

    NoteDTO saveNote(NotePostRequest request) throws NoteAlreadyRegisteredException, LabelNotFoundException;
    
    NoteDTO patchNote(NotePatchRequest request) throws NoteNotFoundException, LabelNotFoundException, NoteAlreadyRegisteredException;

    NoteDTO updateNote(NotePutRequest request) throws NoteNotFoundException, LabelNotFoundException, NoteAlreadyRegisteredException;

    List<NoteDTO> findNotes();

    NoteDTO findById(Long id) throws NoteNotFoundException;

    void deleteById(Long id) throws NoteNotFoundException;
}
