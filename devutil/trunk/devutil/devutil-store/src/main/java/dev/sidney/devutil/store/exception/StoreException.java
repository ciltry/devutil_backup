/**
 * 
 */
package dev.sidney.devutil.store.exception;

/**
 * 
 * @author 杨丰光 2015年8月27日10:19:10
 *
 */
public class StoreException extends RuntimeException {

	/**
	 * uid
	 */
	private static final long serialVersionUID = -982383320115647688L;

	public StoreException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public StoreException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public StoreException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public StoreException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}
}
