package com.barretoareias.note.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LabelPostRequest {
    
    @NotBlank(message = "Label name must not be blank")
    @Size(min = 1,max = 30)
    private String name;
}
