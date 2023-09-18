package az.atl.msauth.exceptions;

public class SameRoleException extends RuntimeException {
    public SameRoleException() {
    }

    public SameRoleException(String message) {
        super(message);
    }
}
