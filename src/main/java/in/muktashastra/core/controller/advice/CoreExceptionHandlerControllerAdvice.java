package in.muktashastra.core.controller.advice;

import in.muktashastra.core.exception.CoreException;
import in.muktashastra.core.exception.CoreRuntimeException;
import in.muktashastra.core.util.translator.MessageTranslator;
import in.muktashastra.core.util.model.ErrorResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@AllArgsConstructor
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CoreExceptionHandlerControllerAdvice {

    private final MessageTranslator translator;

    @ExceptionHandler(CoreException.class)
    public ResponseEntity<ErrorResponse> handleCoreException(CoreException ex) {
        String translatedMessage = translator.translate(ex.getMessage(), ex.getArgs());
        ErrorResponse response = new ErrorResponse(ex.getMessage(), translatedMessage);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    @ExceptionHandler(CoreRuntimeException.class)
    public ResponseEntity<ErrorResponse> handleCoreRuntimeException(CoreRuntimeException ex) {
        log.error("Unhandled CoreRuntimeException caught by CoreExceptionHandler", ex);
        String translatedMessage = translator.translate(ex.getMessage(), ex.getArgs());
        ErrorResponse response = new ErrorResponse("unexpected.error", translatedMessage);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAllOtherExceptions(Exception ex) {
        log.error("Unhandled exception caught by CoreExceptionHandler", ex);
        String translatedMessage = translator.translate("unexpected.error");
        ErrorResponse response = new ErrorResponse("unexpected.error", translatedMessage);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
