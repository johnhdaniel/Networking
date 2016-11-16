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

import foodnetwork.serialization.ErrorMessage;
import foodnetwork.serialization.FoodNetworkException;

/**
 * @author john
 *
 */
public class ErrorMessageTest {
	
	ErrorMessage errorMessageTest;
	ErrorMessage errorMessageTest1;
	long messageTimestampTest;
	long messageTimestampTest1;
	String errorMessage;
	String errorMessage1;

	/**
	 * @throws java.lang.Exception FoodNetworkException
	 */
	@Before
	public void setUp() throws Exception {
		messageTimestampTest = 12345;
		messageTimestampTest1 = 54321;
		errorMessage = "This is an error message";
		errorMessage1 = "An error message, this is";
		errorMessageTest = new ErrorMessage(messageTimestampTest, errorMessage);
		errorMessageTest1 = new ErrorMessage(messageTimestampTest1, errorMessage1); 
	}

	/**
	 * Test method for {@link foodnetwork.serialization.ErrorMessage#hashCode()}.
	 */
	@Test
	public final void testHashCode() {
		ErrorMessage newErrorMessage = errorMessageTest;
		assertEquals(errorMessageTest.hashCode(), newErrorMessage.hashCode());
	}

	/**
	 * Test method for {@link foodnetwork.serialization.ErrorMessage#getRequest()}.
	 */
	@Test
	public final void testGetRequest() {
		assertEquals(errorMessageTest.getRequest(), "ERROR");
	}

	/**
	 * Test method for {@link foodnetwork.serialization.ErrorMessage#equals(java.lang.Object)}.
	 */
	@Test
	public final void testEqualsObject() {
		ErrorMessage newErrorMessage = errorMessageTest;
		assertEquals(newErrorMessage, errorMessageTest);
		assertEquals(true, newErrorMessage.equals(errorMessageTest));
		assertEquals(false, newErrorMessage.equals(errorMessageTest1));
	}

	/**
	 * Test method for {@link foodnetwork.serialization.ErrorMessage#ErrorMessage(long, java.lang.String)}.
	 * @throws FoodNetworkException Bad error message values
	 */
	@Test
	public final void testErrorMessage() throws FoodNetworkException {
		ErrorMessage newErrorMessage = new ErrorMessage(messageTimestampTest, errorMessage);
		assertEquals(newErrorMessage, errorMessageTest);
	}

	/**
	 * Test method for {@link foodnetwork.serialization.ErrorMessage#getErrorMessage()}.
	 */
	@Test
	public final void testGetErrorMessage() {
		assertEquals(errorMessage, errorMessageTest.getErrorMessage());
	}

	/**
	 * Test method for {@link foodnetwork.serialization.ErrorMessage#setErrorMessage(java.lang.String)}.
	 * @throws FoodNetworkException bad error message values
	 */
	@Test
	public final void testSetErrorMessage() throws FoodNetworkException {
		errorMessageTest.setErrorMessage(errorMessage1);
		assertEquals(errorMessage1, errorMessageTest.getErrorMessage());
	}

}
