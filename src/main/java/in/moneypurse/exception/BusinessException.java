package in.moneypurse.exception;

import lombok.Getter;

@Getter
public abstract class BusinessException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private final ErrorCode errorCode;
	private final Object[] args;
	
	protected BusinessException(ErrorCode errorCode, String message, Object... args) {
		super(message != null ? message : errorCode.getDefaultMessage());
		this.errorCode = errorCode;
		this.args = args;
	}

	protected BusinessException(ErrorCode errorCode, Throwable cause, String message, Object... args) {
		super(message != null ? message : errorCode.getDefaultMessage(), cause);
		this.errorCode = errorCode;
		this.args = args;
	}

}
