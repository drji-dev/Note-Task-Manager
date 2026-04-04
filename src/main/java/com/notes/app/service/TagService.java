package com.notes.app.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

import com.notes.app.dto.request.TagRequest;
import com.notes.app.model.Tag;
import com.notes.app.repository.TagRepository;

import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class TagService {
    
    private final TagRepository tagRepository;
    
    public Optional<Tag> getTagByIdAndUserId(Long id, Long userId) {
        return tagRepository.findByIdAndUserId(id, userId);
    }

    public List<Tag> getAllTagByUserId(Long userId) {
        return tagRepository.findByUserId(userId);
    }
    
    public Tag createTagByUserId(TagRequest request, Long userId) {

        if (tagRepository.findByNameAndUserId(request.getTagName(), userId).isPresent()) {
            throw new RuntimeException("Тег с таким именем уже существует");
        }
        
        Tag tag = new Tag();
        tag.setName(request.getTagName());
        tag.setUserId(userId);

        if (request.getColor() != null){   
            tag.setColor(request.getColor());
        }else{
            tag.setColor("#FFFFFF");
        }
        
        return tagRepository.save(tag);
    }
    
    public Tag updateTagByIdAndUserId(Long tagId, String color, String name, Long userId) {
        
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
    
    public void deleteTagByIdAndUserId(Long id, Long userId) {
        tagRepository.deleteByIdAndUserId(id, userId);
    }

    public Tag findTagByNameAndUserId(String tagName, Long userId) {
        return tagRepository.findByNameAndUserId(tagName, userId).orElseThrow(() -> new RuntimeException("Tag not found"));
    }
}
