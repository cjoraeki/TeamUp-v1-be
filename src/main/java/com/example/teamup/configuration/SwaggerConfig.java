package com.example.teamup.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    private static final String SCHEME_NAME = "Authorization";

    @Bean
    public OpenAPI teamUpAPI(){
        return new OpenAPI()
                .info(new Info().title("TeamUp API Documentation")
                        .description("<b>Usage:</b> on successful login a JWT is returned. Copy the token and click the " +
                                "'Authorize' button with a lock icon located at the top right corner before the end points." +
                                "Paste the generated token prepending 'Bearer ' to it.\\n" +
                                "Eg: For a token 'xyztt3637bjsjsj', it should be this before you authorize it 'Bearer xyztt3637bjsjsj." +
                                "Note the space between Bearer and the token.\\n" +
                                "Once you authorize with a valid token, you can proceed to use test the protected routes.\\n\\n")
                        .version("v1.0")
                        .license(new License().name("Apache 2.0").url("https://springdoc.org")))
                .components(new Components()
                        .addSecuritySchemes(SCHEME_NAME, createSecurity()));
    }

    public SecurityScheme createSecurity(){
        return new SecurityScheme()
                .name(SCHEME_NAME)
                .type(SecurityScheme.Type.APIKEY)
                .in(SecurityScheme.In.HEADER);
    }
}
