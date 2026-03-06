package com.notes.app.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.notes.app.dto.response.TagResponse;
import com.notes.app.model.Tag;
import com.notes.app.service.TagService;

import jakarta.validation.Valid;

import com.notes.app.dto.request.TagRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tags")
public class TagController {

    private final TagService tagService;
    
    @GetMapping("/{id}")
    public ResponseEntity<TagResponse> getTagById(@PathVariable Long id) {
        return tagService.getTagById(id).map(tag -> ResponseEntity.ok(convertToResponse(tag))).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping()
    public ResponseEntity<TagResponse> createTag(@Valid @RequestBody TagRequest request) {
        Tag tag = tagService.createTag(request.getColor(), request.getTagName());
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToResponse(tag));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TagResponse> updateTagByID(@PathVariable Long id, @Valid @RequestBody TagRequest request) {
        Tag updateTag = tagService.updateTag(id, request.getColor(), request.getTagName());
        
        if (updateTag != null) {
            return ResponseEntity.ok(convertToResponse(updateTag));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTagById(@PathVariable Long id) {
        tagService.deleteTag(id);
        return ResponseEntity.noContent().build();
    }

    private TagResponse convertToResponse(Tag tag) {
        
        TagResponse response = new TagResponse();
        response.setId(tag.getId());
        response.setColor(tag.getColor());
        response.setTagName(tag.getName());
        response.setNoteCount(tagService.getNoteCount(tag.getId()));

        return response;
    }
}
