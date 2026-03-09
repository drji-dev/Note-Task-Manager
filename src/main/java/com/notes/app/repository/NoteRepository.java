package com.notes.app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.notes.app.model.Note;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {

    Optional<Note> findByIdAndUserId(Long id, Long userId);
    
    List<Note> findByUserId(Long userId);
    
    void deleteByIdAndUserId(Long id, Long userId);

    long countByTagIdAndUserId(Long tagId, Long userId);
    
    @Query("SELECT n FROM Note n WHERE n.tag.name = :tagName AND n.userId = :userId")
    List<Note> findByTagNameAndUserId(@Param("tagName") String tagName, @Param("userId") Long userId);
} 
    
