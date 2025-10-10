package in.muktashastra.core.exception;

public class CoreRuntimeException extends RuntimeException {

    public CoreRuntimeException(String message) {
        super(message);
    }

    public CoreRuntimeException(Throwable cause) {
        super(cause);
    }

    public CoreRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public CoreRuntimeException(String message, Object... args) {
        super(String.format(message, args));
    }

    public CoreRuntimeException(String message, Throwable cause, Object... args) {
        super(String.format(message, args), cause);
    }
}
