package org.terning.terningserver.common.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        servers = {
                @Server(url = "https://www.terning-official.p-e.kr", description = "Default Server url"),
                @Server(url = "https://www.terning-official.n-e.kr", description = "Default Server url (2025 ver.)"),
                @Server(url = "http://15.165.242.132", description = "Staging Server URL"),
                @Server(url = "http://54.180.215.35", description = "Staging Server URL (2025 ver.)"),
                @Server(url = "http://localhost:8080", description = "Local Development Server URL")
        }
)
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {
        String jwt = "JWT";
        SecurityRequirement securityRequirement = new SecurityRequirement().addList(jwt);
        Components components = new Components().addSecuritySchemes(jwt, new SecurityScheme()
                .name(jwt)
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
        );
        return new OpenAPI()
                .components(new Components())
                .info(apiInfo())
                .addSecurityItem(securityRequirement)
                .components(components);
    }

    private Info apiInfo() {
        return new Info()
                .title("Terning Point Swagger") // API의 제목
                .description("Terning Point Swagger ver. API 명세서") // API에 대한 설명
                .version("1.1.0"); // API의 버전
    }
}
