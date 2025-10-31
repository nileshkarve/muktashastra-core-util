package in.muktashastra.core.config;

import in.muktashastra.core.config.properties.PersistenceFrameworkProperties;
import in.muktashastra.core.persistence.relationalstore.RelationalDatabaseEntityMetadataPreLoader;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
@EnableConfigurationProperties(PersistenceFrameworkProperties.class)
@ConditionalOnProperty(
        prefix = "application.persistence-framework",
        name = "enabled",
        havingValue = "true"
)
public class PersistenceFrameworkConfig {

    @Bean
    @ConditionalOnMissingBean
    public DataSource applicationDataSource(PersistenceFrameworkProperties properties) {
        DataSourceBuilder<?> builder = DataSourceBuilder.create();
        builder.url(properties.getDataSourceProperties().getUrl()).driverClassName(properties.getDataSourceProperties().getDriverClassName()).username(properties.getDataSourceProperties().getUsername()).password(properties.getDataSourceProperties().getPswrd());
        return builder.build();
    }

    @Bean
    @ConditionalOnMissingBean
    public JdbcTemplate applicationJdbcTemplate(DataSource applicationDataSource) {
        return new JdbcTemplate(applicationDataSource);
    }

    @Bean
    public RelationalDatabaseEntityMetadataPreLoader relationalDatabaseEntityMetadataPreLoader(PersistenceFrameworkProperties properties) {
        return new RelationalDatabaseEntityMetadataPreLoader(properties.getPersistableEntityPackageNames());
    }

}
