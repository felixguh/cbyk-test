package br.com.cbyk.contas.infrastructure.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
@SecurityScheme(
	    name = "basicAuth",
	    type = SecuritySchemeType.HTTP,
	    scheme = "basic"
	)
public class SwaggerCondig {

	GroupedOpenApi publicApi() {
		return GroupedOpenApi.builder().group("public-apis").pathsToMatch("/**").build();
	}

	public OpenAPI customOpenApi() {
		return new OpenAPI().info(new Info().title("CBYK API").version("1.0.0"));

	}

}
