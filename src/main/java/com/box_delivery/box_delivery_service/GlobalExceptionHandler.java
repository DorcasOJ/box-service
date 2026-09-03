package com.box_delivery.box_delivery_service;

import com.box_delivery.box_delivery_service.common.exception.BusinessException;
import com.box_delivery.box_delivery_service.common.response.ApiError;
import com.box_delivery.box_delivery_service.common.response.ErrorDetail;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError<ErrorDetail>> handleGlobalException(
            Exception ex, HttpServletRequest request
    ) {
        ErrorDetail errorDetail = ErrorDetail.builder()
                .code("INTERNAL_SERVER_ERROR")
                .message("An unexpected error occurred")
                .details(ex.getMessage())
                .build();
        ApiError<ErrorDetail> apiError = ApiError.<ErrorDetail>builder()
                .success(false)
                .responseCode(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                .message("Internal Server error")
                .error(errorDetail)
                .path(request.getRequestURI())
                .build();
        return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError<ErrorDetail>> handleValidationException(
            MethodArgumentNotValidException ex,
            HttpServletRequest request
    ) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult()
                .getFieldErrors()
                .forEach(error ->
                        errors.putIfAbsent(error.getField(), error.getDefaultMessage())
                );

        ErrorDetail errorDetail = ErrorDetail.builder()
                .code("VALIDATION_FAILED")
                .message("Input validation failed")
                .details(errors)
                .build();

        ApiError<ErrorDetail> apiError = ApiError.<ErrorDetail>builder()
                .success(false)
                .responseCode(String.valueOf(HttpStatus.BAD_REQUEST.value()))
                .message("Invalid request payload")
                .error(errorDetail)
                .path(request.getRequestURI())
                .build();

        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);

    }


    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiError<ErrorDetail>> handleBusinessException(
            BusinessException ex, HttpServletRequest request) {


        ErrorDetail errorDetail = ErrorDetail.builder()
                .code(ex.getErrorCode())
                .message(ex.getMessage())
                .details(null)
                .build();

        ApiError<ErrorDetail> apiError = ApiError.<ErrorDetail>builder()
                .success(false)
                .responseCode(String.valueOf(ex.getStatus().value()))
                .message(ex.getMessage())
                .error(errorDetail)
                .path(request.getRequestURI())
                .build();

        return new ResponseEntity<>(apiError, ex.getStatus());
    }


}
