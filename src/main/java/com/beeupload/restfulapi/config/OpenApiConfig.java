package com.beeupload.restfulapi.config;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "BeeUpload Restful API",
                version = "1.0.0",
                description = "BeeUpload Restful API",
                contact = @Contact(
                        name = "Pablo A.",
                        email = "pabloalbrdz02@gmail.com"
                )
        )
)
public class OpenApiConfig {
}
