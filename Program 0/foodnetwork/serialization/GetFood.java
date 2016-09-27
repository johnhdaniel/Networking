package foodnetwork.serialization;

/**
 * Represents a GetFood and provides serialization/deserialization
 * @author john
 *
 */
public class GetFood extends FoodMessage {
	
	/**
	 * Constructs GetFood using set values
	 * @param messageTimestamp message timestamp
	 * @throws FoodNetworkException if validation fails
	 */
	public GetFood(long messageTimestamp) throws FoodNetworkException {
		if (messageTimestamp < 0){
			throw new FoodNetworkException("Bad message timestamp");
		}
		this.messageTimestamp = messageTimestamp;
	}
	
	@Override
	public String toString(){
		return "Message Timestamp: " + messageTimestamp;
	}
	
	@Override
	public void getEncode(MessageOutput out) throws FoodNetworkException {
		out.write(getRequest() + SPACE + NEWLINE);
	}
	
	@Override
	public String getRequest() {
		return GET_REQUEST;
	}
	
}
