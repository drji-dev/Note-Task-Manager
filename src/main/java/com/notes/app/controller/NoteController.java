package com.notes.app.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.notes.app.dto.request.NoteRequest;
import com.notes.app.dto.response.NoteResponse;
import com.notes.app.model.Note;
import com.notes.app.service.NoteService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/notes")
@RequiredArgsConstructor
public class NoteController {
    
    private final NoteService noteService;

    @GetMapping("/{id}")
    public ResponseEntity<NoteResponse> getNoteById(@PathVariable Long id, @RequestHeader("X-Telegram-Id") Long userId) {
        return noteService.getNoteByIdAndUserId(id, userId).map(note -> ResponseEntity.ok(convertToResponse(note))).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<NoteResponse>> getAllNotesByTag(@RequestParam(required = false) String tag, @RequestHeader("X-Telegram-Id") Long userId) {
        if (tag != null) {
            List<NoteResponse> responses = noteService.getAllNotesByTagNameAndUserId(tag, userId).stream().map(this::convertToResponse).toList();
            return ResponseEntity.ok(responses);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<NoteResponse> createNote(@Valid @RequestBody NoteRequest request, @RequestHeader("X-Telegram-Id") Long userId) {
        Note note = noteService.createNoteByUserId(request.getTitle(), request.getContent(), request.getTagName(), userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToResponse(note));
    }

    @PutMapping("/{id}")
    public ResponseEntity<NoteResponse> updateNoteById(@PathVariable Long id, @Valid @RequestBody NoteRequest request, @RequestHeader("X-Telegram-Id") Long userId) {
        Note updateNote = noteService.updateNoteByUserId(id, request.getTitle(), request.getContent(), request.getTagName(), userId);
        
        if (updateNote != null) {
            return ResponseEntity.ok(convertToResponse(updateNote));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNoteById(@PathVariable Long id, @RequestHeader("X-Telegram-Id") Long userId) {
        noteService.deleteNoteByUserId(id, userId);
        return ResponseEntity.noContent().build();
    } 

    private NoteResponse convertToResponse(Note note) {
        
           NoteResponse response = new NoteResponse();
           
            response.setId(note.getId());
            response.setTitle(note.getTitle());
            response.setContent(note.getContent());
            response.setCreatedAt(note.getCreatedAt());
            response.setUpdatedAt(note.getUpdatedAt());
            response.setTagName(note.getTag() != null ? note.getTag().getName() : null);

            return response;
    }

}
