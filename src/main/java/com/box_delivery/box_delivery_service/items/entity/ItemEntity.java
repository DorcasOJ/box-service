package com.box_delivery.box_delivery_service.items.entity;

import com.box_delivery.box_delivery_service.common.entity.BaseEntity;
import com.box_delivery.box_delivery_service.items.enums.ItemStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.UUID;

@Entity
@Table(name = "items")
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class ItemEntity extends BaseEntity {

    private String name;

    private Integer weight;

    private String code;

    @Column(name = "box_id", nullable = false)
    private UUID boxId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private ItemStatus status = ItemStatus.LOADED;
}
