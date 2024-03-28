package pl.norbit.backend.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Norbert Śleziak",
                        email = "norr.bitt@gmail.com",
                        url = "https://github.com/Norbit4"
                ),
                description = "Documentation for License System API",
                title = "License System API",
                version = "v1.0.0",
                license = @License(
                        name = "MIT License",
                        url = "https://opensource.org/licenses/MIT"
                )
        ),
        servers = {
                @Server(
                        description = "Local ENV",
                        url = "http://localhost:8080"
                )

        },
        security = {
                @SecurityRequirement(
                        name = "Authorization"
                )
        }
)
@SecurityScheme(
        type = SecuritySchemeType.APIKEY,
        name = "Authorization",
        description = "Api key for authorization",
        in = SecuritySchemeIn.HEADER)
public class OpenApiConfig {
}
