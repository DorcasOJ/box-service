package com.box_delivery.box_delivery_service.common.mapper;

import com.box_delivery.box_delivery_service.common.response.ApiResponse;
import org.springframework.stereotype.Component;

@Component
public class ApiResponseMapper {
    public <T> ApiResponse<T> toResponse (T data) {
        return ApiResponse.<T>builder()
                .success(true)
                .responseCode("200")
                .responseMessage("Success")
                .data(data).build();

    }
}