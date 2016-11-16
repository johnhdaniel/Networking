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

import foodnetwork.serialization.FoodMessage;
import foodnetwork.serialization.FoodNetworkException;
import foodnetwork.serialization.GetFood;

/**
 * @author john
 *
 */
public class GetFoodTest {

	GetFood test;
	long messageTimestamp;

	/**
	 * @throws java.lang.Exception FoodNetworkException
	 */
	@Before
	public void setUp() throws Exception {
		test = new GetFood(messageTimestamp);
	}

	/**
	 * Test method for {@link foodnetwork.serialization.GetFood#getRequest()}.
	 */
	@Test
	public final void testGetRequest() {
		assertEquals("GET", test.getRequest());
	}

	/**
	 * Test method for {@link foodnetwork.serialization.GetFood#GetFood(long)}.
	 * @throws FoodNetworkException if validation fails
	 */
	@Test
	public final void testGetFood() throws FoodNetworkException {
		GetFood newTest = new GetFood(messageTimestamp);
		assertEquals(newTest, test);
	}

}
