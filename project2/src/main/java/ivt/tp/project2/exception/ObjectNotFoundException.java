package ivt.tp.project2.exception;

public class ObjectNotFoundException extends RuntimeException {
    public ObjectNotFoundException(String message) {
        super(message);
    }

    public ObjectNotFoundException(String message, String username) {
        super(message + username);
    }
}
