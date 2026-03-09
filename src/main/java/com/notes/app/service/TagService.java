package com.notes.app.service;

import java.util.Optional;
import org.springframework.stereotype.Service;
import com.notes.app.model.Tag;
import com.notes.app.repository.NoteRepository;
import com.notes.app.repository.TagRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TagService {
    
    private final TagRepository tagRepository;

    private final NoteRepository noteRepository;

    public void deleteTagByIdAndUserId(Long id, Long userId) {
        tagRepository.deleteByIdAndUserId(id, userId);
    }

    public Optional<Tag> getTagByIdAndUserId(Long id, Long userId) {
        return tagRepository.findByIdAndUserId(id, userId);
    }

    public Tag createTagByUserId(String name, String color, Long userId) {

        Tag tag = new Tag();
        tag.setColor(color);
        tag.setName(name);
        tag.setUserId(userId);

        return tagRepository.save(tag);
    }

    public Tag updateTagByUserId(Long tagId, String color, String name, Long userId) {

        Optional<Tag> optionalTag = tagRepository.findByIdAndUserId(tagId, userId);

        if (optionalTag.isPresent()) {
            Tag tag = optionalTag.get();
            tag.setName(name);
            if (color != null) {
                tag.setColor(color);
            }
            return tagRepository.save(tag);
        }
        return null;
    }

    public Tag findOrCreateTagByUserId(String tagName, Long userId) {
        return tagRepository.findByNameAndUserId(tagName, userId)
                .orElseGet(() -> createTagByUserId(tagName, "#3B82F6", userId));
    }

    public long getNoteCountByUserId(Long tagId, Long userId) {
        return noteRepository.countByTagIdAndUserId(tagId, userId);
    }
}
