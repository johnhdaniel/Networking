/**********************************
 * Author:		John Daniel
 * Assignment:	Program 0
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
	                java.lang.String fat)
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
		String result = "Name: " + name;
		result += " MealType: " + mealType.toString();
		result += " Calories: " + calories;
		result += " Fat: " + fat;
		return result;		
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

	/**
	 * Overrides:
	 * hashcode in class java.lang.Object
	 */
	public int hashCode(){
		int hash;
		hash = (int) calories;
		hash*= Double.parseDouble(fat);
		hash*= 7;
		hash = (int) Math.sqrt(hash);
		return hash;
	}

	/**
	 * Overrides:
	 * equals in class java.lang.Object
	 */
	public boolean equals(Object obj) {
		if (null == this || null == obj){
			return false;
		}
		return this.toString().equals(obj.toString());
	}
}
