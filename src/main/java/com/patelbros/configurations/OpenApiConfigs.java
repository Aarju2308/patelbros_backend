package com.patelbros.configurations;

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
						name = "Aarju Patel",
						email = "aarju123@icloud.com"
				),
				description = "Open Api Documentation For Spring Security",
				title = "OpenAPI Specification",
				version = "1.0.0",
				license = @License(
							name = "Patel's Licence",
							url = "hello@patelbros.com"
						),
				termsOfService = "You Can't Use"
		),
		servers = {
				@Server(
					description = "Local ENV",
					url = "http://localhost:1323"
				),
				@Server(
					description = "Development Env",
					url = "https://aarjupatel.com:2308/api/v1"
				)
		},
		security = {
				@SecurityRequirement(
						name = "bearerAuth"
				)
		}
		
)
@SecurityScheme(
		name = "bearerAuth",
		description = "JWT Authn Desc",
		scheme = "bearer",
		type = SecuritySchemeType.HTTP,
		bearerFormat = "JWT",
		in = SecuritySchemeIn.HEADER
)

public class OpenApiConfigs {

}
