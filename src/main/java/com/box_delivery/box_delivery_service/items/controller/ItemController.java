package com.box_delivery.box_delivery_service.items.controller;


import com.box_delivery.box_delivery_service.items.dto.ItemDto;
import com.box_delivery.box_delivery_service.items.dto.ItemResponse;
import com.box_delivery.box_delivery_service.common.mapper.ApiResponseMapper;
import com.box_delivery.box_delivery_service.common.response.ApiResponse;
import com.box_delivery.box_delivery_service.items.service.ItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/items")
@Tag(name = "Items", description = "Items Service APIs")
@RequiredArgsConstructor
public class ItemController {
    private final ApiResponseMapper apiResponseMapper;
    private final ItemService itemService;

    @Operation(
            summary = "Get all item",
            description = "Returns all item"
    )
    @GetMapping("")
    public ResponseEntity<ApiResponse<Page<ItemResponse>>> getAllBoxes(
            @Parameter(hidden = true)
            @PageableDefault(page = 0, size = 15, sort = "createdAt", direction = Sort.Direction.DESC)
            Pageable pageable
    )

    {
        boolean hasInvalidSort = pageable.getSort().stream()
                .anyMatch(order -> order.getProperty().equalsIgnoreCase("string"));
        if (hasInvalidSort) {
            pageable = PageRequest.of(
                    pageable.getPageNumber(), pageable.getPageSize(),
                    Sort.by(Sort.Direction.DESC, "createdAt")
            );
        }
        return ResponseEntity.ok(
                apiResponseMapper.toResponse(itemService.getAllItems(pageable))
        );
    }

    @Operation(
            summary = "Get a item",
            description = "Returns a box"
    )
    @GetMapping("/{itemId}")
    public ResponseEntity<ApiResponse<ItemResponse>> getABox(
            @PathVariable UUID itemId
    ) {
        return ResponseEntity.ok(
                apiResponseMapper.toResponse(itemService.getAItem(itemId))
        );
    }


//    @Operation(
//            summary = "Create a item",
//            description = "Returns a item"
//    )
//    @PostMapping("")
//    public ResponseEntity<ApiResponse<ItemResponse>> createBox(
//            @RequestBody ItemDto.CreateItemRequest request
//    ) {
//        return ResponseEntity.ok(
//                apiResponseMapper.toResponse(itemService.createItem(request))
//        );
//    }

//    @Operation(
//            summary = "Update a box",
//            description = "Returns a box"
//    )
//    @PatchMapping("")
//    public ResponseEntity<ApiResponse<ItemResponse>> updateBox(
//            @RequestBody ItemDto.UpdateItemRequest request
//    ) {
//        return ResponseEntity.ok(
//                apiResponseMapper.toResponse(itemService.updateItem(request))
//        );
//    }


//    @Operation(
//            summary = "Delete a item",
//            description = "Returns success"
//    )
//    @DeleteMapping("/{itemId}")
//    public ResponseEntity<ApiResponse<String>> deleteBox(
//            @PathVariable UUID itemId
//    ) {
//        itemService.deleteItem(itemId);
//        return ResponseEntity.ok(
//                apiResponseMapper.toResponse("Box Deleted Successfully")
//        );
//    }
}
