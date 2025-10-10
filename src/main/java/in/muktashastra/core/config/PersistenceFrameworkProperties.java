package in.muktashastra.core.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@Getter
@Setter
@ConfigurationProperties(prefix = "application.persistence-framework")
public class PersistenceFrameworkProperties {

    private boolean enabled = false;
    private List<String> persistableEntityPackageNames;
    private Integer maximumParametersPerBatchQuery = 1200;

    private DataSourceProperties dataSourceProperties = new DataSourceProperties();

    @Getter
    @Setter
    public static class DataSourceProperties {
        private String url;
        private String username;
        private String pswrd;
        private String driverClassName;
    }
}
