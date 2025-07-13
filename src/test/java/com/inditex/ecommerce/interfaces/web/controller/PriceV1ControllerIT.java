package com.inditex.ecommerce.interfaces.web.controller;

import com.inditex.ecommerce.domain.exception.ApplicationErrorCodes;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PriceV1ControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @ParameterizedTest
    @CsvSource({
            "2020-06-14T10:00:00,35455,1,1",
            "2020-06-14T16:00:00,35455,1,2",
            "2020-06-14T21:00:00,35455,1,1",
            "2020-06-15T10:00:00,35455,1,3",
            "2020-06-16T21:00:00,35455,1,4"
    })
    void getPriceOkParameterizedTest(final String applyDate, final String productId, final String brandId,
                                      final Long expectedPriceList) throws Exception {
        mockMvc.perform(get("/ecommerce/v1/prices")
                        .param("applyDate", applyDate)
                        .param("productId", productId)
                        .param("brandId", brandId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.priceList").value(expectedPriceList));
    }

    @Test
    void getPriceNotFoundTest() throws Exception {
        mockMvc.perform(get("/ecommerce/v1/prices")
                        .param("applyDate", "2025-06-14T10:00:00")
                        .param("productId", "35455")
                        .param("brandId", "1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value(ApplicationErrorCodes.NOT_FOUND_PRICE_ERROR.getCode()))
                .andExpect(jsonPath("$.message")
                        .value(Matchers.containsString("Price not found for provided criteria")));
    }

    @Test
    void getPriceBadRequestInvalidDateTest() throws Exception {
        mockMvc.perform(get("/ecommerce/v1/prices")
                        .param("applyDate", "invalid-date")
                        .param("productId", "35455")
                        .param("brandId", "1"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(ApplicationErrorCodes.INVALID_LOCAL_DATE_TIME.getCode()))
                .andExpect(jsonPath("$.message")
                        .value(Matchers.containsString("The LocalDateTime is not in the correct format")));
    }

}
