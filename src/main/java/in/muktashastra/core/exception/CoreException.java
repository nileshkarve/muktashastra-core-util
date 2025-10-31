package in.muktashastra.core.exception;

import lombok.Getter;

@Getter
public class CoreException extends Exception {

    private final Object[] args;

    public CoreException(String message) {
        super(message);
        this.args =  new Object[0];
    }

    public CoreException(Throwable cause) {
        super(cause);
        this.args =  new Object[0];
    }

    public CoreException(String message, Throwable cause) {
        super(message, cause);
        this.args =  new Object[0];
    }

    public CoreException(String message, Object... args) {
        super(message);
        this.args = args;
    }

    public CoreException(String message, Throwable cause, Object... args) {
        super(message, cause);
        this.args = args;
    }
}
