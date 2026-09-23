package com.wendson.sistemachamados.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI sistemaChamadosOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API do Sistema de Chamados")
                        .version("1.0.0")
                        .description(
                                "API para gerenciamento de usuários, categorias, "
                                        + "chamados, comentários e históricos."
                        ));
    }
}