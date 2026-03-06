package com.notes.app.dto.response;

import lombok.Data;

@Data
public class TagResponse {
    
    private Long id;
    private String tagName;
    private String color;
    private long noteCount;
}
