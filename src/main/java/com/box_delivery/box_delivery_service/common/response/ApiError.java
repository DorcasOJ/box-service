package com.vehiqon.common.api.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApiError<T> {
    private Boolean success;
    private String responseCode;
    private String message;
    private T error;
//    "error": { "code": "CAR_NOT_FOUND", "message": "Car not found", "details": null },
    private String path;
    private String requestId;
    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();
}
