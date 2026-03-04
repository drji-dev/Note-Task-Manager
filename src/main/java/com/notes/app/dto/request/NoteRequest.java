package com.notes.app.dto.request;

import lombok.Data;

@Data
public class NoteRequest {
    
    private String title;
    private String content;
    private String tag;
}
