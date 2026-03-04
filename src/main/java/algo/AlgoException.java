package algo;

/**
 * Represents a custom exception used in the Algo application.
 * This exception is thrown when an error occurs during command parsing,
 * task operations, or other application processes.
 */
public class AlgoException extends Exception {

    /**
     * Creates an AlgoException with the specified error message.
     *
     * @param message The message describing the error.
     */
    public AlgoException(String message) {
        super(message);
    }
}
