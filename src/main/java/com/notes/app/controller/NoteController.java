package com.notes.app.controller;

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
    public ResponseEntity<NoteResponse> getNoteById(@PathVariable Long id) {
        return noteService.getNoteById(id).map(note -> ResponseEntity.ok(convertToResponse(note))).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<NoteResponse> createNote(@Valid @RequestBody NoteRequest request) {
        Note note = noteService.createNote(request.getTitle(), request.getContent(), request.getTagName());
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToResponse(note));
    }

    @PutMapping("/{id}")
    public ResponseEntity<NoteResponse> updateNoteById(@PathVariable Long id, @Valid @RequestBody NoteRequest request) {
        Note updateNote = noteService.updateNote(id, request.getTitle(), request.getContent(), request.getTagName());
        
        if (updateNote != null) {
            return ResponseEntity.ok(convertToResponse(updateNote));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNoteById(@PathVariable Long id) {
        noteService.deleteNote(id);
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
