package com.notes.app.controller;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.notes.app.dto.request.NoteRequest;
import com.notes.app.dto.response.NoteResponse;
import com.notes.app.model.Note;
import com.notes.app.service.NoteService;

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
    public ResponseEntity<NoteResponse> createNote(@RequestBody NoteRequest request) {
        Note note = noteService.createNote(request.getTitle(), request.getContent());
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToResponse(note));
    }

    private NoteResponse convertToResponse(Note note) {
        
           NoteResponse response = new NoteResponse();
           
           response.setTitle(note.getTitle());
           response.setContent(note.getContent());
           response.setCreatedAt(LocalDateTime.now());
           response.setUpdatedAt(LocalDateTime.now());
           //response.setTag(note.getTag().getName());

           return response;
    }

}
