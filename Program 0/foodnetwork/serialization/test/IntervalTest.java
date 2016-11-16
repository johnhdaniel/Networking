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

import foodnetwork.serialization.FoodNetworkException;
import foodnetwork.serialization.Interval;

/**
 * @author john
 *
 */
public class IntervalTest {
	
	long messageTimestamp;
	int intervalTime;
	Interval tester;

	/**
	 * @throws java.lang.Exception FoodNetworkException
	 */
	@Before
	public void setUp() throws Exception {
		messageTimestamp = 12345;
		intervalTime = 9999;
		tester = new Interval(messageTimestamp, intervalTime);
	}

	/**
	 * Test method for {@link foodnetwork.serialization.Interval#getRequest()}.
	 */
	@Test
	public final void testGetRequest() {
		assertEquals("INTERVAL", tester.getRequest());
	}

	/**
	 * Test method for {@link foodnetwork.serialization.Interval#equals(java.lang.Object)}.
	 * @throws FoodNetworkException if validation fails
	 */
	@Test
	public final void testEqualsObject() throws FoodNetworkException {
		Interval newTest = tester;
		assertEquals(newTest, tester);
	}

	/**
	 * Test method for {@link foodnetwork.serialization.Interval#Interval(long, int)}.
	 * @throws FoodNetworkException if validation fails
	 */
	@Test
	public final void testInterval() throws FoodNetworkException {
		Interval newTest = new Interval(messageTimestamp, intervalTime);
		assertEquals(newTest, tester);
	}

	/**
	 * Test method for {@link foodnetwork.serialization.Interval#setIntervalTime(int)}.
	 * @throws FoodNetworkException if validation fails
	 */
	@Test
	public final void testSetIntervalTime() throws FoodNetworkException {
		tester.setIntervalTime(1111);
		assertEquals(1111, tester.getIntervalTime());
	}

	/**
	 * Test method for {@link foodnetwork.serialization.Interval#getIntervalTime()}.
	 */
	@Test
	public final void testGetIntervalTime() {
		assertEquals(intervalTime, tester.getIntervalTime());
	}

}
