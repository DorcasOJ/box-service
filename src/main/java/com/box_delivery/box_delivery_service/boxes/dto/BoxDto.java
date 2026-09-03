package com.box_delivery.box_delivery_service.boxes.dto;

import com.box_delivery.box_delivery_service.boxes.entity.BoxEntity;
import com.box_delivery.box_delivery_service.boxes.enums.BoxState;
import com.box_delivery.box_delivery_service.items.dto.ItemDto;
import com.box_delivery.box_delivery_service.items.dto.ItemResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;
import java.util.UUID;

public class BoxDto {
    private BoxDto(){}
    public record CreateBoxRequest(
            @NotBlank
            @Size(max=20)
            @Schema(
                    description = "Unique box identifier",
                    example = "1234568703734859"
            )
            String txref,

            @NotNull
            @Min(0)
            @Max(100)
            @Schema(
                    description = "Box Battery",
                    example = "100"
            )
            Integer batteryLevel
    ) {}

    public record UpdateBoxRequest(
            UUID id,
            Integer batteryLevel
    ){}

    public record LoadBoxRequest (
            @NotEmpty
            @Valid
            List<ItemDto.CreateItemRequest> createItemRequests
    ){}

    public record BoxLoadedResponse(
            String txref,
            Integer batteryLevel,
            Integer numberOfItems,
            List<ItemResponse> items
    ) { }

    public record AvailableBoxes(
            UUID id,
            String txref,
            Integer batteryLevel,
            BoxState state,
            Integer currentWeight,
            Integer RemainingWeight
    ) {

    }

    public record BatteryResponse(
            UUID boxId,
            Integer batteryLevel,
            String status
    ) {

        public static BatteryResponse from(BoxEntity box) {

            String status;

            if (box.getBatteryLevel() <= 10) {
                status = "CRITICAL";
            } else if (box.getBatteryLevel() < 25) {
                status = "LOW";
            } else {
                status = "NORMAL";
            }

            return new BatteryResponse(
                    box.getId(),
                    box.getBatteryLevel(),
                    status
            );
        }
    }

}
