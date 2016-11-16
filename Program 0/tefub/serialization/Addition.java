/**********************************
 * Author:		John Daniel
 * Assignment:	Program 3
 * Class:		CSI 4321
 **********************************/
package tefub.serialization;

import foodnetwork.serialization.MealType;

public class Addition extends TeFubMessage {

	private String name;
	private MealType mealType;
	private long calories;

	/**
	 * Constructs from given values
	 * 
	 * @param msgId
	 *            message ID
	 * @param name
	 *            name of food item
	 * @param mealType
	 *            type of meal
	 * @param calories
	 *            number of calories in food item
	 * @throws IllegalArgumentException
	 *             if validation fails (see individual setters)
	 */
	public Addition(int msgId, String name, MealType mealType, long calories) throws IllegalArgumentException {
		super(msgId);
		setName(name);
		setMealType(mealType);
		setCalories(calories);
	}

	/**
	 * Constructs from given values w/ random msgId
	 * 
	 * @param name
	 *            name of food item
	 * @param mealType
	 *            type of meal
	 * @param calories
	 *            number of calories in food item
	 * @throws IllegalArgumentException
	 *             if validation fails (see individual setters)
	 */
	public Addition(String name, MealType mealType, long calories) throws IllegalArgumentException {
		super();
		setName(name);
		setMealType(mealType);
		setCalories(calories);
	}

	/**
	 * Set the name
	 * 
	 * @param name
	 *            new name
	 * @throws IllegalArgumentException
	 *             if validation fails (e.g. null, empty, non-ASCII)
	 */
	public void setName(String name) throws IllegalArgumentException {
		if (null == name) {
			throw new IllegalArgumentException("Illegal Argument: null name");
		}
		if (name.length() == 0) {
			throw new IllegalArgumentException("Illegal Argument: empty name");
		}
		if (!name.matches("^\\p{ASCII}*$")) {
			throw new IllegalArgumentException("Illegal Argument: non-ASCII error message");
		}
		this.name = name;
	}

	/**
	 * Set the meal type
	 * 
	 * @param mealType
	 *            new meal type
	 * @throws IllegalArgumentException
	 *             if validation fails, (e.g. null)
	 */
	public void setMealType(MealType mealType) throws IllegalArgumentException {
		if (mealType == null) {
			throw new IllegalArgumentException("Illegal Argument: null meal type");
		}
		if (mealType.getMealTypeCode() != 'B' && mealType.getMealTypeCode() != 'L' && mealType.getMealTypeCode() != 'D'
				&& mealType.getMealTypeCode() != 'S') {
			throw new IllegalArgumentException("Illegal Argument: bad meal type");
		}
		this.mealType = mealType;
	}

	/**
	 * Set the calories
	 * 
	 * @param calories
	 *            new calories
	 * @throws IllegalArgumentException
	 *             if validation fails (see serialization spec)
	 */
	public void setCalories(long calories) throws IllegalArgumentException {
		if (calories < 0 || calories > 4294967295L) {
			throw new IllegalArgumentException("Illegal Argument: bad calories");
		}
		this.calories = calories;
	}

	@Override
	public int getCode() {
		return ADDITION_CODE;
	}

	/**
	 * get the name
	 * 
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * get the meal type
	 * 
	 * @return meal type
	 */
	public MealType getMealType() {
		return mealType;
	}

	/**
	 * get the calories
	 * 
	 * @return calories
	 */
	public long getCalories() {
		return calories;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (int) (calories ^ (calories >>> 32));
		result = prime * result + ((mealType == null) ? 0 : mealType.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		if (!(obj instanceof Addition)) {
			return false;
		}
		Addition other = (Addition) obj;
		if (calories != other.calories) {
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

	@Override
	public String toString() {
		return super.toString() + " Addition [name=" + name + ", mealType=" 
								+ mealType + ", calories=" + calories + "]";
	}

}
