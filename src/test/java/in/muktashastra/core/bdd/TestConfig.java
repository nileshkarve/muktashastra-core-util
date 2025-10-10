package in.muktashastra.core.bdd;

import in.muktashastra.core.config.PersistenceFrameworkProperties;
import in.muktashastra.core.entity.TestEntity;
import in.muktashastra.core.persistence.repo.PersistableEntityRepoImpl;
import in.muktashastra.core.util.CoreUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class TestConfig {
    @Bean
    public PersistableEntityRepoImpl<TestEntity> testEntityRepoImpl(PersistenceFrameworkProperties properties, JdbcTemplate jdbcTemplate, CoreUtil coreUtil) {
        return new PersistableEntityRepoImpl<TestEntity>(jdbcTemplate, coreUtil, properties.getMaximumParametersPerBatchQuery());
    }
}
