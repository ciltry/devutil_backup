/**
 * 
 */
package dev.sidney.devutil.domain.exception;

/**
 * 
 * @author 杨丰光 2015年8月28日15:52:15
 *
 */
public class BusinessException extends Exception {

	/**
	 * uid
	 */
	private static final long serialVersionUID = -2091515486801078116L;
	
	private ExceptionCodeEnum exceptionCode;

	public BusinessException() {
		super();
	}
	
	public BusinessException(ExceptionCodeEnum exceptionCode, Throwable cause, String message) {
		super(message, cause);
		this.exceptionCode = exceptionCode;
	}

	public BusinessException(ExceptionCodeEnum exceptionCode, Throwable cause) {
		super(exceptionCode.getDesc(), cause);
		this.exceptionCode = exceptionCode;
	}
	
	public BusinessException(ExceptionCodeEnum exceptionCode, String message) {
		super(message);
		this.exceptionCode = exceptionCode;
	}

	public BusinessException(ExceptionCodeEnum exceptionCode) {
		super(exceptionCode.getDesc());
		this.exceptionCode = exceptionCode;
	}
	
	
	
	


	public ExceptionCodeEnum getExceptionCode() {
		return exceptionCode;
	}

	public void setExceptionCode(ExceptionCodeEnum exceptionCode) {
		this.exceptionCode = exceptionCode;
	}
	
	
}
