package com.quizapp.utils;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@RequiredArgsConstructor
public class ApiResponse {
    private String message;
    private boolean status;
    private Object data;

}
