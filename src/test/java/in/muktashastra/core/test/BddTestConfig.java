package in.muktashastra.core.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import in.muktashastra.core.config.properties.PersistenceFrameworkProperties;
import in.muktashastra.core.entity.TestEntity;
import in.muktashastra.core.persistence.relationalstore.repo.CorePersistableEntityRepo;
import in.muktashastra.core.util.CoreUtil;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.jdbc.core.JdbcTemplate;

@TestConfiguration
public class BddTestConfig {
    @Bean
    public CorePersistableEntityRepo<TestEntity> testEntityRepoImpl(PersistenceFrameworkProperties properties, JdbcTemplate jdbcTemplate, CoreUtil coreUtil) {
        return new CorePersistableEntityRepo<>(jdbcTemplate, coreUtil, TestEntity.class, properties.getMaximumParametersPerBatchQuery());
    }

    @Bean
    public TestRestTemplate testRestTemplate(ObjectMapper objectMapper) {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setObjectMapper(objectMapper);
        return new TestRestTemplate(new RestTemplateBuilder().messageConverters(converter));
    }
}
