package in.moneypurse.dto;

import java.time.Instant;
import java.util.UUID;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ApiResponse<T>(int status, String message, Instant timeStamp, String requestId, T data, PageMeta meta) {

	public static <T> ApiResponse<T> of(HttpStatus status, String message, T data, PageMeta meta) {
		return new ApiResponse<>(status.value(), message, Instant.now(), UUID.randomUUID().toString(), data, meta);
	}

	public static <T> ApiResponse<T> of(HttpStatus status, String message, T data) {
		return of(status, message, data, null);
	}

	public static <T> ApiResponse<T> of(HttpStatus status, String message) {
		return of(status, message, null);
	}

	public static <T> ApiResponse<T> of(String message) {
		return of(HttpStatus.OK, message);
	}

	public static <T> ApiResponse<T> of(String message, T data, PageMeta meta) {
		return of(HttpStatus.OK, message, data, meta);
	}

	public record PageMeta(int page, int size, long totalElements, int totalPages, boolean isFirst, boolean isLast,
			boolean hasNext, boolean hasPrev) {
	}

}