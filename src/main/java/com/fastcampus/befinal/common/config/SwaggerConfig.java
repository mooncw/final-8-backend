package com.fastcampus.befinal.common.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@OpenAPIDefinition(
        info = @Info(
                title = "Final API Docs",
                description = "Implemented API Specification",
                version = "v1"
        )
)
@Configuration
public class SwaggerConfig {
    @Value("${spring.profiles.active}")
    private String activeProfile;

    @Value("${server.port}")
    private String port;

    @Bean
    public OpenAPI openAPI() {
        String jwt = "JWT";
        SecurityRequirement securityRequirement = new SecurityRequirement().addList(jwt);
        Components components = new Components()
                .addSecuritySchemes(jwt, new SecurityScheme()
                        .name(jwt)
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("Bearer")
                        .bearerFormat(jwt));

        String url = "";
        if("local".equals(activeProfile)){
            url = "http://localhost:" + port;
        } else{
            url = "https://neuroflow-fastcampus.store";
        }

        return new OpenAPI()
                .addSecurityItem(securityRequirement)
                .components(components)
                .servers(List.of(new Server().url(url)));
    }

}