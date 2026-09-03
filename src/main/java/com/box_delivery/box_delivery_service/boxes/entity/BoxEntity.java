package com.box_delivery.box_delivery_service.boxes.entity;

import com.box_delivery.box_delivery_service.boxes.enums.BoxState;
import com.box_delivery.box_delivery_service.common.entity.BaseEntity;
import com.box_delivery.box_delivery_service.common.exception.InvalidResourceException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "boxes")
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class BoxEntity extends BaseEntity {

    @Column(nullable = false, unique = true, length = 20)
    private String txref;

    @Column(nullable = false)
    private Integer batteryLevel;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BoxState state;

    @Column(nullable = false)
    private Integer maxWeight;

    @Column(nullable = false)
    private Integer currentWeight = 0;

    @Column(nullable = false)
    private boolean cameraEnabled = true;

    public int getRemainingCapacity() {
        return maxWeight - currentWeight;
    }

    @Version
    private Long version;

    public void transitionTo(BoxState target) {

        if (!state.canTransitionTo(target)) {
            throw new InvalidResourceException(
                    "Cannot transition box from "
                            + state
                            + " to "
                            + target
            );
        }

        this.state = target;
    }

    public void decreaseBattery(int amount) {
        this.batteryLevel = Math.max(0, this.batteryLevel - amount);
    }

    public void increaseBattery(int amount) {
        this.batteryLevel = Math.min(100, this.batteryLevel + amount);
    }

}
