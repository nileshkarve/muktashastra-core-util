package in.muktashastra.core.config;

import in.muktashastra.core.config.properties.SwaggerProperties;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Swagger/OpenAPI configuration for API documentation.
 * Conditionally creates OpenAPI bean when swagger.enabled=true and OpenAPI is on classpath.
 * 
 * @author Nilesh
 */
@Configuration
@EnableConfigurationProperties(SwaggerProperties.class)
@ConditionalOnClass(OpenAPI.class)
@ConditionalOnProperty(
        prefix = "swagger",
        name = "enabled",
        havingValue = "true"
)
public class SwaggerConfig {

	/**
	 * Creates OpenAPI bean with configuration from SwaggerProperties.
	 * 
	 * @param props swagger configuration properties
	 * @return configured OpenAPI instance
	 */
	@Bean
	public OpenAPI createSwaggerApi(SwaggerProperties props) {
		return new OpenAPI()
				          .info(new Info()
				          .title(props.getTitle())
				          .version(props.getVersion())
				          .description(props.getDescription()));
	}
}