package com.Project.kharchaTracker.dto;

import com.Project.kharchaTracker.exception.ErrorCode;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;
import java.util.Map;

@Getter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponseDto {
    private Instant timestamp;
    private int status;
    private ErrorCode code;
    private String message;
    private String path;
    private Map<String, String> fieldErrors;
}
