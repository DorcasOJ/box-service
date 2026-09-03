package com.box_delivery.box_delivery_service.boxes.repository;

import com.box_delivery.box_delivery_service.boxes.entity.BoxEntity;
import com.box_delivery.box_delivery_service.boxes.enums.BoxState;
import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BoxRepository extends JpaRepository<BoxEntity, UUID> {
    boolean existsByTxrefAndDeletedFalse(String txref);

    Optional<BoxEntity> findByIdAndDeletedFalse(UUID id);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("""
    SELECT b
    FROM BoxEntity b
    WHERE b.id = :id 
    AND b.deleted = false
    """)
    Optional<BoxEntity> findByIdForUpdate(UUID id);

    @Query("""
    SELECT b
    FROM BoxEntity b
    WHERE b.state = :state
      AND b.batteryLevel >= :minimumBattery
      AND b.deleted = false
""")
    Page<BoxEntity> findAvailableBoxes(
            BoxState state,
            Integer minimumBattery,
            Pageable page
    );

    List<BoxEntity> findByStateAndDeletedFalseAndBatteryLevelGreaterThanEqual(
            BoxState state,
            Integer batteryLevel
    );

}
