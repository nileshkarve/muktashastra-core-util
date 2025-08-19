package in.muktashastra.core.config;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "swagger")
public class SwaggerProperties {
    private boolean enabled = false;
    private String title = "Please update your application title in application properties file";
    private String description = "Please update your application description in application properties file";
    private String version = "Please update your application version in application properties file";
}
