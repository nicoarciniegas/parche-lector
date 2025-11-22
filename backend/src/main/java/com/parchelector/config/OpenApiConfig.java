package com.parchelector.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

/**
 * OpenAPI/Swagger configuration.
 * 
 * @author Nicolas Arciniegas
 */
@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "Parche Lector API",
        version = "1.0.0",
        description = "API documentation for Parche Lector - Social reading platform",
        contact = @Contact(
            name = "Nicolas Arciniegas",
            email = "contact@parchelector.com"
        )
    ),
    servers = {
        @Server(url = "https://parche-lector.onrender.com", description = "Production server"),
        @Server(url = "http://localhost:8080", description = "Local development server")
    }
)
@SecurityScheme(
    name = "bearer-jwt",
    type = SecuritySchemeType.HTTP,
    scheme = "bearer",
    bearerFormat = "JWT",
    description = "Enter your JWT token obtained from /auth/login endpoint"
)
public class OpenApiConfig {
}
