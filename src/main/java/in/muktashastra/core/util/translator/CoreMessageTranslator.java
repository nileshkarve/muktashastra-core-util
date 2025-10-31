package in.muktashastra.core.util.translator;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;

@Slf4j
@AllArgsConstructor
public class CoreMessageTranslator implements MessageTranslator {

    private final MessageSource messageSource;

    @Override
    public String translate(String code, Object... args) {
        try {
            return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
        }
        catch(NoSuchMessageException ex) {
            log.warn("Missing message for code '{}'", code);
            return code;
        }
        catch(Exception ex) {
            log.warn("Error while translating message for code '{}'", code);
            return code;
        }
    }
}
