package az.atl.msauth.exceptions;

public class UniqueConstraintException extends RuntimeException {
    public UniqueConstraintException() {
    }

    public UniqueConstraintException(String message) {
        super(message);
    }
}
