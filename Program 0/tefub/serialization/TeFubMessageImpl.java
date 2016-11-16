/**********************************
 * Author:		John Daniel
 * Assignment:	Program 3
 * Class:		CSI 4321
 **********************************/
package tefub.serialization;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;

public abstract class TeFubMessageImpl extends TeFubMessage{

	/**
	 * Checks if valid version
	 * @param count place holder for pkt array
	 * @param pkt byte array
	 * @throws IllegalArgumentException if invalid version
	 */
	public static void checkVersion(int count, byte[] pkt) throws IllegalArgumentException {
		int version = (int)(pkt[count] & (byte)0xf0) >>> 4;
		if (version != 3){
			throw new IllegalArgumentException("Illegal Argument: Bad version " + version);
		}
	}

	/**
	 * returns the code
	 * @param count place holder for pkt array
	 * @param pkt byte array
	 * @return operation code
	 */
	public static int getCode(int count, byte[] pkt) {
		int code = (int)(pkt[count] & (byte)0x0f);
		return code;
	}

	/**
	 * gets the msgId
	 * @param count place holder for pkt array
	 * @param pkt byte array
	 * @return message ID
	 */
	public static int getMsgId(int count, byte[] pkt) {
		int newMsgId = (int)(pkt[count] & 0xff);
		return newMsgId;
	}
	
	/**
	 * gets the name length
	 * @param count place holder for pkt array
	 * @param pkt byte array
	 * @return length of name
	 */
	public static int getLength(int count, byte[] pkt) {
		int length = (int)(pkt[count] & 0xff);
		if (length <= 0 || length > 255){
			throw new IllegalArgumentException("bad name length");
		}
		count++;
		return length;
	}

	/**
	 * gets the name
	 * @param count place holder for pkt array
	 * @param pkt byte array
	 * @param length length of bytes in name
	 * @return name name of foodItem
	 */
	public static String getName(int count, byte[] pkt, int length) {
		byte[] buffer = new byte[length];
		for (int i = count; i < length + count; i++){
			buffer[i - count] = pkt[i];
		}
		String name = new String(buffer, StandardCharsets.US_ASCII);
		return name;
	}

	/**
	 * gets the mealtype string
	 * @param count place holder for pkt array
	 * @param pkt byte array
	 * @return mealtype string
	 */
	public static char getMealType(int count, byte[] pkt) {
		byte[] buffer = new byte[2];
		buffer[0] = pkt[count];
		count++;
		buffer[1] = pkt[count];
		count++;
		String mealString = new String(buffer, StandardCharsets.US_ASCII);
		char mealType = mealString.charAt(0);
		if (buffer[1] != 0){
			throw new IllegalArgumentException("Bad OS");
		}
		return mealType;
	}

	/**
	 * gets the long calories
	 * @param count place holder for pkt array
	 * @param pkt byte array
	 * @return calories
	 */
	public static long getCalories(int count, byte[] pkt) {
		byte[] buffer = new byte[4];
		buffer[0] = pkt[count];
		count++;
		buffer[1] = pkt[count];
		count++;
		buffer[2] = pkt[count];
		count++;
		buffer[3] = pkt[count];
		count++;
		long calories = ByteBuffer.wrap(buffer).order(ByteOrder.LITTLE_ENDIAN).getInt();
		return calories;
	}

	/**
	 * gets the error message
	 * @param count place holder for pkt array
	 * @param pkt byte array
	 * @return error message
	 */
	public static String getError(int count, byte[] pkt) {
		byte[] buffer = new byte[pkt.length - count];
		for (int i = count; i < pkt.length; i++){
			buffer[i - count] = pkt[i];
		}
		String error = new String(buffer, StandardCharsets.US_ASCII);
		return error;
	}

	/**
	 * gets the Inet4Address
	 * @param count place holder for pkt array
	 * @param pkt byte array
	 * @return address
	 */
	public static Inet4Address getAddress(int count, byte[] pkt) {
		try {
			byte[] buffer = new byte[4];
			buffer[0] = pkt[count];
			count++;
			buffer[1] = pkt[count];
			count++;
			buffer[2] = pkt[count];
			count++;
			buffer[3] = pkt[count];
			count++;
			byte[] newAddress = new byte[4];
			for (int i = 0; i < buffer.length; i++){
				newAddress[buffer.length-i-1] = buffer[i];
			}
//			newAddress = ByteBuffer.wrap(buffer).order(ByteOrder.LITTLE_ENDIAN).array();
			Inet4Address address = (Inet4Address) Inet4Address.getByAddress(newAddress);
			return address;
		} catch (UnknownHostException e) {
			throw new IllegalArgumentException("Illegal Argument: unkown host");
		}
	}

	/**
	 * gets the port
	 * @param count place holder for pkt array
	 * @param pkt byte array
	 * @return port
	 */
	public static int getPort(int count, byte[] pkt) {
		byte[] buffer = new byte[4];
		buffer[0] = pkt[count];
		count++;
		buffer[1] = pkt[count];
		count++;
		buffer[2] = (byte)0;
		buffer[3] = (byte)0;
		int port = ByteBuffer.wrap(buffer).order(ByteOrder.LITTLE_ENDIAN).getInt();
		return port;
	}
}


