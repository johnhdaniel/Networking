/**********************************
 * Author:		John Daniel
 * Assignment:	Program 2
 * Class:		CSI 4321
 **********************************/
package foodnetwork.serialization;

public class FoodNetworkException extends Exception {
	
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs food network exception
	 * @param message - exception message
	 * @param cause - exception cause
	 */
	public FoodNetworkException(String message,
            				Throwable cause) throws FoodNetworkException{
		super(message, cause);
	}

	/**
	 * Constructs food network exception with null cause
	 * @param message - exception message
	 */
	public FoodNetworkException(String message) throws FoodNetworkException{
		super(message);
	}
}
