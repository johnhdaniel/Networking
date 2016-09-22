package foodnetwork.serialization;

/**
 * Represents an AddFoor and provides serialization/deserialization
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
		this.foodItem = foodItem;
	}
	
	@Override
	public String getRequest() {
		String requestString = "ADD ";
		requestString+= foodItem.getName().length() + " ";
		requestString+= foodItem.getName();
		requestString+= foodItem.getMealType().getMealTypeCode();
		requestString+= foodItem.getCalories() + " ";
		requestString+= foodItem.getFat() + " ";
		
		return requestString;
	}
		
	@Override
	public int hashCode(){
		int hashCode;
		hashCode = (int) (foodItem.hashCode() + messageTimestamp);
		return (int)(Math.sqrt(hashCode));
	}
	
	@Override
	public boolean equals(Object obj){
		 if (null == this || null == obj){
				return false;
			}
		return this.toString().equals(obj.toString());
	 }
	
	
}
