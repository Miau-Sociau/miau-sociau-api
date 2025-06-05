package br.com.miausocial.shared;

/**
 * Record representing an error response from the API.
 *
 * @param message The error message describing what went wrong
 * @param status The HTTP status code associated with the error
 * @param timestamp The time when the error occurred
 * @param path The request path that caused the error
 */
public record ErrorResponse(
    String message,
    int status,
    long timestamp,
    String path
) {
    public ErrorResponse {
        if (message == null || message.trim().isEmpty()) {
            throw new IllegalArgumentException("Error message cannot be null or empty");
        }
        if (status < 400 || status > 599) {
            throw new IllegalArgumentException("Status code must be between 400 and 599");
        }
        if (path == null || path.trim().isEmpty()) {
            throw new IllegalArgumentException("Path cannot be null or empty");
        }
    }

    public static ErrorResponse of(String message, int status, String path) {
        return new ErrorResponse(message, status, System.currentTimeMillis(), path);
    }
}
