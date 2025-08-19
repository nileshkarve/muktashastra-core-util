package in.muktashastra.core.config;

import in.muktashastra.core.util.IdGenerator;
import in.muktashastra.core.util.MuktashastraIdGenerator;
import in.muktashastra.core.util.MuktashastraUtil;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MuktashastraConfig {

    @Bean
    @ConditionalOnMissingBean
    public IdGenerator idGenerator() {
        return new MuktashastraIdGenerator();
    }

    @Bean
    public MuktashastraUtil muktashastraUtil() {
        return new MuktashastraUtil();
    }
}
