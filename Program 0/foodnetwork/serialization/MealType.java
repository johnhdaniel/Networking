/**********************************
 * Author:		John Daniel
 * Assignment:	Program 3
 * Class:		CSI 4321
 **********************************/
package foodnetwork.serialization;

import java.io.Serializable;

public enum MealType implements Serializable, Comparable<MealType> {
	Breakfast,
	Dinner,
	Lunch,
	Snack;
	
	/**
	 * Get code for meal type
	 * @return result - the code of the mealType
	 */
	public char getMealTypeCode(){
		char result = ' ';
		if (this.name().equals(Breakfast.name())){
			result = 'B';
		} else if (this.name().equals(Lunch.name())){
			result = 'L';
		} else if (this.name().equals(Dinner.name())){
			result = 'D';
		} else if (this.name().equals(Snack.name())){
			result = 'S';
		}
		return result;
	}
	
	/**
	 * Get meal type for given code
	 * @param code - code of meal type
	 * @return mealType - corresponding to code
	 * @throws FoodNetworkException if bad code value
	 */
	public static MealType getMealType(char code) throws FoodNetworkException {
		MealType result = null;
		switch(code){
			case 'B':
				result = Breakfast;
				break;
			case 'L':
				result = Lunch;
				break;
			case 'D':
				result = Dinner;
				break;
			case 'S':
				result = Snack;
				break;
			default:
				throw new FoodNetworkException("Bad Code");
		}
		return result;	
	}
}
