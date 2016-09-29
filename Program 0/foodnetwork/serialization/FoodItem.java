/**********************************
 * Author:		John Daniel
 * Assignment:	Program 2
 * Class:		CSI 4321
 **********************************/
package foodnetwork.serialization;

import java.io.EOFException;

/**
 * @author John
 *
 */
public class FoodItem {
	
	private String		name;
	private MealType	mealType;
	private long		calories;
	private String		fat;
	
	/**
	 * Constructs food item with set values
	 * @param name - name of food item
	 * @param mealType - type of meal
	 * @param calories - number of calories in food item
	 * @param fat - grams of fat in food item
	 * @throws FoodNetworkException if deserialization or validation failure
	 */
	public FoodItem(String name,
	                MealType mealType,
	                long calories,
	                String fat)
	         throws FoodNetworkException {
		if (null == name || name == ""){
			throw new FoodNetworkException("Bad name");
		}
		if (null == mealType){
			throw new FoodNetworkException("Bad mealType");
		}
		if (calories < 0){
			throw new FoodNetworkException("Bad calories");
		}
		if (null == fat || fat == ""){
			throw new FoodNetworkException("Bad fat");
		}
		try{
			if (Double.parseDouble(fat) < 0){
				throw new FoodNetworkException("Bad fat");
			}
		} catch (NumberFormatException e){
			throw new FoodNetworkException("Bad fat");
		}
		this.name = name;
		this.mealType = mealType;
		this.calories = calories;
		this.fat = fat;		
	}

	/**
	 * Constructs food item using deserialization
	 * @param in - deserialization input source
	 * @throws FoodNetworkException if deserialization or validation failure
	 * @throws java.io.EOFException if premature end of stream
	 */
	public FoodItem(MessageInput in)
	         throws FoodNetworkException,
	                EOFException{
		
		String newName = in.getName();
		MealType newMealType = in.getMealType();
		long newCalories = in.getCalories();
		String newFat = in.getFat();
		if (null == newName || newName == ""){
			throw new FoodNetworkException("Bad name");
		}
		if (null == newMealType){
			throw new FoodNetworkException("Bad mealType");
		}
		if (newCalories < 0){
			throw new FoodNetworkException("Bad calories");
		}
		if (null == newFat || newFat == ""){
			throw new FoodNetworkException("Bad fat");
		}
		try{
			if (Double.parseDouble(newFat) < 0){
				throw new FoodNetworkException("Bad fat");
			}
		} catch (NumberFormatException e){
			throw new FoodNetworkException("Bad fat");
		}
		name = newName;
		mealType = newMealType;
		calories = newCalories;
		fat = newFat;
	}
	
	/**
	 * Serializes food item
	 * @param out - serializaion output destination
	 * @throws FoodNetworkException if serialization output fails
	 */
	public void encode(MessageOutput out)
	            throws FoodNetworkException{
		if (out == null){
			throw new FoodNetworkException("Bad MessageOutput");
		}
		String serialized = name.length() + " " + name;
		serialized+= mealType.getMealTypeCode();
		serialized+= calories + " ";
		serialized+= fat + " ";
		out.messageOut = serialized;
		out.write(serialized);
	}

	/**
	 * Overrides:
	 * toString in class java.lang.Object
	 */
	public String toString(){
		return name + " with " + calories + " calories and " + fat + "g of fat";		
	}

	/**
	 * returns name
	 * @return name
	 */
	public final String getName(){
		return name;
	}

	/**
	 * Sets name
	 * @param name
	 * @throws FoodNetworkException
	 */
	public final void setName(String name)
	                   throws FoodNetworkException {
		if (null == name){
			throw new FoodNetworkException("Bad name");
		}
		this.name = name;
	}

	/**
	 * returns meal type
	 * @return mealType
	 */
	public final MealType getMealType(){
		return mealType;
	}

	/**
	 * Sets meal type
	 * @param mealType
	 * @throws FoodNetworkException
	 */
	public final void setMealType(MealType mealType)
	                       throws FoodNetworkException{
		if (null == mealType){
			throw new FoodNetworkException("Bad mealType");
		}
		this.mealType = mealType;
	}

	/**
	 * returns calories
	 * @return calories
	 */
	public final long getCalories(){
		return calories;
	}

	/**
	 * Sets calories
	 * @param calories
	 * @throws FoodNetworkException
	 */
	public final void setCalories(long calories)
	                       throws FoodNetworkException{
		if (calories < 0){
			throw new FoodNetworkException("Bad Calories");
		}
		this.calories = calories;
	}

	/**
	 * returns fat
	 * @return fat
	 */
	public final String getFat(){
		return fat;
	}

	/**
	 * Set fat
	 * @param fat
	 * @throws FoodNetworkException
	 */
	public final void setFat(String fat)
	                  throws FoodNetworkException {
		if (null == fat){
			throw new FoodNetworkException("Bad fat");
		}
		this.fat = fat;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (calories ^ (calories >>> 32));
		result = prime * result + ((fat == null) ? 0 : fat.hashCode());
		result = prime * result + ((mealType == null) ? 0 : mealType.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof FoodItem)) {
			return false;
		}
		FoodItem other = (FoodItem) obj;
		if (calories != other.calories) {
			return false;
		}
		if (fat == null) {
			if (other.fat != null) {
				return false;
			}
		} else if (!fat.equals(other.fat)) {
			return false;
		}
		if (mealType != other.mealType) {
			return false;
		}
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		return true;
	}
}
