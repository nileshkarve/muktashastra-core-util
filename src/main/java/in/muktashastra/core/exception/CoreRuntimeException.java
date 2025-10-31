package in.muktashastra.core.exception;

import lombok.Getter;

@Getter
public class CoreRuntimeException extends RuntimeException {

    private final Object[] args;

    public CoreRuntimeException(String message) {
        super(message);
        this.args =  new Object[0];
    }

    public CoreRuntimeException(Throwable cause) {
        super(cause);
        this.args =  new Object[0];
    }

    public CoreRuntimeException(String message, Throwable cause) {
        super(message, cause);
        this.args =  new Object[0];
    }

    public CoreRuntimeException(String message, Object... args) {
        super(message);
        this.args = args;
    }

    public CoreRuntimeException(String message, Throwable cause, Object... args) {
        super(message, cause);
        this.args = args;
    }
}
