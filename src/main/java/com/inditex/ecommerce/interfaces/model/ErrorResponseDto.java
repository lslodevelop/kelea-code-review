package com.inditex.ecommerce.interfaces.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "DTO that models an application error")
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorResponseDto(

        @Schema(description = "Custom application error code")
        String code,

        @Schema(description = "Error message")
        String message,

        @Schema(description = "Timestamp of the error")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "UTC")
        LocalDateTime timestamp
) {}