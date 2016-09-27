package foodnetwork.serialization;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a FoodList message and provides serialization/deserialization 
 * Note: The insertion order of food items MUST be preserved by 
 * getFoodItemList() and serialization. That is, if I added FoodItemA and then 
 * FoodItemB, then getFoodItemList() returns {FoodItemA, FoodItemB} and the 
 * serialization encoded FoodItemA before FoodItem B.
 * @author john
 *
 */
public class FoodList extends FoodMessage {

	private long modifiedTimestamp;
	private List<FoodItem> foodItemList = new ArrayList<FoodItem>();
	
	/**
	 * Constructs FoodList using set values
	 * @param messageTimestamp
	 * @param modifiedTimestamp
	 * @throws FoodNetworkException
	 */
	public FoodList(long messageTimestamp, long modifiedTimestamp)
						throws FoodNetworkException{
		if (messageTimestamp < 0){
			throw new FoodNetworkException("Bad meesagetimestamp");
		}
		if (modifiedTimestamp < 0){
			throw new FoodNetworkException("Bad modified timestamp");
		}
		this.messageTimestamp = messageTimestamp;
		this.modifiedTimestamp = modifiedTimestamp;
	}

	@Override
	public String toString(){
		return "Message Timestamp: " + messageTimestamp + " Modified Timestamp: " + modifiedTimestamp;
	}
	
	/**
	 * Returns modified timestamp
	 * @return modified timestamp
	 */
	public long getModifiedTimestamp(){
		return modifiedTimestamp;
	}
	
	/**
	 * Set modified timestamp
	 * @param modifiedTimestamp modification timestamp
	 * @throws FoodNetworkException if validation fails
	 */
	public final void setModifiedTimestamp(long modifiedTimestamp)
							throws FoodNetworkException{
		if (modifiedTimestamp < 0){
			throw new FoodNetworkException("Bad modified timestamp");
		}
		this.modifiedTimestamp = modifiedTimestamp;
	}
	
	/**
	 * Returns list of food items
	 * @return food items
	 */
	public List<FoodItem> getFoodItemList(){
		return foodItemList;
	}
	
	/**
	 * Adds food item
	 * @param foodItem new food item to add
	 */
	public void addFoodItem(FoodItem foodItem){
		foodItemList.add(foodItem);
	}
	
	@Override
	public int hashCode(){
		if (foodItemList.isEmpty()){
			return 0;
		}
		return foodItemList.get(0).hashCode();
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
		String encodeString = getRequest() + " ";
		encodeString+= modifiedTimestamp + " ";
		encodeString+= foodItemList.size() + " ";
		out.write(encodeString);;
		for(int i = 0; i < foodItemList.size(); i++){
			foodItemList.get(i).encode(out);
		}
		out.write(Character.toString(NEWLINE));
	}
	
	@Override
	public String getRequest() {
		String requestString = LIST_REQUEST;
		
		return requestString;
	}
	
}
