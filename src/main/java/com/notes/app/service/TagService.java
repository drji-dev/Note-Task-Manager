package com.notes.app.service;

import java.util.Optional;
import org.springframework.stereotype.Service;
import com.notes.app.model.Tag;
import com.notes.app.repository.TagRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TagService {
    
    private final TagRepository tagRepository;

    public void deleteTag(Long id) {
        tagRepository.deleteById(id);
    }

    public Optional<Tag> getTagById(Long id) {
        return tagRepository.findById(id);
    }

    public Tag createTag(String color, String name) {

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
}
