package com.inditex.ecommerce.interfaces.web.controller;

import com.inditex.ecommerce.interfaces.aop.TraceableEndpoint;
import com.inditex.ecommerce.interfaces.model.PriceDto;
import com.inditex.ecommerce.interfaces.web.adapter.PriceInterfaceAdapter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ecommerce/v1/prices")
@Slf4j
@RequiredArgsConstructor
public class PriceV1Controller {

    private final PriceInterfaceAdapter priceInterfaceAdapter;

    @TraceableEndpoint
    @Operation(summary = "Get a price by id", operationId = "getPrice", method = "GET", tags = {"Prices"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Price retrieved successfully")})
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(HttpStatus.OK)
    public PriceDto getPrice(@RequestParam(value = "applyDate") final String applyDate,
                             @RequestParam(value = "productId") final Long productId,
                             @RequestParam(value = "brandId") final Long brandId) {
        return priceInterfaceAdapter.getPrice(applyDate, productId, brandId);
    }

}
