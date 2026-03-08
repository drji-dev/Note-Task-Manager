package com.notes.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.notes.app.model.Note;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {
    long countByTagId(Long tagId);
    
    @Query("SELECT n FROM Note n WHERE n.tag.name = :tagName")
    List<Note> findByTagName(@Param("tagName") String tagName);
} 
    
