package foodnetwork.serialization;

/**
 * Represents an AddFood and provides serialization/deserialization
 *
 */
public class AddFood extends FoodMessage {
	
	public FoodItem	foodItem;

	/**
	 * Constructs new AddFood using set values
	 * @param messageTimeStamp message timestamp
	 * @param foodItem new fooditem
	 * @throws FoodNetworkException if validation fails
	 */
	public AddFood (long messageTimestamp, FoodItem foodItem)
					throws FoodNetworkException{
		if (messageTimestamp < 0){
			throw new FoodNetworkException("Bad meesagetimestamp");
		}
		if (foodItem == null){
			throw new FoodNetworkException("Null fooditem");
		}
		this.messageTimestamp = messageTimestamp;
		this.foodItem = foodItem;
	}

	@Override
	public String toString(){
		return "Message Timestamp: " + messageTimestamp + " Food Item: " + foodItem;
	}
	
	/**
	 * Returns food item
	 * @return food item
	 */
	public final FoodItem getFoodItem(){
		return this.foodItem;
	}
	
	/**
	 * Sets food item
	 * @param foodItem new foodItem
	 * @throws FoodNetworkException if null food item
	 */
	public final void setFoodItem(FoodItem foodItem)
						throws FoodNetworkException {
		if (foodItem == null){
			throw new FoodNetworkException("Null fooditem");
		}
		this.foodItem = foodItem;
	}
	
	@Override
	public void getEncode(MessageOutput out) throws FoodNetworkException{
		
		out.write(getRequest() + SPACE);
		foodItem.encode(out);
		out.write(Character.toString(NEWLINE));
	}
	
	@Override
	public String getRequest() {
		String requestString = ADD_REQUEST;
		
		
		return requestString;
	}
		
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((foodItem == null) ? 0 : foodItem.hashCode());
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
		if (!(obj instanceof AddFood)) {
			return false;
		}
		AddFood other = (AddFood) obj;
		if (foodItem == null) {
			if (other.foodItem != null) {
				return false;
			}
		} else if (!foodItem.equals(other.foodItem)) {
			return false;
		}
		return true;
	}
	
	
}
