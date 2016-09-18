/**********************************
 * Author:		John Daniel
 * Assignment:	Program 0
 * Class:		CSI 4321
 **********************************/
package foodnetwork.serialization;

public class FoodNetworkException extends Throwable {
	
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs food network exception
	 * @param message - exception message
	 * @param cause - exception cause
	 * @throws Exception 
	 */
	public FoodNetworkException(java.lang.String message,
            java.lang.Throwable cause) throws Exception{
		throw new Exception(message, cause);
	}

	/**
	 * Constructs food network exception with null cause
	 * @param message - exception message
	 * @throws Exception 
	 */
	public FoodNetworkException(java.lang.String message) throws Exception{
		throw new Exception(message);
	}
}
