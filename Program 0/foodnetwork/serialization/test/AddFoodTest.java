/**********************************
 * Author:		John Daniel
 * Assignment:	Program 3
 * Class:		CSI 4321
 **********************************/
package foodnetwork.serialization.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import foodnetwork.serialization.AddFood;
import foodnetwork.serialization.FoodItem;
import foodnetwork.serialization.FoodNetworkException;
import foodnetwork.serialization.MealType;

/**
 * @author john
 *
 */
public class AddFoodTest {
	
	AddFood addFoodTest;
	FoodItem foodItemTest;
	AddFood addFoodTest1;
	FoodItem foodItemTest1;

	/**
	 * @throws java.lang.Exception FoodNetworkException
	 */
	@Before
	public void setUp() throws Exception {
		foodItemTest = new FoodItem("Plum", MealType.Snack, 10, "1.0");
		addFoodTest = new AddFood(12345, foodItemTest);
		foodItemTest1 = new FoodItem("Pear", MealType.Breakfast, 9, "1.6");
		addFoodTest1 = new AddFood(54321, foodItemTest1);
	}

	/**
	 * Test method for {@link foodnetwork.serialization.AddFood#hashCode()}.
	 */
	@Test
	public final void testHashCode() {
		AddFood newAddFood = addFoodTest;
		assertEquals(newAddFood.hashCode(), addFoodTest.hashCode());
	}

	/**
	 * Test method for {@link foodnetwork.serialization.AddFood#getRequest()}.
	 */
	@Test
	public final void testGetRequest() {
		assertEquals(addFoodTest.getRequest(), "ADD");
	}

	/**
	 * Test method for {@link foodnetwork.serialization.AddFood#equals(java.lang.Object)}.
	 */
	@Test
	public final void testEqualsObject() {
		AddFood newAddFood = addFoodTest;
		assertEquals(newAddFood.equals(addFoodTest), true);
		assertEquals(addFoodTest.equals(addFoodTest1), false);
	}

	/**
	 * Test method for {@link foodnetwork.serialization.AddFood#AddFood(long, foodnetwork.serialization.FoodItem)}.
	 * @throws FoodNetworkException bad addFood values
	 */
	@Test
	public final void testAddFood() throws FoodNetworkException {
		AddFood newAddFood = new AddFood(12345, foodItemTest);
		assertEquals(newAddFood, addFoodTest);
	}

	/**
	 * Test method for {@link foodnetwork.serialization.AddFood#getFoodItem()}.
	 */
	@Test
	public final void testGetFoodItem() {
		assertEquals(foodItemTest, addFoodTest.getFoodItem());
	}

	/**
	 * Test method for {@link foodnetwork.serialization.AddFood#setFoodItem(foodnetwork.serialization.FoodItem)}.
	 * @throws FoodNetworkException Bad food item
	 */
	@Test
	public final void testSetFoodItem() throws FoodNetworkException {
		addFoodTest.setFoodItem(foodItemTest1);
		assertEquals(foodItemTest1, addFoodTest.getFoodItem());
	}

}
