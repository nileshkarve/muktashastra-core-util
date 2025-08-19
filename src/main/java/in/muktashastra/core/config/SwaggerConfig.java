package in.muktashastra.core.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(SwaggerProperties.class)
@ConditionalOnClass(OpenAPI.class)
@ConditionalOnProperty(
        prefix = "swagger",
        name = "enabled",
        havingValue = "true",
        matchIfMissing = false
)
public class SwaggerConfig {

	@Bean
	public OpenAPI createSwaggerApi(SwaggerProperties props) {
		return new OpenAPI()
				          .info(new Info()
				          .title(props.getTitle())
				          .version(props.getVersion())
				          .description(props.getDescription()));
	}
}