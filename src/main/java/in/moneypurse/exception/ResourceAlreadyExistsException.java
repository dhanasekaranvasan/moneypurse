package in.moneypurse.exception;

public class ResourceAlreadyExistsException extends BusinessException {
	private static final long serialVersionUID = 1L;

	public ResourceAlreadyExistsException(String resourceName, String fieldName, Object fieldValue) {
		super(ErrorCode.RESOURCE_ALREADY_EXISTS,
				String.format("%s already exists with %s: '%s'", resourceName, fieldName, fieldValue));
	}

	public ResourceAlreadyExistsException(String message) {
		super(ErrorCode.RESOURCE_ALREADY_EXISTS, message);
	}
}
