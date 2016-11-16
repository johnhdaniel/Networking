/**********************************
 * Author:		John Daniel
 * Assignment:	Program 3
 * Class:		CSI 4321
 **********************************/
package foodnetwork.serialization;

public class FoodNetworkException extends Exception {
	
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs food network exception
	 * @param message - exception message
	 * @param cause - exception cause
	 * @throws FoodNetworkException if serialization/deserializtion fails
	 */
	public FoodNetworkException(String message,
            				Throwable cause) throws FoodNetworkException{
		super(message, cause);
	}

	/**
	 * Constructs food network exception with null cause
	 * @param message exception message
	 * @throws FoodNetworkException if serialization/deserializtion fails
	 */
	public FoodNetworkException(String message) throws FoodNetworkException{
		super(message);
	}
}
