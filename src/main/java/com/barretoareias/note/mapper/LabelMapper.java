package com.barretoareias.note.mapper;

import com.barretoareias.note.dto.LabelDTO;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.barretoareias.note.entity.Label;

@Mapper
public interface LabelMapper {
    
    LabelMapper INSTANCE = Mappers.getMapper(LabelMapper.class);

    LabelDTO toDTO(Label label);

    Label toLabel(LabelDTO dto);
}
