package com.project.Offering_Booking_System.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {



    @Value("${server.url}")
    private String serverURL;

    @Value("${server.description}")
    private String description;


    @Bean
    public OpenAPI customOpenAPI()
    {

        Server server = new Server();
        server.setUrl(serverURL);
        server.setDescription(description);

        return new OpenAPI()
                .info( new Info().title("Global Class Offering Booking System "))
                .servers(List.of(server))
                .addSecurityItem( new SecurityRequirement().addList("JavaInUseSecurityScheme"))
                .components( new Components().addSecuritySchemes("JavaInUseSecurityScheme"  , new SecurityScheme()
                        .name("JavaInUseSecurityScheme").type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")));

    }


}
