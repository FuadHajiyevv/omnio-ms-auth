package az.atl.msauth.exceptions;

public class TokenIsNotRefreshException extends RuntimeException {
    public TokenIsNotRefreshException() {
    }

    public TokenIsNotRefreshException(String message) {
        super(message);
    }
}
