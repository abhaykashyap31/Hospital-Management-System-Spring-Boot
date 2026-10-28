package com.example.hospitalmanagement.common;

import java.time.Instant;
import java.util.Map;

public record ApiResponse<T>(
        Instant timestamp,
        boolean success,
        String message,
        T data,
        Map<String, String> errors
) {
    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(Instant.now(), true, message, data, Map.of());
    }

    public static ApiResponse<Void> failure(String message, Map<String, String> errors) {
        return new ApiResponse<>(Instant.now(), false, message, null, errors);
    }
}
