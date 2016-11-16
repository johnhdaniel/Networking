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

import foodnetwork.serialization.FoodItem;
import foodnetwork.serialization.FoodList;
import foodnetwork.serialization.FoodNetworkException;
import foodnetwork.serialization.MealType;

/**
 * @author john
 *
 */
public class FoodListTest {
	
	long messageTimestamp;
	long messageTimestamp1;
	long modifiedTimestamp;
	long modifiedTimestamp1;
	FoodList foodListTest;
	FoodList foodListTest1;

	/**
	 * @throws java.lang.Exception FoodNetworkException
	 */
	@Before
	public void setUp() throws Exception {
		foodListTest = new FoodList(messageTimestamp, modifiedTimestamp);
		foodListTest1 = new FoodList(messageTimestamp1, modifiedTimestamp1);
	}

	/**
	 * Test method for {@link foodnetwork.serialization.FoodList#hashCode()}.
	 */
	@Test
	public final void testHashCode() {
		FoodList newTest = foodListTest;
		assertEquals(newTest.hashCode(), foodListTest.hashCode());
	}

	/**
	 * Test method for {@link foodnetwork.serialization.FoodList#getRequest()}.
	 */
	@Test
	public final void testGetRequest() {
		assertEquals("LIST", foodListTest.getRequest());
	}

	/**
	 * Test method for {@link foodnetwork.serialization.FoodList#equals(java.lang.Object)}.
	 */
	@Test
	public final void testEqualsObject() {
		FoodList newTest = foodListTest;
		assertEquals(true, newTest.equals(foodListTest));
	}

	/**
	 * Test method for {@link foodnetwork.serialization.FoodList#FoodList(long, long)}.
	 * @throws FoodNetworkException if constructor fails
	 */
	@Test
	public final void testFoodList() throws FoodNetworkException {
		FoodList newTest = new FoodList(messageTimestamp, modifiedTimestamp);
		assertEquals(foodListTest, newTest);
	}

	/**
	 * Test method for {@link foodnetwork.serialization.FoodList#getModifiedTimestamp()}.
	 */
	@Test
	public final void testGetModifiedTimestamp() {
		assertEquals(modifiedTimestamp, foodListTest.getModifiedTimestamp());
	}

	/**
	 * Test method for {@link foodnetwork.serialization.FoodList#setModifiedTimestamp(long)}.
	 * @throws FoodNetworkException if validation failure
	 */
	@Test
	public final void testSetModifiedTimestamp() throws FoodNetworkException {
		foodListTest.setModifiedTimestamp(modifiedTimestamp1);
		assertEquals(modifiedTimestamp1, foodListTest.getModifiedTimestamp());
	}

	/**
	 * Test method for {@link foodnetwork.serialization.FoodList#getFoodItemList()}.
	 * @throws FoodNetworkException if validation failure
	 */
	@Test
	public final void testGetFoodItemList() throws FoodNetworkException {
		FoodItem testFood = new FoodItem("Plum",MealType.Snack,7,"1");
		foodListTest.addFoodItem(testFood);
		assertEquals(testFood, foodListTest.getFoodItemList().get(0));
	}

	/**
	 * Test method for {@link foodnetwork.serialization.FoodList#addFoodItem(foodnetwork.serialization.FoodItem)}.
	 * @throws FoodNetworkException  if validation failure
	 */
	@Test
	public final void testAddFoodItem() throws FoodNetworkException {
		FoodItem testFood = new FoodItem("Plum",MealType.Snack,7,"1");
		foodListTest.addFoodItem(testFood);
		assertEquals(testFood, foodListTest.getFoodItemList().get(0));
	}

}
