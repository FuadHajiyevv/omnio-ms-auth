package az.atl.msauth.exceptions;

public class TokenDoestExistsException extends RuntimeException {
    public TokenDoestExistsException() {
    }

    public TokenDoestExistsException(String message) {
        super(message);
    }
}
