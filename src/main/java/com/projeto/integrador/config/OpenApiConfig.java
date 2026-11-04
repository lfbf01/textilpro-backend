package com.projeto.integrador.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI textilProOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("TextilPro API")
                        .description("Documentação oficial do backend de gestão têxtil (integração com Supabase)")
                        .version("1.0.0"));
    }
}