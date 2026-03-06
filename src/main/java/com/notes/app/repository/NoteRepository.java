package com.notes.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.notes.app.model.Note;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {
    long countByTagId(Long tagId);
} 
    
