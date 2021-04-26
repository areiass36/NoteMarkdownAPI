package com.barretoareias.note.service;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.barretoareias.note.dto.NoteDTO;
import com.barretoareias.note.dto.request.NotePatchRequest;
import com.barretoareias.note.dto.request.NotePostRequest;
import com.barretoareias.note.dto.request.NotePutRequest;
import com.barretoareias.note.exception.LabelNotFoundException;
import com.barretoareias.note.exception.NoteAlreadyRegisteredException;
import com.barretoareias.note.exception.NoteNotFoundException;
import com.barretoareias.note.mapper.NoteMapper;
import com.barretoareias.note.repository.LabelRepository;
import com.barretoareias.note.repository.NoteRepository;
import com.barretoareias.note.utils.LabelUtils;
import com.barretoareias.note.utils.NoteUtils;

import org.springframework.stereotype.Service;

import java.util.Set;

import com.barretoareias.note.entity.Label;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NoteServiceImpl implements NoteService{
    
    private final NoteMapper mapper = NoteMapper.INSTANCE;
    private final NoteRepository repository;
    private final NoteUtils noteUtils;
    private final LabelUtils labelUtils;
    
    @Override
    public NoteDTO saveNote(NotePostRequest request) throws NoteAlreadyRegisteredException, LabelNotFoundException {
        final var dto = noteUtils.requestToDTO(request);  

        final var hasTitle = noteUtils.doesNoteExistsByTitle(dto.getTitle());
        if(hasTitle){
            throw new NoteAlreadyRegisteredException(dto.getTitle());
        }
        
        if(dto.getId()!=null){
            var hasId = noteUtils.doesNoteExistsById(dto.getId());
            if(hasId){
                throw new NoteAlreadyRegisteredException(dto.getId());
            }
        }
        var entity = mapper.toNote(dto);
        entity = noteUtils.getNotesLabelsAndReturnsIt(entity);
        var returned = repository.save(entity);
        return mapper.toDTO(returned);
    }

    @Override
    public NoteDTO updateNote(NotePutRequest request) throws NoteNotFoundException, LabelNotFoundException, NoteAlreadyRegisteredException {
        final var dto = noteUtils.requestToDTO(request);
        if(!noteUtils.doesNoteExistsById(dto.getId())){
            throw new NoteNotFoundException(dto.getId());
        }
        if(noteUtils.doesNoteExistsByTitle(dto.getTitle())){
            final var e = repository.findByTitle(dto.getTitle()).get();
            if(e.getId()!=dto.getId()){
                throw new NoteAlreadyRegisteredException(dto.getTitle());
            }
        }
        var entity = mapper.toNote(dto);
        entity = noteUtils.getNotesLabelsAndReturnsIt(entity);
        var returned = repository.save(entity);
        return mapper.toDTO(returned);
    }

    @Override
    public NoteDTO patchNote(NotePatchRequest request) throws NoteNotFoundException, LabelNotFoundException, NoteAlreadyRegisteredException{
        final var dto = noteUtils.requestToDTO(request);
        if(!noteUtils.doesNoteExistsById(dto.getId())){
            throw new NoteNotFoundException(dto.getId());
        }
        var entity = repository.findById(dto.getId()).get();
        if(dto.getTitle()!=null){
            if(noteUtils.doesNoteExistsByTitle(dto.getTitle())){
                final var e = repository.findByTitle(dto.getTitle()).get();
                if(e.getId()!=dto.getId()){
                    throw new NoteAlreadyRegisteredException(dto.getTitle());
                }
            }
            entity.setTitle(dto.getTitle());
        }
        if(dto.getDescription()!=null){
            entity.setDescription(dto.getDescription());
        }
        final var labelList = labelUtils.convertLabelDTOListIntoLabelList(dto.getLabelsInNote());
        entity.setLabelsInNote(labelList);
        final var returned = repository.save(entity);
        return mapper.toDTO(returned);
    }

    @Override
    public List<NoteDTO> findNotes() {
        var list = repository.findAll().stream()
            .map(mapper::toDTO)
            .collect(Collectors.toList());
        return list;
    }
    @Override
    public NoteDTO findById(Long id) throws NoteNotFoundException {
        if(!noteUtils.doesNoteExistsById(id)){
            throw new NoteNotFoundException(id);
        }
        var entity = repository.findById(id);
        return mapper.toDTO(entity.get());
    }
    @Override
    public void deleteById(Long id) throws NoteNotFoundException {
        if(!noteUtils.doesNoteExistsById(id)){
            throw new NoteNotFoundException(id);
        }
        repository.deleteById(id);
    }

    
}
