package com.box_delivery.box_delivery_service.common.exception;

import org.springframework.http.HttpStatus;

public class InvalidResourceException extends BusinessException {
    public InvalidResourceException(String message) {

        super(HttpStatus.BAD_REQUEST, "INVALID_RESOURCE", message);
    }
}
