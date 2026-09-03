package com.box_delivery.box_delivery_service.items.repository;

import com.box_delivery.box_delivery_service.items.entity.ItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ItemRepository extends JpaRepository<ItemEntity, UUID> {
    boolean existsByNameAndDeletedFalse(String name);
    Optional<ItemEntity> findByIdAndDeletedFalse(UUID id);


    @Query("""
        SELECT COALESCE(SUM(i.weight), 0)
        FROM ItemEntity i
        WHERE i.boxId = :boxId
    """)
    Integer sumWeightByBoxId(UUID boxId);

    Optional<List<ItemEntity>> FindByBoxIdAndDeletedFalse(UUID boxId);
}
