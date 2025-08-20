package in.muktashastra.core.config;

import in.muktashastra.core.util.IdGenerator;
import in.muktashastra.core.util.MuktashastraIdGenerator;
import in.muktashastra.core.util.MuktashastraUtil;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Spring configuration class for Muktashastra core utilities.
 * Provides bean definitions for core utility components.
 * 
 * @author Nilesh
 */
@Configuration
public class MuktashastraConfig {

    /**
     * Creates an IdGenerator bean if none exists in the application context.
     * 
     * @return MuktashastraIdGenerator instance
     */
    @Bean
    @ConditionalOnMissingBean
    public IdGenerator idGenerator() {
        return new MuktashastraIdGenerator();
    }

    /**
     * Creates a MuktashastraUtil bean for utility operations.
     * 
     * @return MuktashastraUtil instance
     */
    @Bean
    public MuktashastraUtil muktashastraUtil() {
        return new MuktashastraUtil();
    }
}
