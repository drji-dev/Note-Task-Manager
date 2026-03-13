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
    public Optional<Note> getNoteByIdAndUserId(Long id, Long userId) {
        return noteRepository.findByIdAndUserId(id, userId);
    }

    public List<Note> getAllNotesByUserId(Long userId) {
        return noteRepository.findByUserId(userId);
    }

    //создание заметки с id
    public Note createNoteByUserId(String title, String content, String tag, Long userId) {
        
        Note note = new Note();
        note.setTitle(title);
        note.setContent(content);
        note.setCreatedAt(LocalDateTime.now());
        note.setUpdatedAt(LocalDateTime.now());
        note.setTag(tagService.findOrCreateTagByUserId(tag, userId));
        note.setUserId(userId);
        
        return noteRepository.save(note);
    }

    // Обновление заметки по id
    public Note updateNoteByUserId(Long noteId, String title, String content, String tagName, Long userId) {
        Note note = noteRepository.findByIdAndUserId(noteId, userId).orElseThrow(() -> new RuntimeException("Note not found"));
        
        note.setTitle(title);
        note.setContent(content);
        note.setUpdatedAt(LocalDateTime.now());
        note.setTag(tagService.findOrCreateTagByUserId(tagName, userId));
        
        return noteRepository.save(note);
    }

    public List<Note> getAllNotesByTagNameAndUserId(String tagName, Long userId) {
        return noteRepository.findByTagNameAndUserId(tagName, userId);
    }
    
    // Удаление заметки по id
    public void deleteNoteByUserId(Long id, Long userId) {
        noteRepository.deleteByIdAndUserId(id, userId);
    }

    public List<Note> getFavoriteNotes(Long userId) {
        return noteRepository.findFavoriteNotesByUser(userId);
    }

    public Note toggleFavorite(Long id, Long userId) {
        Optional<Note> optionalNote = noteRepository.findByIdAndUserId(id, userId);
        if(optionalNote.isPresent()) {
            Note note = optionalNote.get();
            note.setIsFavorite(!note.getIsFavorite());
            return noteRepository.save(note);
        }
        return null;
    }
}
