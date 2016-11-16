/**********************************
 * Author:		John Daniel
 * Assignment:	Program 3
 * Class:		CSI 4321
 **********************************/
package foodnetwork.serialization;

import java.io.EOFException;

public abstract class FoodMessage {

	public static final String 	PROTOCOL = "FN1.0";
	public static final String 	ADD_REQUEST = "ADD";
	public static final String 	GET_REQUEST = "GET";
	public static final String 	LIST_REQUEST = "LIST";
	public static final String 	ERROR_REQUEST = "ERROR";
	public static final String	INTERVAL_REQUEST = "INTERVAL";
	public static final char	SPACE = ' ';
	public static final char	NEWLINE = '\n';
	
	protected long messageTimestamp;
	protected static String request;

	/**
	 * Serializes message to MessageOutput
	 * 
	 * @param out
	 *            serialization output destination
	 * @throws FoodNetworkException
	 *             if serialization output fails
	 */
	public void encode(MessageOutput out) throws FoodNetworkException {
		if (out == null) {
			throw new FoodNetworkException("null outputstream");
		}
		String encoder = PROTOCOL + SPACE + messageTimestamp + SPACE;
		out.write(encoder);
		getEncode(out);
	}

	/**
	 * Deserializes message from byte source
	 * 
	 * @param in
	 *            deserialization input source
	 * @return a specific FoodMessage resulting from deserialization
	 * @throws FoodNetworkException
	 *             if deserialization or validation failure
	 * @throws java.io.EOFException
	 *             if premature end of stream
	 */
	public static FoodMessage decode(MessageInput in) throws FoodNetworkException, java.io.EOFException {
		try {
			if (in == null) {
				throw new FoodNetworkException("bad input stream");
			}
			String protocol = in.getStringWithSpaceDelimiter();
			if (!protocol.equals(PROTOCOL)) {
				throw new FoodNetworkException("Unexpected Version: " + protocol);
			}
			long initMessageTimestamp = in.getLong();
			request = in.getStringWithSpaceDelimiter();
			if (request.equals(ADD_REQUEST)) {
				AddFood result = new AddFood(initMessageTimestamp, new FoodItem(in));
				in.readNewLine();
				return result;
			} else if (request.equals(GET_REQUEST)) {
				GetFood result = new GetFood(initMessageTimestamp);
				in.readNewLine();
				return result;
			} else if (request.equals(INTERVAL_REQUEST)) {
				int time = in.getInt();
				Interval result = new Interval(initMessageTimestamp, time);
				in.readNewLine();
				return result;
			} else if (request.equals(LIST_REQUEST)) {
				long modifiedTimestamp = in.getLong();
				FoodList result = new FoodList(initMessageTimestamp, modifiedTimestamp);
				int numInList = (int) in.getInt();
				for (int i = 0; i < numInList; i++) {
					result.addFoodItem(new FoodItem(in));
				}
				in.readNewLine();
				return result;
			} else if (request.equals(ERROR_REQUEST)) {
				String errorMessage = in.getStringByNumberLength();
				ErrorMessage result = new ErrorMessage(initMessageTimestamp, errorMessage);
				in.readNewLine();
				return result;
			} else {
				throw new FoodNetworkException("Unexpected Request: " + request);
			}
		} catch (EOFException e) {
			throw new EOFException("Unexpected end of file");
		}

	}

	@Override
	public String toString() {
		return "FoodMessage [messageTimestamp=" + messageTimestamp + "]";
	}

	/**
	 * Returns message timestamp
	 * 
	 * @return message timestamp
	 */
	public final long getMessageTimestamp() {
		return messageTimestamp;

	}

	/**
	 * Sets message timestamp
	 * 
	 * @param messageTimestamp
	 *            message timestamp
	 * @throws FoodNetworkException
	 *             if validation fails
	 */
	public final void setMessageTimestamp(long messageTimestamp) throws FoodNetworkException {
		if (messageTimestamp < 0) {
			throw new FoodNetworkException("Bad message timestamp");
		}
		this.messageTimestamp = messageTimestamp;
	}

	/**
	 * Returns message request (e.g., ADD)
	 * 
	 * @return message request
	 */
	public abstract String getRequest();

	/**
	 * Serializes a food message
	 * @param out byte output source
	 * @throws FoodNetworkException if serialization/deserialization fails
	 */
	public abstract void getEncode(MessageOutput out) throws FoodNetworkException;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (messageTimestamp ^ (messageTimestamp >>> 32));
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
		if (!(obj instanceof FoodMessage)) {
			return false;
		}
		FoodMessage other = (FoodMessage) obj;
		if (messageTimestamp != other.messageTimestamp) {
			return false;
		}
		return true;
	}

}
