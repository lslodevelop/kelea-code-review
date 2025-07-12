package com.inditex.ecommerce.interfaces.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorResponseDto(String code, String message, LocalDateTime timestamp) {}