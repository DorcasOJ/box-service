package com.box_delivery.box_delivery_service.boxes.dto;

import com.box_delivery.box_delivery_service.boxes.enums.BoxState;
import jakarta.persistence.Column;

import java.util.UUID;

public record BoxResponse(
        UUID id,
        String txref,
        Integer batteryLevel,
        BoxState state,
        boolean cameraEnabled,
        Integer currentWeight,
        Integer maxWeight,
        Integer remainingCapacity

) {

}
