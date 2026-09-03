package com.box_delivery.box_delivery_service.items.mapper;

import com.box_delivery.box_delivery_service.items.dto.ItemDto;
import com.box_delivery.box_delivery_service.items.dto.ItemResponse;
import com.box_delivery.box_delivery_service.items.entity.ItemEntity;
import org.springframework.stereotype.Component;

@Component
public class ItemMapper {
    public ItemResponse toResponse(ItemEntity item) {
        return new ItemResponse(item.getId(), item.getName(),
                item.getWeight(), item.getCode());
    }

    public ItemEntity toUpdate(ItemDto.UpdateItemRequest request, ItemEntity itemEntity) {
        itemEntity.setWeight(request.weight());
        return itemEntity;
    }

}
