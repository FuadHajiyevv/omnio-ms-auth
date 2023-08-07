package az.atl.msauth.exceptions;

public class IncorrectLoginOrPasswordException extends RuntimeException {

    public IncorrectLoginOrPasswordException() {
    }

    public IncorrectLoginOrPasswordException(String message) {
        super(message);
    }
}
