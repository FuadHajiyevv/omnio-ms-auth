package az.atl.msauth.exceptions;

public class IdenticalPasswordsException extends RuntimeException {
    public IdenticalPasswordsException() {
    }

    public IdenticalPasswordsException(String message) {
        super(message);
    }
}
