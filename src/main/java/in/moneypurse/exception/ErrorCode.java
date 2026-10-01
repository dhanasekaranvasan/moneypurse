package in.moneypurse.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public enum ErrorCode {

	// --- 400 Bad Request / Validation ---
	INVALID_PAYLOAD("ERR_INVALID_PAYLOAD", "Request validation failed for one or more fields.", HttpStatus.BAD_REQUEST),
	MALFORMED_JSON("ERR_MALFORMED_JSON", "Malformed JSON request body or missing required payload.",
			HttpStatus.BAD_REQUEST),
	TYPE_MISMATCH("ERR_TYPE_MISMATCH", "Invalid format or type mismatch for request parameter.",
			HttpStatus.BAD_REQUEST),
	MISSING_PARAMETER("ERR_MISSING_PARAM", "Required query parameter or header is missing.", HttpStatus.BAD_REQUEST),
	CONSTRAINT_VIOLATION("ERR_CONSTRAINT_VIOLATION", "Validation constraint check failed.", HttpStatus.BAD_REQUEST),

	// --- 401 Unauthorized & 403 Forbidden ---
	UNAUTHORIZED("ERR_UNAUTHORIZED", "Full authentication is required to access this resource.",
			HttpStatus.UNAUTHORIZED),
	ACCESS_DENIED("ERR_ACCESS_DENIED", "You do not have permission to access this resource.", HttpStatus.FORBIDDEN),
	TOKEN_EXPIRED("ERR_TOKEN_EXPIRED", "Authentication token has expired.", HttpStatus.UNAUTHORIZED),

	// --- 404 Not Found & 405 Method Not Allowed ---
	RESOURCE_NOT_FOUND("ERR_NOT_FOUND", "The requested resource could not be found.", HttpStatus.NOT_FOUND),
	METHOD_NOT_ALLOWED("ERR_METHOD_NOT_ALLOWED", "HTTP method is not supported for this endpoint.",
			HttpStatus.METHOD_NOT_ALLOWED),
	NO_HANDLER_FOUND("ERR_NO_HANDLER", "No handler found for requested URL path.", HttpStatus.NOT_FOUND),

	// --- 409 Conflict & 415 Unsupported Media Type ---
	RESOURCE_ALREADY_EXISTS("ERR_CONFLICT", "Resource already exists or state conflict occurred.", HttpStatus.CONFLICT),
	DATA_INTEGRITY_VIOLATION("ERR_DATA_INTEGRITY", "Database constraint or foreign key violation.",
			HttpStatus.CONFLICT),
	UNSUPPORTED_MEDIA_TYPE("ERR_UNSUPPORTED_MEDIA", "Content type is not supported.",
			HttpStatus.UNSUPPORTED_MEDIA_TYPE),
	OPTIMISTIC_LOCKING_FAILURE("ERR_CONFLICT_TRANSACTION", "Resource state was updated by another transaction. Please retry.",
			HttpStatus.CONFLICT),

	// --- 429 Too Many Requests ---
	RATE_LIMIT_EXCEEDED("ERR_RATE_LIMIT", "Rate limit exceeded. Too many requests.", HttpStatus.TOO_MANY_REQUESTS),

	// --- 500 Internal Error & 503 Service Unavailable ---
	INTERNAL_SERVER_ERROR("ERR_INTERNAL_SERVER", "An unexpected internal server error occurred.",
			HttpStatus.INTERNAL_SERVER_ERROR),
	DATABASE_ERROR("ERR_DATABASE_FAILURE", "Database operation failed or connection timed out.",
			HttpStatus.INTERNAL_SERVER_ERROR),
	SERVICE_UNAVAILABLE("ERR_SERVICE_UNAVAILABLE", "Downstream service is currently unavailable.",
			HttpStatus.SERVICE_UNAVAILABLE);

	private final String code;
	private final String defaultMessage;
	private final HttpStatus httpStatus;

	ErrorCode(String code, String defaultMessage, HttpStatus httpStatus) {
		this.code = code;
		this.defaultMessage = defaultMessage;
		this.httpStatus = httpStatus;
	}

}