package com.box_delivery.box_delivery_service.items.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.util.UUID;

public class ItemDto {
    private ItemDto(){}

    public record CreateItemRequest(
            @NotBlank
            @Size(max=20)
            @Pattern(
                    regexp = "^[a-zA-Z0-9_-]+$"
            )
            @Schema(
                    description = "Item",
                    example = "Food bowl"
            )
            String name,

            @NotNull
            @Min(0)
            @Max(5000)
            @Positive
            @Schema(
                    description = "Item weight",
                    example = "2000"
            )
            Integer weight,

            @Pattern(
                    regexp = "^[A-Z0-9_]+$"
    )
            String code
    ) {}

    public record UpdateItemRequest(
            UUID id,
            Integer weight
    ){}




}
