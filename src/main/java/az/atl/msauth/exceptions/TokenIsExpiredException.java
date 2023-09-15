package az.atl.msauth.exceptions;

public class TokenIsExpiredException extends RuntimeException {
    public TokenIsExpiredException() {
        super();
    }

    public TokenIsExpiredException(String message) {
        super(message);
    }
}
