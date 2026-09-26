package exceptions;

public class LogManagerException extends Exception {

    /**
     * Creates a new instance of ConfigManagerException without detail
     * message.
     */
    public LogManagerException() {
        super();
    }

    /**
     * Constructs an instance of ConfigManagerException with the specified
     * detail message.
     *
     * @param msg message of the exception.
     */
    public LogManagerException(String msg) {
        super(msg);
    }

    /**
     * Constructs an instance of ConfigManagerException and wrapped the
     * source exception.
     *
     * @param exception wrapped exception.
     */
    public LogManagerException(Exception exception) {
        super(exception);
    }
}