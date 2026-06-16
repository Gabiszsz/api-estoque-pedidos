package com.estoque.pedidos.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Gestão de Estoque e Pedidos")
                        .version("v1.0.0")
                        .description("Documentação completa da API REST para gerenciamento de estoque, clientes, fornecedores e pedidos desenvolvida para a disciplina de Programação Web II no curso de Sistemas de Informação.")
                        .contact(new Contact()
                                .name("Marcus Augusto, Gabriella e Letícia")));



    }

}
