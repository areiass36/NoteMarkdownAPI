package com.barretoareias.note.service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.barretoareias.note.dto.LabelDTO;
import com.barretoareias.note.dto.NoteDTO;
import com.barretoareias.note.dto.request.LabelPostRequest;
import com.barretoareias.note.dto.request.LabelPutRequest;
import com.barretoareias.note.exception.LabelAlreadyRegisteredException;
import com.barretoareias.note.exception.LabelNotFoundException;
import com.barretoareias.note.mapper.LabelMapper;
import com.barretoareias.note.repository.LabelRepository;
import com.barretoareias.note.utils.LabelUtils;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LabelServiceImpl implements LabelService{

    private final LabelRepository repository;
    private final LabelMapper mapper = LabelMapper.INSTANCE;
    private final LabelUtils labelUtils;

    @Override
    public LabelDTO saveLabel(LabelPostRequest request) throws LabelAlreadyRegisteredException {
        final var label = labelUtils.requestToDTO(request);
        var hasName = labelUtils.doesLabelExistsByName(label.getName());
        if(hasName){
            throw new LabelAlreadyRegisteredException(label.getName());
        }
        if(label.getId()!=null){
            var hasId = labelUtils.doesLabelExistsById(label.getId());
            if(hasId){
                throw new LabelAlreadyRegisteredException(label.getId());
            }
        }   
        var entity = mapper.toLabel(label);
        var returned = repository.save(entity);
        return mapper.toDTO(returned);     
    }

    @Override
    public LabelDTO updateLabel(LabelPutRequest request) throws LabelNotFoundException{
        final var label = labelUtils.requestToDTO(request);
        if(!labelUtils.doesLabelExistsById(label.getId())){
            throw new LabelNotFoundException(label.getId());
        }
        var entity = mapper.toLabel(label);
        var returned = repository.save(entity);
        return mapper.toDTO(returned);
    }
    

    @Override
    public LabelDTO findById(Long id) throws LabelNotFoundException {
        if(labelUtils.doesLabelExistsById(id)){
            var returned = repository.findById(id);
            return mapper.toDTO(returned.get());
        }
        throw new LabelNotFoundException(id);
    }

    @Override
    public LabelDTO findByName(String name) throws LabelNotFoundException{
        if(labelUtils.doesLabelExistsByName(name)){
            var returned = repository.findByName(name);
            return mapper.toDTO(returned.get());
        }
        throw new LabelNotFoundException(name);
    }

    @Override
    public List<LabelDTO> findLabels() {
        var list = repository.findAll().stream()
            .map(e -> {
                var dto = mapper.toDTO(e);
                System.out.println(e.getName());
                System.out.println(dto.getName());
                return dto;})
            .collect(Collectors.toList());
        return list;
    }

    @Override
    public void deleteById(Long id) throws LabelNotFoundException {
        if(!labelUtils.doesLabelExistsById(id)){
            throw new LabelNotFoundException(id); 
        }
        repository.deleteById(id);   
    }
    
}
