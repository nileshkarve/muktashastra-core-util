package in.muktashastra.core.config;

import in.muktashastra.core.controller.advice.CoreExceptionHandlerControllerAdvice;
import in.muktashastra.core.util.translator.CoreMessageTranslator;
import in.muktashastra.core.util.translator.MessageTranslator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static in.muktashastra.core.util.constant.CoreConstant.FILE_NAME_EXTENSION_PROPERTIES;
import static in.muktashastra.core.util.constant.CoreConstant.MULTI_LINGUAL_MESSAGE_FILES_FOLDER_NAME;

@Configuration
@ConditionalOnProperty(
        prefix = "application.exception-handler",
        name = "enabled",
        havingValue = "true",
        matchIfMissing = true
)
public class CoreExceptionConfig {

    @Bean
    @ConditionalOnMissingBean(MessageSource.class)
    public MessageSource coreMessageSource() {
        List<String> messageSourceFileNames = extractMessageSourceFileNames();
        ResourceBundleMessageSource source = new ResourceBundleMessageSource();
        source.setBasenames(messageSourceFileNames.toArray(String[]::new)); // looks for all messages.properties files
        source.setDefaultEncoding("UTF-8");
        source.setUseCodeAsDefaultMessage(true);
        return source;
    }

    @Bean
    @ConditionalOnMissingBean(MessageTranslator.class)
    public MessageTranslator coreMessageTranslator(MessageSource messageSource) {
        return new CoreMessageTranslator(messageSource);
    }

    @Bean
    @ConditionalOnMissingBean(CoreExceptionHandlerControllerAdvice.class)
    public CoreExceptionHandlerControllerAdvice coreExceptionHandler(MessageTranslator translator) {
        return new CoreExceptionHandlerControllerAdvice(translator);
    }

    private List<String> extractMessageSourceFileNames() {
        List<String> baseNames = new ArrayList<>();
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        try {
            // Matches both filesystem and JAR resources
            Resource[] resources = resolver.getResources("classpath*:" + MULTI_LINGUAL_MESSAGE_FILES_FOLDER_NAME + "/*" +FILE_NAME_EXTENSION_PROPERTIES);

            for (Resource resource : resources) {
                String filename = resource.getFilename();
                if (filename != null && filename.endsWith(FILE_NAME_EXTENSION_PROPERTIES)) {
                    String baseName = filename.substring(0, filename.length() - FILE_NAME_EXTENSION_PROPERTIES.length());
                    baseNames.add(MULTI_LINGUAL_MESSAGE_FILES_FOLDER_NAME + "/" + baseName);
                }
            }
            // Ensure default core-message.properties is always included first
            String coreBaseName = MULTI_LINGUAL_MESSAGE_FILES_FOLDER_NAME + "/core-message";
            if (!baseNames.contains(coreBaseName)) {
                baseNames.addFirst(coreBaseName);
            }
        }
        catch(IOException e) {
            // Fallback to default if scanning fails
            baseNames.clear();
            baseNames.add(MULTI_LINGUAL_MESSAGE_FILES_FOLDER_NAME + "/core-message");
        }
        return baseNames;
    }
}
