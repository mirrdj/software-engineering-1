package exceptions;

import java.io.UncheckedIOException;

public class IncorrectNumberOfFieldsException extends RuntimeException {
    public IncorrectNumberOfFieldsException(String message) {
        super(message);
    }
}
