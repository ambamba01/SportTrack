package exceptions;


public class FXMLException extends Exception {

    /**
     * Creates a new instance of FXMLException without detail
     * message.
     */
    public FXMLException() {
        super();
    }

    /**
     * Constructs an instance of FXMLException with the specified
     * detail message.
     *
     * @param msg message of the exception.
     */
    public FXMLException(String msg) {
        super(msg);
    }

    /**
     * Constructs an instance of FXMLException and wrapped the
     * source exception.
     *
     * @param exception wrapped exception.
     */
    public FXMLException(Exception exception) {
        super(exception);
    }
}
