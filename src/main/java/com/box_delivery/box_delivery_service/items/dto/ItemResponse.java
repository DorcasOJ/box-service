package com.box_delivery.box_delivery_service.items.dto;

import com.box_delivery.box_delivery_service.boxes.enums.BoxState;
import com.box_delivery.box_delivery_service.items.enums.ItemStatus;

import java.util.UUID;

public record ItemResponse (
        UUID id,
        String name,
        Integer weight,
        String code,
        ItemStatus status
) {
}
