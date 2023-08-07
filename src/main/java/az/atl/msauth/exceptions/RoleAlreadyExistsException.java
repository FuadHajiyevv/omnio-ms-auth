package az.atl.msauth.exceptions;

public class RoleAlreadyExistsException extends RuntimeException{
    public RoleAlreadyExistsException() {
        super();
    }

    public RoleAlreadyExistsException(String message) {
        super(message);
    }
}
