package com.notes.app.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import com.notes.app.dto.request.ItemRequest;
import com.notes.app.model.Item;
import com.notes.app.model.Tag;
import com.notes.app.model.Item.ItemType;
import com.notes.app.repository.ItemRepository;

import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemService {
    
    private final ItemRepository itemRepository;
    
    private final TagService tagService;

    public Optional<Item> getItemByIdAndUserId(Long id, Long userId) {
        return itemRepository.findByIdAndUserId(id, userId);
    }

    public List<Item> getAllItemsByUserId(Long userId) {
        return itemRepository.findByUserId(userId);
    }

    public Item createItemByUserId(ItemRequest request, Long userId) {
        
        Item item = new Item();
        Tag tag = tagService.findTagByNameAndUserId(request.getTagName(), userId);

        item.setTitle(request.getTitle());
        item.setContent(request.getContent());
        item.setCreatedAt(LocalDateTime.now());
        item.setUpdatedAt(LocalDateTime.now());
        item.setTagName(tag.getName());
        item.setTagColor(tag.getColor());
        item.setUserId(userId);

        if (request.getType() != null && request.getType() == ItemType.TASK) {
            item.setType(ItemType.TASK);
            item.setPriority(request.getPriority());
        }else {
            item.setType(ItemType.NOTE);
        }
        
        return itemRepository.save(item);
    }

    public Item updateItemByUserId(Long itemId, ItemRequest request, Long userId) {
        Item item = itemRepository.findByIdAndUserId(itemId, userId).orElseThrow(() -> new RuntimeException("Item not found"));
        Tag tag = tagService.findTagByNameAndUserId(request.getTagName(), userId);
        
        item.setTitle(request.getTitle());
        item.setContent(request.getContent());
        item.setUpdatedAt(LocalDateTime.now());
        item.setTagName(tag.getName());
        item.setTagColor(tag.getColor());

        if (request.getType() != null && request.getType() == ItemType.TASK) {
            item.setType(ItemType.TASK);
            item.setPriority(request.getPriority());
        }else {
            item.setType(ItemType.NOTE);
        }
        
        return itemRepository.save(item);
    }

    public List<Item> getAllItemByTagNameAndUserId(String tagName, Long userId) {
        return itemRepository.findByTagNameAndUserId(tagName, userId);
    }

    public List<Item> getAllItemByTypeAndUserId(ItemType type, Long userId) {
        return itemRepository.findByTypeAndUserId(type, userId);
    }

    public void deleteItemByUserId(Long id, Long userId) {
        itemRepository.deleteByIdAndUserId(id, userId);
    }

    public List<Item> getFavoriteItems(Long userId) {
        return itemRepository.findFavoriteNotesByUser(userId);
    }

    public Item toggleFavorite(Long id, Long userId) {
        Optional<Item> optionalNote = itemRepository.findByIdAndUserId(id, userId);
        if(optionalNote.isPresent()) {
            Item item = optionalNote.get();
            item.setIsFavorite(!item.getIsFavorite());
            return itemRepository.save(item);
        }
        return null;
    }
}
