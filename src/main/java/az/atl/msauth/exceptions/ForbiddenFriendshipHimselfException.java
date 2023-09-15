package az.atl.msauth.exceptions;

public class ForbiddenFriendshipHimselfException extends RuntimeException {
    public ForbiddenFriendshipHimselfException() {
    }

    public ForbiddenFriendshipHimselfException(String message) {
        super(message);
    }
}
