package com.barretoareias.note.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LabelPutRequest {
    
    @NotNull(message = "Id must not be null")
    private Long id;

    @NotBlank(message = "Label name must not be blank")
    @Size(min = 1,max = 30)
    private String name;
}
