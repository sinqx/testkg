package ivt.tp.project2.exception;

public class AuthException extends RuntimeException{
    public AuthException(String message) {
        super("Registration error: " + message);
    }
}

