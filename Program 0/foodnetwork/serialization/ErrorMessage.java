package foodnetwork.serialization;

/**
 * Represents an error message and provides serialization/deserialization
 * @author john
 *
 */
public class ErrorMessage extends FoodMessage {

	private String	errorMessage;
	
	/**
	 * Constructs error message using set values
	 * @param messageTimestamp message timestamp
	 * @param errorMessage error message
	 * @throws FoodNetworkException if validation fails
	 */
	public ErrorMessage(long messageTimestamp, String errorMessage)
							throws FoodNetworkException {
		if (messageTimestamp < 0){
			throw new FoodNetworkException("Bad meesagetimestamp");
		}
		if (errorMessage == null){
			throw new FoodNetworkException("Null error message");
		}
		this.messageTimestamp = messageTimestamp;
		this.errorMessage = errorMessage;
	}
	
	@Override
	public String toString(){
		return "Message Timestamp: " + messageTimestamp + " Error Message" + errorMessage;
	}
	
	/**
	 * Return error message
	 * @return error message
	 */
	public final String getErrorMessage(){
		return errorMessage;
	}
	
	/**
	 * Set error message
	 * @param errorMessage new error messgae
	 * @throws FoodNetworkException if null error message
	 */
	public final void setErrorMessage(String errorMessage)
						throws FoodNetworkException {
		if (errorMessage == null){
			throw new FoodNetworkException("null error message");
		}
		this.errorMessage = errorMessage;
	}
		
	@Override
	public int hashCode(){
		int hashCode = (int) Math.sqrt(messageTimestamp);
		hashCode *= errorMessage.charAt(0);
		hashCode = (int) Math.sqrt(hashCode);
		return hashCode;
	}
	
	@Override
	public boolean equals(Object obj){
		 if (null == this || null == obj){
				return false;
			}
		return this.toString().equals(obj.toString());
	 }
	
	@Override
	public void getEncode(MessageOutput out) throws FoodNetworkException{
		out.write(getRequest() + SPACE + errorMessage.length() + SPACE + errorMessage + NEWLINE);
	}
	
	@Override
	public String getRequest() {
		return ERROR_REQUEST;
	}	

}
