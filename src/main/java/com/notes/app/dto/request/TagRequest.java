package com.notes.app.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class TagRequest {
    
    @NotBlank(message = "Поле не может быть пустым")
    private String tagName;

    @Pattern(regexp = "^#[0-9A-Fa-f]{6}$", message = "Цвет должен быть в hex формате")
    private String color;
}
