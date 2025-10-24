package in.muktashastra.core.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import in.muktashastra.core.persistence.model.EntityId;
import in.muktashastra.core.util.CoreUtil;
import in.muktashastra.core.util.serialization.EntityIdDeserializer;
import in.muktashastra.core.util.serialization.EntityIdSerializer;
import in.muktashastra.core.util.serialization.InstantDeserializer;
import in.muktashastra.core.util.serialization.InstantSerializer;
import in.muktashastra.core.util.serialization.LocalDateDeserializer;
import in.muktashastra.core.util.serialization.LocalDateSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;

/**
 * Spring configuration class for Muktashastra core utilities.
 * Provides bean definitions for core utility components.
 * 
 * @author Nilesh
 */
@Configuration
public class CoreUtilConfig {

    /**
     * Creates a CoreUtil bean for utility operations.
     * 
     * @return CoreUtil instance
     */
    @Bean
    public CoreUtil coreUtil() {
        return new CoreUtil();
    }

    @Bean
    public Clock clock() {
        return Clock.systemUTC();
    }

    /**
     * Creates an ObjectMapper bean with custom serialization/deserialization modules.
     * 
     * @return ObjectMapper instance configured with custom modules
     */
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        
        SimpleModule customModule = new SimpleModule();
        customModule.addSerializer(Instant.class, new InstantSerializer());
        customModule.addDeserializer(Instant.class, new InstantDeserializer());
        customModule.addSerializer(EntityId.class, new EntityIdSerializer());
        customModule.addDeserializer(EntityId.class, new EntityIdDeserializer());
        customModule.addSerializer(LocalDate.class, new LocalDateSerializer());
        customModule.addDeserializer(LocalDate.class, new LocalDateDeserializer());
        
        mapper.registerModule(customModule);
        return mapper;
    }
}
