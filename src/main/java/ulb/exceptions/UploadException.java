package ulb.exceptions;

public class UploadException extends Exception {

    /**
     * Creates a new instance of RepositoryException without detail
     * message.
     */
    public UploadException() {
        super();
    }

    /**
     * Constructs an instance of RepositoryException with the specified
     * detail message.
     *
     * @param msg message of the exception.
     */
    public UploadException(String msg) {
        super(msg);
    }

    /**
     * Constructs an instance of RepositoryException and wrapped the
     * source exception.
     *
     * @param exception wrapped exception.
     */
    public UploadException(Exception exception) {
        super(exception);
    }
}
