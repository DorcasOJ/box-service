package com.box_delivery.box_delivery_service.common.exception;

import org.springframework.http.HttpStatus;

public class CapacityExceededException extends BusinessException {
    public CapacityExceededException(String message) {

        super(HttpStatus.BAD_REQUEST, "CAPACITY_EXCEEDED", message);
    }
}
