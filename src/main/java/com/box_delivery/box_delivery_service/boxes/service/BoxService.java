package com.box_delivery.box_delivery_service.boxes.service;

import com.box_delivery.box_delivery_service.boxes.dto.BoxDto;
import com.box_delivery.box_delivery_service.boxes.dto.BoxResponse;
import com.box_delivery.box_delivery_service.boxes.entity.BoxEntity;
import com.box_delivery.box_delivery_service.boxes.enums.BoxState;
import com.box_delivery.box_delivery_service.boxes.mapper.BoxMapper;
import com.box_delivery.box_delivery_service.boxes.repository.BoxRepository;
import com.box_delivery.box_delivery_service.common.exception.CapacityExceededException;
import com.box_delivery.box_delivery_service.common.exception.InvalidResourceException;
import com.box_delivery.box_delivery_service.common.exception.ResourceAlreadyExistException;
import com.box_delivery.box_delivery_service.common.exception.ResourceNotFoundException;
import com.box_delivery.box_delivery_service.items.dto.ItemDto;
import com.box_delivery.box_delivery_service.items.dto.ItemResponse;
import com.box_delivery.box_delivery_service.items.mapper.ItemMapper;
import com.box_delivery.box_delivery_service.items.repository.ItemRepository;
import com.box_delivery.box_delivery_service.items.service.ItemService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BoxService {
    private final BoxRepository boxRepository;
    private final ItemRepository itemRepository;
    private final ItemService itemService;
    private final BoxMapper boxMapper;
    private final ItemMapper itemMapper;
    private final static Integer MINIMUM_LOADING_BATTERY  =25;

    @Transactional
    public BoxResponse createBox(BoxDto.CreateBoxRequest request){
        if(boxRepository.existsByTxrefAndDeletedFalse(request.txref())) {
            throw new ResourceAlreadyExistException( "Box with txref already exists");
        }

        BoxEntity box = BoxEntity.builder()
                .txref(request.txref())
                .batteryLevel(request.batteryLevel())
                .state(BoxState.IDLE)
                .build();
        BoxEntity saved = boxRepository.save(box);
        return boxMapper.toResponse(saved);
    }

    public BoxResponse updateBox(BoxDto.UpdateBoxRequest request) {

        BoxEntity itemEntity = boxRepository.findByIdAndDeletedFalse(request.id()).orElseThrow(
                () -> new ResourceNotFoundException("Box does not exist. Update Failed")

        );
        BoxEntity updated = boxRepository.save(boxMapper.toUpdate(request, itemEntity));
        return boxMapper.toResponse(updated);
    }

    public Page<BoxResponse> getAllBoxes(Pageable pageable) {

        Page<BoxEntity> allBoxes = boxRepository.findAll(pageable);
        return allBoxes.map(boxMapper::toResponse);
    }

//    public Page<BoxDto.AvailableBoxes> getAvailableBoxes(Pageable pageable) {
//        Page<BoxEntity> availableBoxes = boxRepository.findAvailableBoxes( pageable);
//        return availableBoxes.map(boxMapper::toResponse);
//    }

    public BoxResponse getABox(UUID id) {

        BoxEntity itemEntity = boxRepository.findByIdAndDeletedFalse(id).orElseThrow(
                () -> new ResourceNotFoundException("Box does not exist. Update Failed")

        );
        return boxMapper.toResponse(itemEntity);
    }

    @Transactional
    public BoxDto.BoxLoadedResponse loadItem(UUID boxId,
                                             BoxDto.LoadBoxRequest request) {
        BoxEntity box = boxRepository.findByIdForUpdate(boxId).orElseThrow(
                () -> new ResourceNotFoundException("Box not found")
        );

        // box cannot be in LOADING state when battery < 25%.
        if (box.getBatteryLevel() < MINIMUM_LOADING_BATTERY) {
            throw new InvalidResourceException(
                    "Box battery level must be at least 25% to load items"
            );
        }


        if (box.getState() != BoxState.IDLE &&
                box.getState() != BoxState.LOADING) {

            throw new InvalidResourceException(
                    "Box cannot be loaded in state " + box.getState()
            );
        }

        int currentWeight = itemRepository
                .sumWeightByBoxId(boxId);

        int incomingWeight = request.createItemRequests()
                .stream()
                .mapToInt(ItemDto.CreateItemRequest::weight)
                .sum();

        int totalWeight = currentWeight + incomingWeight;


        if (totalWeight > box.getRemainingCapacity()) {
            throw new CapacityExceededException(
                    "Box capacity is 5000g. Requested total: "
                            + totalWeight + "g"
            );
        }

        box.setState(BoxState.LOADING);
        List<ItemResponse> items = request.createItemRequests()
                .stream()
                .map(item -> itemService.createItem(item, boxId)
                )
                .toList();
//        for (ItemDto.CreateItemRequest requestItem : request.createItemRequests()) {
//            itemService.createItem(requestItem, boxId);
//        }
        box.setCurrentWeight(
                box.getCurrentWeight() + incomingWeight
        );
        box.setState(BoxState.LOADED);
        boxRepository.save(box);

        return boxMapper.toBoxLoadedResponse(box, request.createItemRequests().size(), items);
    }


    @Transactional
    public BoxDto.BatteryResponse getBattery(UUID boxId) {
        BoxEntity box = boxRepository.findById(boxId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Box not found: " + boxId
                        )
                );
        return BoxDto.BatteryResponse.from(box);
    }

    @Transactional
    public List<ItemResponse> getLoadedItems(UUID boxId) {

        if (!boxRepository.existsById(boxId)) {
            throw new ResourceNotFoundException(
                    "Box not found: " + boxId
            );
        }

        return itemRepository.findByBoxId(boxId)
                .stream()
                .map(itemMapper::toResponse)
                .toList();
    }

    public BoxResponse toggleCamera(UUID boxId) {

        BoxEntity box = boxRepository.findById(boxId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Box not found: " + boxId
                ));

        box.setCameraEnabled(!box.isCameraEnabled());
        return boxMapper.toResponse( boxRepository.save(box));
    }

    @Transactional
    public List<BoxResponse> getAvailableBoxes() {

        List<BoxEntity> boxes =
                boxRepository.findByStateAndDeletedFalseAndBatteryLevelGreaterThanEqual(
                        BoxState.IDLE,
                        25
                );

        return boxes.stream()
                .map(boxMapper::toResponse)
                .toList();
    }


    public void deleteBox(UUID id) {

        BoxEntity itemEntity = boxRepository.findByIdAndDeletedFalse(id).orElseThrow(
                () -> new ResourceNotFoundException("Box does not exist. Update Failed")

        );
        itemEntity.softDelete();
        BoxEntity saved = boxRepository.save(itemEntity);
    }




}
