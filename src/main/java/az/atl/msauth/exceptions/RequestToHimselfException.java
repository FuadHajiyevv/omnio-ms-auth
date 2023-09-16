package az.atl.msauth.exceptions;

public class RequestToHimselfException extends RuntimeException {
    public RequestToHimselfException() {
    }

    public RequestToHimselfException(String message) {
        super(message);
    }
}
