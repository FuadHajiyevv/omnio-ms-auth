package az.atl.msauth.exceptions;

public class EmailIsAlreadyBusyException extends RuntimeException {

    public EmailIsAlreadyBusyException() {
        super();
    }

    public EmailIsAlreadyBusyException(String message) {
        super(message);
    }
}
