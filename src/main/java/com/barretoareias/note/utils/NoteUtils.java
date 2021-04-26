package com.barretoareias.note.utils;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.barretoareias.note.dto.NoteDTO;
import com.barretoareias.note.dto.request.NotePatchRequest;
import com.barretoareias.note.dto.request.NotePostRequest;
import com.barretoareias.note.dto.request.NotePutRequest;
import com.barretoareias.note.entity.Label;
import com.barretoareias.note.entity.Note;
import com.barretoareias.note.exception.LabelNotFoundException;
import com.barretoareias.note.exception.NoteNotFoundException;
import com.barretoareias.note.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public final class NoteUtils{
    
    private final NoteRepository repository;
    private final LabelUtils labelUtils;

    public boolean doesNoteExistsById(Long id){
        return repository.findById(id).isPresent();
    }

    public boolean doesNoteExistsByTitle(String title){
        return repository.findByTitle(title).isPresent();
    }

    public Note getNotesLabelsAndReturnsIt(Note note) throws LabelNotFoundException{
        final var list = note.getLabelsInNote().stream()
            .map(e -> labelUtils.getLabelById(e.getId()))
            .collect(Collectors.toList());
        if(list.contains(null)){
            throw new LabelNotFoundException();
        }
        note.setLabelsInNote(list);
        return note;
    }

    public NoteDTO requestToDTO(NotePatchRequest request){
        final var idList = request.getLabelsInNote();
        final var convertedList = labelUtils.convertIdsIntoLabelDTOList(idList);
        final var dto = NoteDTO.builder()
                .id(request.getId())
                .labelsInNote(convertedList).build();
        if(request.getTitle() != null && !request.getTitle().isBlank()){
            dto.setTitle(request.getTitle());
        }
        if(request.getDescription() != null && !request.getDescription().isBlank()){
            dto.setDescription(request.getDescription());
        }
        return dto;
    }

    public NoteDTO requestToDTO(NotePutRequest request){
        final var idList = request.getLabelsInNote();
        final var convertedList = labelUtils.convertIdsIntoLabelDTOList(idList);
        return NoteDTO.builder()
                .id(request.getId())
                .title(request.getTitle())
                .labelsInNote(convertedList)
                .description(request.getDescription()).build();
    }

    public NoteDTO requestToDTO(NotePostRequest request){
        final var idList = request.getLabelsInNote();
        final var convertedList = labelUtils.convertIdsIntoLabelDTOList(idList);
        return NoteDTO.builder()
                .title(request.getTitle())
                .labelsInNote(convertedList)
                .description(request.getDescription()).build();
    }
}
