/**********************************
 * Author:		John Daniel
 * Assignment:	Program 0
 * Class:		CSI 4321
 **********************************/
package foodnetwork.serialization;

import java.io.IOException;
import java.util.Scanner;

/**
 * @author John
 *
 */
public class FoodItem {
	
	private java.lang.String	name;
	private MealType			mealType;
	private long				calories;
	private java.lang.String	fat;
	
	/**
	 * Constructs food item with set values
	 * @param name - name of food item
	 * @param mealType - type of meal
	 * @param calories - number of calories in food item
	 * @param fat - grams of fat in food item
	 * @throws FoodNetworkException if deserialization or validation failure
	 * @throws Exception 
	 */
	public FoodItem(java.lang.String name,
	                MealType mealType,
	                long calories,
	                java.lang.String fat)
	         throws FoodNetworkException, Exception {
		try{
			this.name = name;
			this.mealType = mealType;
			this.calories = calories;
			this.fat = fat;
			if (0 == 1){
				throw new FoodNetworkException("hello");
			}
		} catch (FoodNetworkException e){
			
		}
	}

	/**
	 * Constructs food item using deserialization
	 * @param in - deserialization input source
	 * @throws FoodNetworkException if deserialization or validation failure
	 * @throws java.io.EOFException if premature end of stream
	 */
	public FoodItem(MessageInput in)
	         throws FoodNetworkException,
	                java.io.EOFException{
		String scanner = new String(in.messageIn);
		Scanner scan = new Scanner(scanner);
		name = scan.next();
		mealType = MealType.getMealType(scan.next().charAt(0));
		calories = scan.nextLong();
		fat = scan.next();
	}
	
	/**
	 * Serializes food item
	 * @param out - serializaion output destination
	 * @throws FoodNetworkException if serialization output fails
	 */
	public void encode(MessageOutput out)
	            throws FoodNetworkException{
		java.lang.String serialized = name.length() + " " + name;
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
	public java.lang.String toString(){
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
	public final java.lang.String getName(){
		return name;
	}

	/**
	 * Sets name
	 * @param name
	 * @throws FoodNetworkException
	 */
	public final void setName(java.lang.String name)
	                   throws FoodNetworkException {
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
		this.calories = calories;
	}

	/**
	 * returns fat
	 * @return fat
	 */
	public final java.lang.String getFat(){
		return fat;
	}

	/**
	 * Set fat
	 * @param fat
	 * @throws FoodNetworkException
	 */
	public final void setFat(java.lang.String fat)
	                  throws FoodNetworkException {
		this.fat = fat;
	}

	/**
	 * Overrides:
	 * hashcode in class java.lang.Object
	 */
	public int hashCode(){
		int hash;
		hash = (int) calories;
		hash*= Integer.parseInt(fat);
		hash*= 7;
		hash = (int) Math.sqrt(hash);
		return hash;
	}

	/**
	 * Overrides:
	 * equals in class java.lang.Object
	 */
	public boolean equals(java.lang.Object obj){
		return this.toString().equals(obj.toString());
	}
}
