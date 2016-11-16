/**********************************
 * Author:		John Daniel
 * Assignment:	Program 3
 * Class:		CSI 4321
 **********************************/
package tefub.serialization;

import java.io.IOException;
import java.net.Inet4Address;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;

import foodnetwork.serialization.FoodNetworkException;
import foodnetwork.serialization.MealType;

public abstract class TeFubMessage {

	private int msgId;
	public static final int ACK_CODE = 4;
	public static final int ERROR_CODE = 3;
	public static final int DEREGISTER_CODE = 2;
	public static final int ADDITION_CODE = 1;
	public static final int REGISTER_CODE = 0;
	private static final int MAX_MSG_ID = 255;
	private static final int MIN_MSG_ID = 0;

	/**
	 * Constructs from given values
	 * 
	 * @param msgId
	 *            message ID
	 * @throws IllegalArgumentException
	 *             if bad message ID
	 */
	public TeFubMessage(int msgId) throws IllegalArgumentException {
		setMsgId(msgId);
	}
	
	/**
	 * Constructs with random msgId
	 * @throws IllegalArgumentException if msgId is out of range
	 */
	public TeFubMessage() throws IllegalArgumentException {
		int id = (int) Math.random() * MAX_MSG_ID + 1;
		setMsgId(id);
	}

	/**
	 * sets msgId
	 * 
	 * @param msgId
	 *            message ID
	 * @throws IllegalArgumentException
	 *             if msgId is out of range
	 */
	public void setMsgId(int msgId) throws IllegalArgumentException {
		if (msgId < MIN_MSG_ID || msgId > MAX_MSG_ID) {
			throw new IllegalArgumentException("Illegal Argument: Bad message ID " + msgId);
		}
		this.msgId = msgId;
	}

	/**
	 * Deserializes from byte array
	 * 
	 * @param pkt
	 *            byte array to deserialize
	 * @return a specific message resulting from deserialization
	 * @throws IllegalArgumentException
	 *             if bad version or code or attribute setter failure
	 * @throws IOException
	 *             if I/O problem including packet too long/short (EOFException)
	 */
	public static TeFubMessage decode(byte[] pkt) throws IllegalArgumentException, IOException {
		TeFubMessage tfm;
		int count = 0; //0
		if (pkt.length < 2){
			throw new IOException("Packet length too small");
		}
		TeFubMessageImpl.checkVersion(count, pkt);

		int code = TeFubMessageImpl.getCode(count, pkt);
		count++; //1

		int newMsgId = TeFubMessageImpl.getMsgId(count, pkt);
		count++; //2

		if (code == ACK_CODE) {
			if (pkt.length != 2){
				throw new IOException("Packet length incorrect");
			}
			tfm = new ACK(newMsgId);
			return tfm;
		} else if (code == ADDITION_CODE) {
			if (pkt.length < 9){
				throw new IOException("Packet length incorrect");
			}
			int length = TeFubMessageImpl.getLength(count,pkt);
			count++; //3
			if ((pkt.length - length) != 9){
				throw new IOException("Packet length incorrect");
			}

			String name = TeFubMessageImpl.getName(count, pkt, length);
			count+= length; // 3 + length
			
			char mealType = TeFubMessageImpl.getMealType(count, pkt);
			count+= 2; // 5 + length
			
			long calories = TeFubMessageImpl.getCalories(count, pkt);
			count+= 4; // 9 + length [9-12]
			try {
				tfm = new Addition(newMsgId, name, MealType.getMealType(mealType), calories);
			} catch (FoodNetworkException e) {
				throw new IllegalArgumentException(e.getMessage());
			}
			return tfm;
		} else if (code == ERROR_CODE) {
			String error = TeFubMessageImpl.getError(count, pkt);

			tfm = new Error(newMsgId, error);
			return tfm;
		} else if (code == REGISTER_CODE || code == DEREGISTER_CODE) {
			if (pkt.length != 8){
				throw new IOException("Packet length incorrect");
			}
			Inet4Address address = TeFubMessageImpl.getAddress(count, pkt);
			count+= 4; //6
			
			int port = TeFubMessageImpl.getPort(count, pkt);
			count+= 2; //8
			
			if (code == REGISTER_CODE) {
				tfm = new Register(newMsgId, address, port);
			} else {
				tfm = new Deregister(newMsgId, address, port);
			}
			return tfm;
		} else {
			throw new IllegalArgumentException("Unknown code: " + code);
		}
	}

