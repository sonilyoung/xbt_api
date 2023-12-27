package egovframework.com.global.http.exception;

import egovframework.com.global.http.BaseResponseCode;

public class AbstractBaseException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7909952777336435788L;
	protected BaseResponseCode responseCode;
	protected String message;
	protected Object[] args;

	public AbstractBaseException() {

	}

	public AbstractBaseException(BaseResponseCode responseCode) {
		this.responseCode = responseCode;
	}

	public BaseResponseCode getResponseCode() {
		return responseCode;
	}

	public Object[] getArgs() {
		return args;
	}
	

	public String getMessage() {
		return message;
	}

}
