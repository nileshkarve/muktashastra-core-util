package in.muktashastra.core.exception;

public class CoreException extends Exception {

    public CoreException(String message) {
        super(message);
    }

    public CoreException(Throwable cause) {
        super(cause);
    }

    public CoreException(String message, Throwable cause) {
        super(message, cause);
    }

    public CoreException(String message, Object... args) {
        super(String.format(message, args));
    }

    public CoreException(String message, Throwable cause, Object... args) {
        super(String.format(message, args), cause);
    }
}
