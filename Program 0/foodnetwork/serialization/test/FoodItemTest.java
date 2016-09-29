/**********************************
 * Author:		John Daniel
 * Assignment:	Program 2
 * Class:		CSI 4321
 **********************************/
package foodnetwork.serialization.test;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import foodnetwork.serialization.FoodItem;
import foodnetwork.serialization.FoodNetworkException;
import foodnetwork.serialization.MealType;
import foodnetwork.serialization.MessageInput;
import foodnetwork.serialization.MessageOutput;

/**
 * @author John
 *
 */
public class FoodItemTest {

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link foodnetwork.serialization.FoodItem#hashCode()}.
	 */
	@Test
	public void testHashCode() {
		try {
			FoodItem testFood = new FoodItem("Plum",MealType.Snack,7,"1");
			int hash = testFood.hashCode();
			assertEquals(7,hash);
		} catch (FoodNetworkException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Test method for {@link foodnetwork.serialization.FoodItem#FoodItem(java.lang.String, foodnetwork.serialization.MealType, long, java.lang.String)}.
	 */
	@Test
	public void testFoodItemStringMealTypeLongString() {
		try {
			FoodItem testFood = new FoodItem("Plum",MealType.Breakfast,50,"3.8");

			assertEquals("Plum", testFood.getName());
			assertEquals(MealType.Breakfast,testFood.getMealType());
			assertEquals(50, testFood.getCalories());
			assertEquals("3.8", testFood.getFat());
		} catch (FoodNetworkException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Test method for {@link foodnetwork.serialization.FoodItem#FoodItem(foodnetwork.serialization.MessageInput)}.
	 */
	@Test
	public void testFoodItemMessageInput() {
		try {
			InputStream in = new FileInputStream("input.txt");
			MessageInput mIn = new MessageInput(in);
			FoodItem testFood = new FoodItem(mIn);
			
			assertEquals("Plum", testFood.getName());
			assertEquals(MealType.Breakfast,testFood.getMealType());
			assertEquals(50, testFood.getCalories());
			assertEquals("3.8", testFood.getFat());
		} catch (FoodNetworkException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Test method for {@link foodnetwork.serialization.FoodItem#encode(foodnetwork.serialization.MessageOutput)}.
	 */
	@Test
	public void testEncode() {
		try {
			FoodItem testFood = new FoodItem("Plum",MealType.Breakfast,50,"3.8");
			MessageOutput out = new MessageOutput(System.out);
			testFood.encode(out);
			assertEquals("4 PlumB50 3.8 ", out.messageOut);
		} catch (FoodNetworkException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Test method for {@link foodnetwork.serialization.FoodItem#toString()}.
	 */
	@Test
	public void testToString() {
		try {
			FoodItem testFood = new FoodItem("Plum",MealType.Breakfast,50,"3.8");
			String testString = "Name: Plum MealType: Breakfast Calories: 50 Fat: 3.8";
			assertEquals(testString, testFood.toString());
		} catch (FoodNetworkException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	/**
	 * Test method for {@link foodnetwork.serialization.FoodItem#getName()}.
	 */
	@Test
	public void testGetName() {
		try {
			FoodItem testFood = new FoodItem("Plum",MealType.Breakfast,50,"3.8");
			assertEquals("Plum", testFood.getName());
		} catch (FoodNetworkException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	/**
	 * Test method for {@link foodnetwork.serialization.FoodItem#setName(java.lang.String)}.
	 */
	@Test
	public void testSetName() {
		try {
			FoodItem testFood = new FoodItem("Plum",MealType.Breakfast,50,"3.8");
			testFood.setName("Apple");
			assertEquals("Apple", testFood.getName());
		} catch (FoodNetworkException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Test method for {@link foodnetwork.serialization.FoodItem#getMealType()}.
	 */
	@Test
	public void testGetMealType() {
		try {	
			FoodItem testFood = new FoodItem("Plum",MealType.Breakfast,50,"3.8");
			assert(MealType.Breakfast.equals(testFood.getMealType()));
		} catch (FoodNetworkException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Test method for {@link foodnetwork.serialization.FoodItem#setMealType(foodnetwork.serialization.MealType)}.
	 */
	@Test
	public void testSetMealType() {
		try {	
			FoodItem testFood = new FoodItem("Plum",MealType.Breakfast,50,"3.8");
			testFood.setMealType(MealType.Lunch);
			assert(MealType.Lunch.equals(testFood.getMealType()));
		} catch (FoodNetworkException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Test method for {@link foodnetwork.serialization.FoodItem#getCalories()}.
	 */
	@Test
	public void testGetCalories() {
		try {
			FoodItem testFood = new FoodItem("Plum",MealType.Breakfast,50,"3.8");
			assertEquals(50, testFood.getCalories());
		} catch (FoodNetworkException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Test method for {@link foodnetwork.serialization.FoodItem#setCalories(long)}.
	 */
	@Test
	public void testSetCalories() {
		try {
			FoodItem testFood = new FoodItem("Plum",MealType.Breakfast,50,"3.8");
			testFood.setCalories(100);
			assertEquals(100, testFood.getCalories());
		} catch (FoodNetworkException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Test method for {@link foodnetwork.serialization.FoodItem#getFat()}.
	 */
	@Test
	public void testGetFat() {
		try {
			FoodItem testFood = new FoodItem("Plum",MealType.Breakfast,50,"3.8");
			assertEquals("3.8", testFood.getFat());
		} catch (FoodNetworkException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Test method for {@link foodnetwork.serialization.FoodItem#setFat(java.lang.String)}.
	 */
	@Test
	public void testSetFat() {
		try {
			FoodItem testFood = new FoodItem("Plum",MealType.Breakfast,50,"3.8");
			testFood.setFat("6.0");
			assertEquals("6.0", testFood.getFat());
		} catch (FoodNetworkException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Test method for {@link foodnetwork.serialization.FoodItem#equals(java.lang.Object)}.
	 */
	@Test
	public void testEqualsObject() {
		try {
			FoodItem testFood = new FoodItem("Plum",MealType.Breakfast,50,"3.8");
			FoodItem testFood2 = new FoodItem("Plum",MealType.Breakfast,50,"3.8");
			assert(testFood.equals(testFood2));
		} catch (FoodNetworkException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
