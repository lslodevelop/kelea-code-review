package com.inditex.ecommerce.domain.model.price;

import lombok.Data;

import java.util.regex.Pattern;

@Data
public class Currency {

    private static final Pattern ISO_PATTERN = Pattern.compile("^[A-Z]{3}$");
    private String code;

    public Currency(final String code) {
        if (code == null || !ISO_PATTERN.matcher(code).matches()) {
            throw new IllegalArgumentException("Invalid ISO currency code: " + code);
        }
        this.code = code;
    }
}
