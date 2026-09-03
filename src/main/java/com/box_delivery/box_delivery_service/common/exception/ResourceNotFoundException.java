package com.box_delivery.box_delivery_service.common.exception;

import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends BusinessException {
    public ResourceNotFoundException(String message) {

        super(HttpStatus.NOT_FOUND, "RESOURCE_NOT_CREATED", message);
    }
}
