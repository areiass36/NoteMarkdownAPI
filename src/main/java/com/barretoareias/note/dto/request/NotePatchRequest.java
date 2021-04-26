package com.barretoareias.note.dto.request;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotePatchRequest {
    
    @NotNull(message = "Id must not be null")
    private Long id;

    @Size(min = 0,max = 100,message = "Title must contain 100 chars max")
    private String title;

    @Size(min = 0, message = "Description has wrong values")
    private String description;

    private List<Long> labelsInNote; 
}
