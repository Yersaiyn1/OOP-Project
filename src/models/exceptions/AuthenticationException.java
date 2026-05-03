package models.exceptions;

/**
 * Thrown by AuthService when login fails:
 * unknown user, wrong password, or inactive account.
 */
public class AuthenticationException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public AuthenticationException(String message) {
        super(message);
    }

    public AuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }
}