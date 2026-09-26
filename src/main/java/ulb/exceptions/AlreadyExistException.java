package ulb.exceptions;

public class AlreadyExistException extends Exception {

    /**
     * Creates a new instance of RepositoryException without detail
     * message.
     */
    public AlreadyExistException() {
        super();
    }

    /**
     * Constructs an instance of RepositoryException with the specified
     * detail message.
     *
     * @param msg message of the exception.
     */
    public AlreadyExistException(String msg) {
        super(msg);
    }

    /**
     * Constructs an instance of RepositoryException and wrapped the
     * source exception.
     *
     * @param exception wrapped exception.
     */
    public AlreadyExistException(Exception exception) {
        super(exception);
    }
}
