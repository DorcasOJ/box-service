package com.box_delivery.box_delivery_service.items.service;

import com.box_delivery.box_delivery_service.common.exception.ResourceAlreadyExistException;
import com.box_delivery.box_delivery_service.common.exception.ResourceNotFoundException;
import com.box_delivery.box_delivery_service.items.dto.ItemDto;
import com.box_delivery.box_delivery_service.items.dto.ItemResponse;
import com.box_delivery.box_delivery_service.items.entity.ItemEntity;
import com.box_delivery.box_delivery_service.items.mapper.ItemMapper;
import com.box_delivery.box_delivery_service.items.repository.ItemRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;

    @Transactional
    public com.box_delivery.box_delivery_service.items.dto.ItemResponse createItem(ItemDto.CreateItemRequest request, UUID boxId){
        if(itemRepository.existsByNameAndDeletedFalse(request.name())) {
            throw new ResourceAlreadyExistException( "Item with with already exists");
        }

        com.box_delivery.box_delivery_service.items.entity.ItemEntity item = com.box_delivery.box_delivery_service.items.entity.ItemEntity.builder()
                .name(request.name())
                .weight(request.weight())
                .code(request.code())
//                .code(UUID.randomUUID().toString().replace("-", "_ "))
                .boxId(boxId)
                .build();

        return itemMapper.toResponse( itemRepository.save(item));
    }

    public ItemResponse updateItem(ItemDto.UpdateItemRequest request) {

        com.box_delivery.box_delivery_service.items.entity.ItemEntity itemEntity = itemRepository.findByIdAndDeletedFalse(request.id()).orElseThrow(
                () -> new ResourceNotFoundException("Box does not exist. Update Failed")

        );
        ItemEntity updated = itemRepository.save(itemMapper.toUpdate(request, itemEntity));
        return itemMapper.toResponse(updated);
    }

    public Page<ItemResponse> getAllItems(Pageable pageable) {

        Page<ItemEntity> allBoxes = itemRepository.findAll(pageable);
        return allBoxes.map(itemMapper::toResponse);
    }

    public ItemResponse getAItem(UUID id) {

        ItemEntity itemEntity = itemRepository.findByIdAndDeletedFalse(id).orElseThrow(
                () -> new ResourceNotFoundException("Box does not exist. Update Failed")

        );
        return itemMapper.toResponse(itemEntity);
    }


    public void deleteItem(UUID id) {

        ItemEntity itemEntity = itemRepository.findByIdAndDeletedFalse(id).orElseThrow(
                () -> new ResourceNotFoundException("Box does not exist. Update Failed")

        );
        itemEntity.softDelete();
        ItemEntity saved = itemRepository.save(itemEntity);
    }



}
