package in.moneypurse.exception;

import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.jspecify.annotations.Nullable;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import in.moneypurse.dto.ApiErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<ApiErrorResponse> handleBusinessException(BusinessException ex, HttpServletRequest request) {
		ErrorCode errorCode = ex.getErrorCode();
		ApiErrorResponse response = ApiErrorResponse.of(errorCode, ex.getLocalizedMessage(),
				UUID.randomUUID().toString(), request.getRequestURI());
		log.warn("Business Exception [{}] at path '{}': {}", ex.getErrorCode(), request.getRequestURI(),
				ex.getMessage());
		return ResponseEntity.status(errorCode.getHttpStatus()).body(response);
	}

	@ExceptionHandler(NoResourceFoundException.class)
	public ResponseEntity<ApiErrorResponse> handleNoResourceFound(NoResourceFoundException ex,
			HttpServletRequest request) {
		log.warn("Resource/Route not found at path '{}': {}", request.getRequestURI(), ex.getMessage());
		String detail = String.format("The requested path '%s' was not found on this server.", request.getRequestURI());
		ApiErrorResponse response = ApiErrorResponse.of(ErrorCode.RESOURCE_NOT_FOUND, detail,
				UUID.randomUUID().toString(), request.getRequestURI());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiErrorResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpServletRequest request) {
		log.warn("DTO Validation failed at path '{}': {} error(s)", request.getRequestURI(),
				ex.getBindingResult().getFieldErrors());
		Map<String, @Nullable String> errors = ex.getFieldErrors().stream().collect(Collectors.toMap(FieldError::getField,
				FieldError::getDefaultMessage, (msg1, msg2) -> msg1 + ", " + msg2));
		ApiErrorResponse response = ApiErrorResponse.of(ErrorCode.INVALID_PAYLOAD,
				ErrorCode.INVALID_PAYLOAD.getDefaultMessage(), UUID.randomUUID().toString(), request.getRequestURI(),
				errors);
		return ResponseEntity.badRequest().body(response);
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ApiErrorResponse> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpServletRequest request) {
		log.warn("Malformed JSON request at path '{}': {}", request.getRequestURI(), ex.getMessage());
		ApiErrorResponse response = ApiErrorResponse.of(ErrorCode.MALFORMED_JSON, UUID.randomUUID().toString(),
				request.getRequestURI());
		return ResponseEntity.badRequest().body(response);
	}

	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<ApiErrorResponse> handleMethodNotSupported(HttpRequestMethodNotSupportedException ex,
			HttpServletRequest request) {
		log.warn("Method '{}' not allowed at path '{}'", ex.getMethod(), request.getRequestURI());

		String detail = String.format("HTTP method '%s' is not supported for this endpoint.", ex.getMethod());
		ApiErrorResponse response = ApiErrorResponse.of(ErrorCode.METHOD_NOT_ALLOWED, detail,
				UUID.randomUUID().toString(), request.getRequestURI());

		/*
		 * if (ex.getSupportedHttpMethods() != null) {
		 * problem.setProperty("supportedMethods", ex.getSupportedHttpMethods()); }
		 */
		return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(response);
	}

	@ExceptionHandler(OptimisticLockingFailureException.class)
	public ResponseEntity<ApiErrorResponse> handleOptimisticLocking(OptimisticLockingFailureException ex,
			HttpServletRequest request) {
		log.warn("Optimistic locking failure at path '{}'", request.getRequestURI());
		ApiErrorResponse response = ApiErrorResponse.of(ErrorCode.OPTIMISTIC_LOCKING_FAILURE, ex.getLocalizedMessage(),
				UUID.randomUUID().toString(), request.getRequestURI());
		return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiErrorResponse> handleGenericException(Exception ex, HttpServletRequest request) {
		log.error("Unhandled exception trapped on path {}: ", request.getRequestURI(), ex);
		ApiErrorResponse response = ApiErrorResponse.of(ErrorCode.INTERNAL_SERVER_ERROR, ex.getLocalizedMessage(),
				UUID.randomUUID().toString(), request.getRequestURI());
		return ResponseEntity.internalServerError().body(response);
	}

}
