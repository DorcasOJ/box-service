package com.box_delivery.box_delivery_service.boxes.mapper;

import com.box_delivery.box_delivery_service.boxes.dto.BoxDto;
import com.box_delivery.box_delivery_service.boxes.dto.BoxResponse;
import com.box_delivery.box_delivery_service.boxes.entity.BoxEntity;
import com.box_delivery.box_delivery_service.items.dto.ItemResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BoxMapper {

    public BoxResponse toResponse(BoxEntity box) {
        return new BoxResponse(box.getId(), box.getTxref(),
                box.getBatteryLevel(), box.getState(), box.isCameraEnabled(),
                box.getCurrentWeight(), box.getMaxWeight(), box.getRemainingCapacity());
    }

    public BoxEntity toUpdate(BoxDto.UpdateBoxRequest request, BoxEntity itemEntity) {
        itemEntity.setBatteryLevel(request.batteryLevel());
        return itemEntity;
    }

    public BoxDto.BoxLoadedResponse toBoxLoadedResponse(BoxEntity box, Integer NumberOfItems, List<ItemResponse> items) {
        return new BoxDto.BoxLoadedResponse(box.getTxref(), box.getBatteryLevel(), NumberOfItems, items);
    }
}
