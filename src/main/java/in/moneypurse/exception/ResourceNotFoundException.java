package in.moneypurse.exception;

public class ResourceNotFoundException extends BusinessException {
	
	private static final long serialVersionUID = 1L;

	public ResourceNotFoundException(String resource, String fieldName, Object fieldValue) {
		super(ErrorCode.RESOURCE_NOT_FOUND, String.format("%s not found with %s: '%s'", resource, fieldName, fieldValue));
	}

}
