package com.notes.app.dto.request;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;

@Data
public class NoteRequest {
    
    @NotBlank(message = "Поле не может быть пустым")
    private String title;

    @NotBlank(message = "Поле не может быть пустым")
    private String content;

    private String tagName;
}
