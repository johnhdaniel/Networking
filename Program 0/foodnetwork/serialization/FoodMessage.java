/**
 * 
 */
package foodnetwork.serialization;

/**
 * @author john
 *
 */
public abstract class FoodMessage {
	
	protected long messageTimestamp;
	protected static String request;
	
	/**
	 * Serializes message to MessageOutput
	 * @param out serialization output destination
	 * @throws FoodNetworkException if serialization output fails
	 */
	public void encode(MessageOutput out) throws FoodNetworkException{
		String encoder = "FN1.0 " + messageTimestamp;
		encoder+= " " + getRequest() + "\n";
		out.write(encoder);
	}

	/**
	 * Deserializes message from byte source
	 * @param in deserialization input source
	 * @return a specific FoodMessage resulting from deserialization
	 * @throws FoodNetworkException if deserialization or validation failure
	 * @throws java.io.EOFException if premature end of stream
	 */
	 public static FoodMessage decode (MessageInput in) 
	 						   throws FoodNetworkException,
	 						   		  java.io.EOFException {
		 in.getProtocol();
		 long initMessageTimestamp = in.getTimestamp();
		 request = in.getRequest();
		 if (request.equals("ADD")){
			 AddFood result = new AddFood(initMessageTimestamp, new FoodItem(in));
			 return result;
		 } else if (request.equals("GET")){
			 GetFood result = new GetFood(initMessageTimestamp);
			 return result;
		 } else if (request.equals("LIST")){
			 long modifiedTimestamp = in.getTimestamp();
			 FoodList result = new FoodList(initMessageTimestamp, modifiedTimestamp);
			 int numInList = (int)in.getNumber();
			 for (int i = 0; i < numInList; i++){
				 result.addFoodItem(new FoodItem(in));
			 }
			 return result;
		 } else if (request.equals("ERROR")){
			 String errorMessage = in.getErrorMessage();
			 ErrorMessage result = new ErrorMessage(initMessageTimestamp, errorMessage);
			 return result;
		 } else {
			 throw new FoodNetworkException("Bad Request");
		 }
		
	 }
	 
	 @Override
	 public String toString(){
		return null;
		 
	 }
	 
	 /**
	  * Returns message timestamp
	  * @return message timestamp
	  */
	 public final long getMessageTimestamp(){
		return messageTimestamp;
		 
	 }
	 
	 /**
	  * Sets message timestamp
	  * @param messageTimestamp message timestamp
	  * @throws FoodNetworkException if validation fails
	  */
	 public final void setMessageTimestamp(long messageTimestamp)
	 					throws FoodNetworkException{
		 this.messageTimestamp = messageTimestamp;
	 }
	 
	 /**
	  * Returns message request (e.g., ADD)
	  * @return message request
	  */
	 public abstract String getRequest();
	 
	 @Override
	 public int hashCode(){
		 return (int) Math.sqrt((double)messageTimestamp);
	 }
	 
	 @Override
	 public boolean equals(Object obj){
		 if (null == this || null == obj){
				return false;
			}
		return this.toString().equals(obj.toString());
	 }
	 
}
