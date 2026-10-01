package in.moneypurse.dto;

import java.time.Instant;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;

import in.moneypurse.exception.ErrorCode;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ApiErrorResponse(int status, String errorCode, String title, String detail, String instance,
		String requestId, Instant timeStamp, Map<String, Object> properties, Map<String, String> fieldErrors) {

	public static ApiErrorResponse of(ErrorCode code, String detail, String requestId, String instance,
			Map<String, String> fieldErrors) {
		return new ApiErrorResponse(code.getHttpStatus().value(), code.getCode(),
				code.getHttpStatus().getReasonPhrase(), detail, instance, requestId, Instant.now(), null, fieldErrors);
	}

	public static ApiErrorResponse of(ErrorCode code, String detail, String requestId, String instance) {
		return new ApiErrorResponse(code.getHttpStatus().value(), code.getCode(),
				code.getHttpStatus().getReasonPhrase(), detail, instance, requestId, Instant.now(), null, null);
	}

	public static ApiErrorResponse of(ErrorCode code, String requestId, String instance) {
		return new ApiErrorResponse(code.getHttpStatus().value(), code.getCode(),
				code.getHttpStatus().getReasonPhrase(), code.getDefaultMessage(), instance, requestId, Instant.now(),
				null, null);
	}

}
