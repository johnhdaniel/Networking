/**********************************
 * Author:		John Daniel
 * Assignment:	Program 2
 * Class:		CSI 4321
 **********************************/
package foodnetwork.serialization.test;

import static org.junit.Assert.*;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import foodnetwork.serialization.FoodMessage;
import foodnetwork.serialization.FoodNetworkException;
import foodnetwork.serialization.MessageInput;

/**
 * @author john
 *
 */
public class FoodMessageTest {

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
	 * Test method for {@link foodnetwork.serialization.FoodMessage#hashCode()}.
	 */
	@Test
	public final void testHashCode() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link foodnetwork.serialization.FoodMessage#encode(foodnetwork.serialization.MessageOutput)}.
	 */
	@Test
	public final void testEncode() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link foodnetwork.serialization.FoodMessage#decode(foodnetwork.serialization.MessageInput)}.
	 * @throws IOException 
	 * @throws FoodNetworkException 
	 * @throws FileNotFoundException 
	 * @throws EOFException 
	 */
	@Test
	public final void testDecode() throws EOFException, FileNotFoundException, FoodNetworkException, IOException {
		FoodMessage tester = FoodMessage.decode(new MessageInput(new FileInputStream("input.txt")));
		assertEquals(tester.getMessageTimestamp(), 100);
	}

	/**
	 * Test method for {@link foodnetwork.serialization.FoodMessage#toString()}.
	 */
	@Test
	public final void testToString() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link foodnetwork.serialization.FoodMessage#getMessageTimestamp()}.
	 */
	@Test
	public final void testGetMessageTimestamp() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link foodnetwork.serialization.FoodMessage#setMessageTimestamp(long)}.
	 */
	@Test
	public final void testSetMessageTimestamp() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link foodnetwork.serialization.FoodMessage#getRequest()}.
	 */
	@Test
	public final void testGetRequest() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link foodnetwork.serialization.FoodMessage#equals(java.lang.Object)}.
	 */
	@Test
	public final void testEqualsObject() {
		fail("Not yet implemented"); // TODO
	}

}
