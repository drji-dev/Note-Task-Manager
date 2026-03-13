package com.notes.app.controller;

import java.util.List;
import java.util.stream.Collectors;

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
    public ResponseEntity<NoteResponse> getNoteById(@PathVariable Long id, @RequestHeader("User-Id") Long userId) {
        return noteService.getNoteByIdAndUserId(id, userId).map(note -> ResponseEntity.ok(convertToResponse(note))).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<NoteResponse>> getAllNote(@RequestParam(required = false) String tag, @RequestHeader("User-Id") Long userId) {
        List<NoteResponse> responses;

        if (tag != null) {
            responses = noteService.getAllNotesByTagNameAndUserId(tag, userId).stream().map(this::convertToResponse).toList();
        } else {
            responses = noteService.getAllNotesByUserId(userId).stream().map(this::convertToResponse).toList();
        }
    
        return ResponseEntity.ok(responses);
    }

    @PostMapping
    public ResponseEntity<NoteResponse> createNote(@Valid @RequestBody NoteRequest request, @RequestHeader("User-Id") Long userId) {
        Note note = noteService.createNoteByUserId(request.getTitle(), request.getContent(), request.getTagName(), userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToResponse(note));
    }

    @PutMapping("/{id}")
    public ResponseEntity<NoteResponse> updateNoteById(@PathVariable Long id, @Valid @RequestBody NoteRequest request, @RequestHeader("User-Id") Long userId) {
        Note updateNote = noteService.updateNoteByUserId(id, request.getTitle(), request.getContent(), request.getTagName(), userId);
        
        if (updateNote != null) {
            return ResponseEntity.ok(convertToResponse(updateNote));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNoteById(@PathVariable Long id, @RequestHeader("User-Id") Long userId) {
        noteService.deleteNoteByUserId(id, userId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/favorite")
    public ResponseEntity<NoteResponse> toogleFavorite(@PathVariable Long id, @RequestHeader("User-Id") Long userId) {
        Note note = noteService.toggleFavorite(id, userId);
        if(note != null) {
            return ResponseEntity.ok(convertToResponse(note));
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/favorites")
    public ResponseEntity<List<NoteResponse>> getFavoriteNotes(@RequestHeader("User-Id") Long userId) {
        List<Note> notes = noteService.getFavoriteNotes(userId);
        List<NoteResponse> response = notes.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    private NoteResponse convertToResponse(Note note) {
        
           NoteResponse response = new NoteResponse();
           
            response.setId(note.getId());
            response.setTitle(note.getTitle());
            response.setContent(note.getContent());
            response.setCreatedAt(note.getCreatedAt());
            response.setUpdatedAt(note.getUpdatedAt());
            response.setIsFavorite(note.getIsFavorite());
            response.setTagName(note.getTag() != null ? note.getTag().getName() : null);

            return response;
    }

}
