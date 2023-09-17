package az.atl.msauth.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                title = "Omnio",
                description = "Open-Api documentation for Omnio application",
                contact = @Contact(
                        name = "Fuad Hajiyev",
                        email = "fuad.hajiyev.2003@gmail.com",
                        url = "https://github.com/FuadHajiyevv/omnio"
                ),
                version = "1.0"
        ),
        servers = @Server(
                description = "Omnio server",
                url = "http://localhost:8080/omnio"
        )
)
@SecurityScheme(
    name = "jwtAuth",
      description = "JWT authentication",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER,
        scheme = "bearer"
)
public class OpenApiConfig {



}
