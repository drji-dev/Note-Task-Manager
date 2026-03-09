package com.notes.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.notes.app.model.Tag;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long>{

    Optional<Tag> findByIdAndUserId(Long id, Long userId);

    void deleteByIdAndUserId(Long id, Long userId);

    Optional<Tag> findByNameAndUserId(String name, Long userId);
}
