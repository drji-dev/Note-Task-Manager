package com.notes.app.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.notes.app.dto.request.ItemRequest;
import com.notes.app.dto.response.ItemResponse;
import com.notes.app.model.Item;
import com.notes.app.model.Item.ItemType;
import com.notes.app.service.ItemService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/items")
@RequiredArgsConstructor
public class ItemController {
    
    private final ItemService itemService;

    @GetMapping("/{id}")
    public ResponseEntity<ItemResponse> getItemById(@PathVariable Long id, @RequestHeader("User-Id") Long userId) {
        return itemService.getItemByIdAndUserId(id, userId).map(item -> ResponseEntity.ok(convertToResponse(item))).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<ItemResponse>> getAllItemByTagOrType(@RequestParam(required = false) String tag, @RequestParam(required = false) ItemType type, @RequestHeader("User-Id") Long userId) {
        List<ItemResponse> responses;

        if (tag != null) {
            responses = itemService.getAllItemByTagNameAndUserId(tag, userId).stream().map(this::convertToResponse).toList();
        }else if (type != null) {
            responses = itemService.getAllItemByTypeAndUserId(type, userId).stream().map(this::convertToResponse).toList();
        }else {
            responses = itemService.getAllItemsByUserId(userId).stream().map(this::convertToResponse).toList();
        }
    
        return ResponseEntity.ok(responses);
    }

    @PostMapping
    public ResponseEntity<ItemResponse> createNote(@Valid @RequestBody ItemRequest request, @RequestHeader("User-Id") Long userId) {
        Item item = itemService.createItemByUserId(request, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToResponse(item));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ItemResponse> updateNoteById(@PathVariable Long id, @Valid @RequestBody ItemRequest request, @RequestHeader("User-Id") Long userId) {
        Item updateNote = itemService.updateItemByUserId(id, request, userId);
        
        if (updateNote != null) {
            return ResponseEntity.ok(convertToResponse(updateNote));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNoteById(@PathVariable Long id, @RequestHeader("User-Id") Long userId) {
        itemService.deleteItemByUserId(id, userId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/favorite")
    public ResponseEntity<ItemResponse> toogleFavorite(@PathVariable Long id, @RequestHeader("User-Id") Long userId) {
        Item item = itemService.toggleFavorite(id, userId);
        if(item != null) {
            return ResponseEntity.ok(convertToResponse(item));
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/favorites")
    public ResponseEntity<List<ItemResponse>> getFavoriteNotes(@RequestHeader("User-Id") Long userId) {
        List<Item> notes = itemService.getFavoriteItems(userId);
        List<ItemResponse> response = notes.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    private ItemResponse convertToResponse(Item item) {
        
           ItemResponse response = new ItemResponse();
           
            response.setId(item.getId());
            response.setTitle(item.getTitle());
            response.setContent(item.getContent());
            response.setCreatedAt(item.getCreatedAt());
            response.setUpdatedAt(item.getUpdatedAt());
            response.setIsFavorite(item.getIsFavorite());
            response.setTagName(item.getTagName() != null ? item.getTagName() : null);
            response.setType(item.getType());
            response.setPriority(item.getPriority());

            return response;
    }

}
