/**********************************
 * Author:		John Daniel
 * Assignment:	Program 3
 * Class:		CSI 4321
 **********************************/
package foodnetwork.serialization.test;

import static org.junit.Assert.*;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import foodnetwork.serialization.FoodMessage;
import foodnetwork.serialization.FoodNetworkException;
import foodnetwork.serialization.MessageInput;
import foodnetwork.serialization.MessageOutput;

/**
 * @author john
 *
 */
public class FoodMessageTest {

	FoodMessage tester;

	/**
	 * @throws java.lang.Exception FoodNetworkException
	 */
	@Before
	public void setUp() throws Exception {
		tester = FoodMessage.decode(new MessageInput(new FileInputStream("input.txt")));
	}

	/**
	 * Test method for {@link foodnetwork.serialization.FoodMessage#decode(foodnetwork.serialization.MessageInput)}.
	 * @throws IOException if input/output failure
	 * @throws FoodNetworkException if serialization/deserialization failure
	 * @throws FileNotFoundException if file not found
	 * @throws EOFException if premature end of file
	 */
	@Test
	public final void testDecode() throws EOFException, FileNotFoundException, FoodNetworkException, IOException {
		assertEquals(tester.getRequest(), "ADD");
		assertEquals(tester.getMessageTimestamp(), 100);
	}

	/**
	 * Test method for {@link foodnetwork.serialization.FoodMessage#getMessageTimestamp()}.
	 */
	@Test
	public final void testGetMessageTimestamp() {
		assertEquals(tester.getMessageTimestamp(), 100);
	}

	/**
	 * Test method for {@link foodnetwork.serialization.FoodMessage#setMessageTimestamp(long)}.
	 * @throws FoodNetworkException if validation fails
	 */
	@Test
	public final void testSetMessageTimestamp() throws FoodNetworkException {
		tester.setMessageTimestamp(12345);
		assertEquals(tester.getMessageTimestamp(), 12345);
	}

	/**
	 * Test method for {@link foodnetwork.serialization.FoodMessage#getRequest()}.
	 */
	@Test
	public final void testGetRequest() {
		assertEquals(tester.getRequest(), "ADD");
	}

	/**
	 * Test method for {@link foodnetwork.serialization.FoodMessage#equals(java.lang.Object)}.
	 */
	@Test
	public final void testEqualsObject() {
		FoodMessage newTest = tester;
		assertEquals(true, newTest.equals(tester));
	}

}
