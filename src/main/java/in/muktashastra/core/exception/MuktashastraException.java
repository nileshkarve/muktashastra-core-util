package in.muktashastra.core.exception;

public class MuktashastraException extends Exception {

    public MuktashastraException(String message) {
        super(message);
    }

    public MuktashastraException(Throwable cause) {
        super(cause);
    }

    public MuktashastraException(String message, Throwable cause) {
        super(message, cause);
    }

    public MuktashastraException(String message, Object... args) {
        super(String.format(message, args));
    }

    public MuktashastraException(String message, Throwable cause, Object... args) {
        super(String.format(message, args), cause);
    }
}
