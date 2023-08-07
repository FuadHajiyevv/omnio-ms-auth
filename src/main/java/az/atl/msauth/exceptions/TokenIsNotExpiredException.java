package az.atl.msauth.exceptions;

public class TokenIsNotExpiredException extends RuntimeException {
    public TokenIsNotExpiredException() {
        super();
    }

    public TokenIsNotExpiredException(String message) {
        super(message);
    }
}
