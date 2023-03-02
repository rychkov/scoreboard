package uedp.scoreboard.exceptions;

/**
 * Base class for validation exception.
 */
public class ValidationException extends RuntimeException {
    public ValidationException(String message) {
        super(message);
    }
}
