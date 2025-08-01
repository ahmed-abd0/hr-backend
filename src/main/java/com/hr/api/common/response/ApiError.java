package com.hr.api.common.response;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiError {
    private int status;
    private String message;
    private Map<String, String> messages = new HashMap();
    private String error;
    private LocalDateTime timestamp = LocalDateTime.now();
    private String path;
}