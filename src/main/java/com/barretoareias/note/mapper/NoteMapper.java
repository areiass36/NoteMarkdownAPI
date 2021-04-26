package com.barretoareias.note.mapper;


import com.barretoareias.note.dto.NoteDTO;
import com.barretoareias.note.entity.Note;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface NoteMapper {
    
    NoteMapper INSTANCE = Mappers.getMapper(NoteMapper.class);
    
    Note toNote(NoteDTO dto);
    
    NoteDTO toDTO(Note note);
}
