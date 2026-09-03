package com.box_delivery.box_delivery_service.common.response;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ApiResponse<T> {
    private Boolean success;
    private String responseCode;
    private String responseMessage;
    private T data;
    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();

//    public ApiResponse() {
//        this.timestamp = LocalDateTime.now();
//    }
}
