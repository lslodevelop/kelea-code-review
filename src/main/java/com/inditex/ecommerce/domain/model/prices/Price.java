package com.inditex.ecommerce.domain.model.prices;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Price {

    private Long id;
    private Brand brand;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Integer priceList;
    private Long productId;
    private Integer priority;
    private BigDecimal amount;
    private Currency currency;
}
