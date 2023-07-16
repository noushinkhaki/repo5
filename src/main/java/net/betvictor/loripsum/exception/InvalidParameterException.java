package net.betvictor.loripsum.exception;

public class InvalidParameterException extends RuntimeException {

    private final String message;

    public InvalidParameterException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
