package in.muktashastra.core.config.properties;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Configuration properties for Swagger/OpenAPI documentation.
 * Maps properties with prefix 'swagger' from application configuration.
 * 
 * @author Nilesh
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "swagger")
public class SwaggerProperties {
    /** Whether Swagger documentation is enabled */
    private boolean enabled = false;
    
    /** API documentation title */
    private String title = "Please update your application title in application properties file";
    
    /** API documentation description */
    private String description = "Please update your application description in application properties file";
    
    /** API version */
    private String version = "Please update your application version in application properties file";
}
