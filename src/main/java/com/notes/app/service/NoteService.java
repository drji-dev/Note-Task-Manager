package com.notes.app.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import com.notes.app.model.Note;
import com.notes.app.repository.NoteRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NoteService {
    
    private final NoteRepository noteRepository;
    
    private final TagService tagService;

    // Получение заметки по id
    public Optional<Note> getNoteById(Long id) {
        return noteRepository.findById(id);
    }

    public List<Note> getAllNotes() {
        return noteRepository.findAll();
    }

    //создание заметки с id
    public Note createNote(String title, String content, String tag) {
        
        Note note = new Note();
        note.setTitle(title);
        note.setContent(content);
        note.setCreatedAt(LocalDateTime.now());
        note.setUpdatedAt(LocalDateTime.now());
        note.setTag(tagService.findOrCreateTag(tag));
        
        return noteRepository.save(note);
    }

    // Обновление заметки по id
    public Note updateNote(Long noteId, String title, String content, String tagName ) {
        Note note = noteRepository.findById(noteId).orElseThrow(() -> new RuntimeException("Note not found"));
        
        note.setTitle(title);
        note.setContent(content);
        note.setUpdatedAt(LocalDateTime.now());
        note.setTag(tagService.findOrCreateTag(tagName));
        
        return noteRepository.save(note);
    }

    public List<Note> getAllNotesByTagName(String tagName) {
        return noteRepository.findByTagName(tagName);
    }
    
    // Удаление заметки по id
    public void deleteNote(Long id) {
        noteRepository.deleteById(id);
    }
}
