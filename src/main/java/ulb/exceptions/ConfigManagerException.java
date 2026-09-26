package ulb.exceptions;

public class ConfigManagerException extends Exception {

    /**
     * Creates a new instance of ConfigManagerException without detail
     * message.
     */
    public ConfigManagerException() {
        super();
    }

    /**
     * Constructs an instance of ConfigManagerException with the specified
     * detail message.
     *
     * @param msg message of the exception.
     */
    public ConfigManagerException(String msg) {
        super(msg);
    }

    /**
     * Constructs an instance of ConfigManagerException and wrapped the
     * source exception.
     *
     * @param exception wrapped exception.
     */
    public ConfigManagerException(Exception exception) {
        super(exception);
    }
}