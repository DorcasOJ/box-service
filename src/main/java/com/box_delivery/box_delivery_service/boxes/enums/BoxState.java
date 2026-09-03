package com.box_delivery.box_delivery_service.boxes.enums;

public enum BoxState {

    IDLE,
    LOADING,
    LOADED,
    DELIVERING,
    DELIVERED,
    RETURNING;

    public boolean canTransitionTo(BoxState target) {

        return switch (this) {
            case IDLE ->
                    target == LOADING;

            case LOADING ->
                    target == LOADED;

            case LOADED ->
                    target == DELIVERING;

            case DELIVERING ->
                    target == DELIVERED;

            case DELIVERED ->
                    target == RETURNING;

            case RETURNING ->
                    target == IDLE;
        };
    }


}