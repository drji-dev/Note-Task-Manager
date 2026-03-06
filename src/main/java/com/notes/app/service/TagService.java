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

    public void deleteTag(Long id) {
        tagRepository.deleteById(id);
    }

    public Optional<Tag> getTagById(Long id) {
        return tagRepository.findById(id);
    }

    public Tag createTag(String name, String color) {

        Tag tag = new Tag();
        tag.setColor(color);
        tag.setName(name);

        return tagRepository.save(tag);
    }

    public Tag updateTag(Long tagId, String color, String name) {

        Optional<Tag> optionalTag = tagRepository.findById(tagId);

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

    public Tag findOrCreateTag(String tagName) {
        return tagRepository.findByName(tagName)
                .orElseGet(() -> createTag(tagName, "#3B82F6"));
    }

    public long getNoteCount(Long tagId) {
        return noteRepository.countByTagId(tagId);
    }
}
