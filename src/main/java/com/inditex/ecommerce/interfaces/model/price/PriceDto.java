package com.inditex.ecommerce.interfaces.model.price;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO that models the price")
public class PriceDto {

    @Schema(description = "Price ID")
    private Long id;

    @Schema(description = "Brand ID")
    private Long brandId;

    @Schema(description = "Start date for the price")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "UTC")
    private LocalDateTime startDate;

    @Schema(description = "End date for the price")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "UTC")
    private LocalDateTime endDate;

    @Schema(description = "Price List")
    private Integer priceList;

    @Schema(description = "Product ID")
    private Long productId;

    @Schema(description = "Amount value")
    private BigDecimal amount;

    @Schema(description = "Price currency")
    private String currency;

}
