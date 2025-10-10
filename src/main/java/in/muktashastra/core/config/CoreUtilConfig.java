package in.muktashastra.core.config;

import in.muktashastra.core.util.CoreUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;

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
}
