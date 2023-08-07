package az.atl.msauth.exceptions;

public class PhoneNumberIsAlreadyBusyException extends RuntimeException {

    public PhoneNumberIsAlreadyBusyException() {
    }

    public PhoneNumberIsAlreadyBusyException(String message) {
        super(message);
    }
}
