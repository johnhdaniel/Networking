/**********************************
 * Author:		John Daniel
 * Assignment:	Program 3
 * Class:		CSI 4321
 **********************************/
package tefub.serialization;

//import java.lang.IllegalArgumentException;

public class Error extends TeFubMessage {

	protected String errorMessage;

	/**
	 * Constructs from given values
	 * @param msgId message ID
	 * @param errorMessage error message
	 * @throws IllegalArgumentException if validation fails (see individual setters)
	 */
	public Error(int msgId, String errorMessage)
     throws IllegalArgumentException {
		super(msgId);
		setErrorMessage(errorMessage);
	}

	/**
	 * Set the error message
	 * @param errorMessage error message
	 * @throws IllegalArgumentException if validation fails (e.g. null or non-ASCII)
	 */
	public void setErrorMessage(String errorMessage) throws IllegalArgumentException{
		if (null == errorMessage){
			throw new IllegalArgumentException("Illegal Argument: null error message");
		}
		if (!errorMessage.matches("^\\p{ASCII}*$")){
			throw new IllegalArgumentException("Illegal Argument: non-ASCII error message");
		}
		this.errorMessage = errorMessage;
	}
	
	@Override
	public int getCode(){
		return ERROR_CODE;
	}
	
	/**
	 * Get the error message
	 * @return error message
	 */
	public String getErrorMessage(){
		return errorMessage;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((errorMessage == null) ? 0 : errorMessage.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (!(obj instanceof Error)) {
			return false;
		}
		Error other = (Error) obj;
		if (errorMessage == null) {
			if (other.errorMessage != null) {
				return false;
			}
		} else if (!errorMessage.equals(other.errorMessage)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return super.toString() + " Error [errorMessage=" + errorMessage + "]";
	}
}
