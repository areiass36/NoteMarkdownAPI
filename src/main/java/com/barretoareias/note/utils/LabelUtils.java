package com.barretoareias.note.utils;

import java.util.List;
import java.util.stream.Collectors;

import com.barretoareias.note.dto.LabelDTO;
import com.barretoareias.note.dto.request.LabelPostRequest;
import com.barretoareias.note.dto.request.LabelPutRequest;
import com.barretoareias.note.entity.Label;
import com.barretoareias.note.exception.LabelNotFoundException;
import com.barretoareias.note.mapper.LabelMapper;
import com.barretoareias.note.repository.LabelRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public final class LabelUtils{

    private final LabelRepository repository;
    private final LabelMapper mapper = LabelMapper.INSTANCE;
    
    public boolean doesLabelExistsById(Long id){
        return repository.findById(id).isPresent();
    }

    public Label getLabelById(Long id){
        final var result = repository.findById(id); 
        if(result.isPresent()){
            return result.get();
        }
        return null;
    }

    public boolean doesLabelExistsByName(String name){
        return repository.findByName(name).isPresent();
    }

    public List<LabelDTO> convertIdsIntoLabelDTOList(List<Long> ids){
        if(ids == null || ids.isEmpty()){
            return null;
        }
        return ids.stream()
                .map(e -> {return LabelDTO.builder().id(e).build();})
                .collect(Collectors.toList());
    }

    public List<Label> convertLabelDTOListIntoLabelList(List<LabelDTO> list) throws LabelNotFoundException{
        if(list == null || list.isEmpty()){
            return null;
        }
        final var labelList = list.stream()
                                .map(e -> getLabelById(e.getId()))
                                .collect(Collectors.toList());
        if(!labelList.contains(null)){
            return labelList;
        }
        throw new LabelNotFoundException();
    }

    public LabelDTO requestToDTO(LabelPutRequest request){
        final var dto = LabelDTO.builder()
                        .id(request.getId())
                        .name(request.getName()).build();
        return dto; 
    }

    public LabelDTO requestToDTO(LabelPostRequest request){
        final var dto = LabelDTO.builder()
                        .name(request.getName()).build();
        return dto; 
    }
}
