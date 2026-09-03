package com.box_delivery.box_delivery_service.common.exception;

import org.springframework.http.HttpStatus;

public class ResourceAlreadyExistException extends BusinessException {
    public ResourceAlreadyExistException(String message) {

        super(HttpStatus.CONFLICT, "RESOURCE_ALREADY_EXPIRED", message);
    }
}
