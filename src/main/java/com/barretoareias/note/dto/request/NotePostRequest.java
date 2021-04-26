package com.barretoareias.note.dto.request;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class NotePostRequest {
    
    @NotBlank(message = "Title must not be blank")
    @Size(min = 1,max = 100, message = "Title must contain 100 chars max")
    private String title;

    @NotBlank(message = "Description must not be blank")
    @Size(min = 1, message = "Description must have at least one char")
    private String description;

    private List<Long> labelsInNote; 
}
