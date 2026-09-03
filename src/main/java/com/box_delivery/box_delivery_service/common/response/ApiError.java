package com.box_delivery.box_delivery_service.common.response;

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
    private String path;
    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();
}
