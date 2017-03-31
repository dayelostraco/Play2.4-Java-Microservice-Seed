package utils;

public class ErrorHandler {

    public static final String BAD_REQUEST = "The request has no JSON body or the JSON is invalid.";
    public static final String NOT_FOUND = "No matching objects found.";
    public static final String UNAUTHORIZED = "Valid authorization credentials were not supplied.";
    public static final String INTERNAL_SERVER_ERROR = "An internal error occurred while communicating with the database.";
    public static final String ALREADY_EXISTS = "An object with this id already exists.";
    public static final String TOKEN_REFRESH_ERROR = "Token validation error.";
    public static final String TOKEN_INVALID_ERROR = "The given bearer token is invalid.";
    public static final String UPLOAD_FAILURE = "File upload failed, the failure has been logged.";

    private ErrorHandler() {
    }

    public ErrorHandler(String message) {
        this.errorMessage = message;
    }

    private String errorMessage;

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
