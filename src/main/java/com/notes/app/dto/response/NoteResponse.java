package com.notes.app.dto.response;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class NoteResponse {

    private String title;
    private String content;
    private LocalDateTime updatedAt;
    private LocalDateTime createdAt;
    private String tag;
}
