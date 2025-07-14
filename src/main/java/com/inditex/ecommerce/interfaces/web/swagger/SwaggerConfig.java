package com.inditex.ecommerce.interfaces.web.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        final Info info = new Info()
                .title("Prices Management API")
                .version("1.0")
                .description("API for Kelea code challenge.");
        return new OpenAPI().info(info);
    }

}
