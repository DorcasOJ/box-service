package com.box_delivery.box_delivery_service.boxes.controller;

import com.box_delivery.box_delivery_service.boxes.dto.BoxDto;
import com.box_delivery.box_delivery_service.boxes.dto.BoxResponse;
import com.box_delivery.box_delivery_service.boxes.service.BoxService;
import com.box_delivery.box_delivery_service.common.mapper.ApiResponseMapper;
import com.box_delivery.box_delivery_service.common.response.ApiResponse;
import com.box_delivery.box_delivery_service.items.dto.ItemResponse;
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

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/boxes")
@Tag(name = "Boxes", description = "Box Service APIs")
@RequiredArgsConstructor
public class BoxController {

    private final BoxService boxService;
    private final ApiResponseMapper apiResponseMapper;

    @Operation(
            summary = "Get all boxes",
            description = "Returns all boxes"
    )
    @GetMapping("")
    public ResponseEntity<ApiResponse<Page<BoxResponse>>> getAllBoxes(
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
                apiResponseMapper.toResponse(boxService.getAllBoxes(pageable))
        );
    }

    @Operation(
            summary = "Get a box",
            description = "Returns a box"
    )
    @GetMapping("/{boxId}")
    public ResponseEntity<ApiResponse<BoxResponse>> getABox(
            @PathVariable UUID boxId
            ) {
        return ResponseEntity.ok(
                apiResponseMapper.toResponse(boxService.getABox(boxId))
        );
    }


    @Operation(
            summary = "Create a box",
            description = "Returns a box"
    )
    @PostMapping("")
    public ResponseEntity<ApiResponse<BoxResponse>> createBox(
            @RequestBody BoxDto.CreateBoxRequest request
            ) {
        return ResponseEntity.ok(
                apiResponseMapper.toResponse(boxService.createBox(request))
        );
    }

    @Operation(
            summary = "Update a box",
            description = "Returns a box"
    )
    @PatchMapping("")
    public ResponseEntity<ApiResponse<BoxResponse>> updateBox(
            @RequestBody BoxDto.UpdateBoxRequest request
    ) {
        return ResponseEntity.ok(
                apiResponseMapper.toResponse(boxService.updateBox(request))
        );
    }


    @Operation(
            summary = "Load items to a box",
            description = "Returns a box"
    )
    @PostMapping("/{boxId}/items")
    public ResponseEntity<ApiResponse<BoxDto.BoxLoadedResponse>> loadBox(
            @PathVariable UUID boxId,
            @RequestBody BoxDto.LoadBoxRequest request
    ) {
        return ResponseEntity.ok(
                apiResponseMapper.toResponse(boxService.loadItem(boxId, request))
        );
    }



//    @GetMapping("/{boxId}/items")
//    public ResponseEntity<ApiResponse<List<ItemResponse>>> getItems(
//            @PathVariable UUID boxId) {
//
//        return ResponseEntity.ok( apiResponseMapper.toResponse(
//                boxService.getLoadedItems(boxId)
//                )
//        );
//    }

    @GetMapping("/available")
    public ResponseEntity<ApiResponse<List<BoxResponse>>> getAvailableBoxes() {

        return ResponseEntity.ok( apiResponseMapper.toResponse(
                boxService.getAvailableBoxes()
                )
        );
    }


    @PatchMapping("/{boxId}/camera/toggle")
    public ResponseEntity<ApiResponse<BoxResponse>> toggleCamera(
            @PathVariable UUID boxId
    ) {
        return ResponseEntity.ok(apiResponseMapper.toResponse(
                boxService.toggleCamera(boxId)
                )
        );
    }


    @GetMapping("/{boxId}/battery")
    public ResponseEntity<BoxDto.BatteryResponse> getBattery(
            @PathVariable UUID boxId) {
        return ResponseEntity.ok(
                boxService.getBattery(boxId)
        );
    }

    @PatchMapping("/{boxId}/battery/recharge")
    public ResponseEntity<ApiResponse<BoxResponse>> rechargeBattery(
            @PathVariable UUID boxId) {
        return ResponseEntity.ok(apiResponseMapper.toResponse(
                boxService.rechargeBattery(boxId)
                )
        );
    }


    @GetMapping("/{boxId}/readiness")
    public ResponseEntity<ApiResponse<BoxDto.BoxReadinessResponse>> getBoxReadiness(
            @PathVariable UUID boxId
    ) {
        return ResponseEntity.ok( apiResponseMapper.toResponse(
                boxService.getReadiness(boxId)
                )
        );
    }


    @Operation(
            summary = "Delete a box",
            description = "Returns success"
    )
    @DeleteMapping("/{boxId}")
    public ResponseEntity<ApiResponse<String>> deleteBox(
            @PathVariable UUID boxId
    ) {
        boxService.deleteBox(boxId);
        return ResponseEntity.ok(
                apiResponseMapper.toResponse("Box Deleted Successfully")
        );
    }


}