	/**
	 * Serializes message
	 * @return serialized message bytes
	 */
	public byte[] encode() {
		byte[] result;
		byte versionCode = (byte) ((3 << 4) | getCode());
		byte byteMsgId = (byte)getMsgId();
		if (getCode() == ACK_CODE) {
			result = new byte[2];
			result[0] = versionCode;
			result[1] = byteMsgId;
			return result;
		} else if (getCode() == ERROR_CODE) {
			byte[] error = ((Error) this).getErrorMessage().getBytes(StandardCharsets.US_ASCII);
			result = new byte[error.length + 2];
			result[0] = versionCode;
			result[1] = byteMsgId;
			for (int i = 0; i < error.length; i++) {
				result[i + 2] = error[i];
			}
			return result;
		} else if (getCode() == REGISTER_CODE) {
			byte[] address = ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).put(((Register) this).getAddress().getAddress()).array();
			byte[] port = ByteBuffer.allocate(2).order(ByteOrder.LITTLE_ENDIAN)
					.putShort((short) ((Register) this).getPort()).array();
			result = new byte[address.length + port.length + 2];
			result[0] = versionCode;
			result[1] = byteMsgId;
			byte[] newAddress = new byte[address.length];
			for (int i = 0; i < address.length; i++){
				newAddress[i] = address[address.length - i - 1];
			}
			for (int i = 0; i < newAddress.length; i++) {
				result[i + 2] = newAddress[i];
			}
			for (int i = 0; i < port.length; i++) {
				result[i + newAddress.length + 2] = port[i];
			}
			return result;
		} else if (getCode() == DEREGISTER_CODE) {
			byte[] address = ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).put(((Deregister) this).getAddress().getAddress()).array();
			byte[] port = ByteBuffer.allocate(2).order(ByteOrder.LITTLE_ENDIAN)
					.putShort((short) ((Deregister) this).getPort()).array();
			result = new byte[address.length + port.length + 2];
			result[0] = versionCode;
			result[1] = byteMsgId;
			byte[] newAddress = new byte[address.length];
			for (int i = 0; i < address.length; i++){
				newAddress[i] = address[address.length - i - 1];
			}
			for (int i = 0; i < newAddress.length; i++) {
				result[i + 2] = newAddress[i];
			}
			for (int i = 0; i < port.length; i++) {
				result[i + newAddress.length + 2] = port[i];
			}
			return result;
		} else {
			int length = ((Addition) this).getName().length();
			byte byteLength = (byte) (length);
			byte[] name = ((Addition) this).getName().getBytes(StandardCharsets.US_ASCII);
			String stringCode = String.valueOf(((Addition) this).getMealType().getMealTypeCode());
			byte[] mealCode = stringCode.getBytes(StandardCharsets.US_ASCII);
//			byte[] mealType = ((Addition) this).getMealType().toString().getBytes(StandardCharsets.US_ASCII);
			byte[] calories = ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN)
					.putInt((int)((Addition) this).getCalories()).array();
			result = new byte[2 + 1 + name.length + mealCode.length + 1 + calories.length];
			result[0] = versionCode;
			result[1] = byteMsgId;
			result[2] = byteLength;
			for (int i = 0; i < name.length; i++){
				result[i + 3] = name[i];
			}
			
			result[3 + name.length] = mealCode[0];
			result[4 + name.length] = (byte)0;
			
			for (int i = 0; i < calories.length; i++){
				result[i + 3 + name.length + mealCode.length + 1] = calories[i];
			}
			return result;
		}

	}

	/**
	 * returns message ID
	 * 
	 * @return msgId
	 */
	public int getMsgId() {
		return msgId;
	}

	/**
	 * get the operation code
	 * 
	 * @return operation code
	 */
	public abstract int getCode();

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + msgId;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof TeFubMessage)) {
			return false;
		}
		TeFubMessage other = (TeFubMessage) obj;
		if (msgId != other.msgId) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "TeFubMessage [msgId=" + msgId + "]";
	}
	
}	


