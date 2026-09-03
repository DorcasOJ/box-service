package com.box_delivery.box_delivery_service.items.mapper;

import com.box_delivery.box_delivery_service.items.dto.ItemDto;
import com.box_delivery.box_delivery_service.items.dto.ItemResponse;
import com.box_delivery.box_delivery_service.items.entity.ItemEntity;
import org.springframework.stereotype.Component;

@Component
public class ItemMapper {
    public ItemResponse toResponse(ItemEntity item) {
        return new ItemResponse(item.getId(), item.getName(),
                item.getWeight(), item.getCode(), item.getStatus());
    }

    public ItemEntity toUpdate(ItemDto.UpdateItemRequest request, ItemEntity itemEntity) {
        if (request.weight() != null) {
            itemEntity.setWeight(request.weight());
        }
        if (request.status() != null) {
            itemEntity.setStatus(request.status());
        }
        return itemEntity;
    }

}
