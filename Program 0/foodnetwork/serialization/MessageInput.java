/**********************************
 * Author:		John Daniel
 * Assignment:	Program 0
 * Class:		CSI 4321
 **********************************/
package foodnetwork.serialization;

import java.io.EOFException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class MessageInput {
	protected static final char 	SPACE_DELIMITER = ' ';
	protected static final char 	NEWLINE_DELIMITER = '\n';
	protected static final int		EOF_DELIMITER = -1;

	private InputStreamReader inr;
	private static final Charset CHARSET = StandardCharsets.US_ASCII;

	/**
	 * Constructs a new input source from an InputStream
	 * @param in - byte input source
	 * @throws FoodNetworkException 
	 * @throws IOException 
	 */
	public MessageInput(InputStream in) throws FoodNetworkException, IOException{
		inr = new InputStreamReader(in, CHARSET);
	}
	
	protected String getName() throws FoodNetworkException, EOFException{
		try{
		int charCode = getInt();
		char charReader;
		String name = "";
		for (int i = 0; i < charCode; i++){
			charReader = (char)inr.read();
			if (charReader == EOF_DELIMITER){
				throw new EOFException("Unexpected end of file");
			}
			name+= charReader;
		}
		return name;
		} catch (IOException e){
			throw new FoodNetworkException("IOException: Get name");
		}
	}
	
	protected double getDouble() throws FoodNetworkException {
		try {
			return Double.parseDouble(getNumber());
		} catch (NumberFormatException e) {
			throw new FoodNetworkException("Bad number");
		} catch (EOFException e) {
			throw new FoodNetworkException("Bad number");
		} catch (FoodNetworkException e) {
			throw new FoodNetworkException("Bad number");
		}
	}
	
	protected int getInt() throws FoodNetworkException{
		try {
			return Integer.parseInt(getNumber());
		} catch (NumberFormatException e) {
			throw new FoodNetworkException("Bad number");
		} catch (EOFException e) {
			throw new FoodNetworkException("Bad number");
		} catch (FoodNetworkException e) {
			throw new FoodNetworkException("Bad number");
		}	}
	
	protected String getNumber() throws FoodNetworkException, EOFException{
		try{
			String initNumber = "";
			char charReader;
			charReader = (char)inr.read();
			while (charReader != SPACE_DELIMITER){
				if (charReader == EOF_DELIMITER){
					throw new EOFException("Unexpected end of file");
				}
				initNumber += charReader;
				charReader = (char)inr.read();
			}
			return initNumber;
		} catch (IOException e){
			throw new FoodNetworkException("IOException: get Character Code");
		} catch (NumberFormatException e) {
			throw new FoodNetworkException("NumberFormatException");
		}
	}
	
	protected MealType getMealType() throws FoodNetworkException, EOFException{
		try{
			char mealCode = (char) inr.read();
			if (mealCode == EOF_DELIMITER){
				throw new EOFException("Unexpected end of file");
			}
			MealType mealType = MealType.getMealType(mealCode);
			return mealType;
		} catch (IOException e){
			throw new FoodNetworkException("IOException: get meal type");
		}
	}
	
	protected long getCalories() throws FoodNetworkException, EOFException{
		return (long)getInt();
	}
	
	protected String getFat() throws FoodNetworkException, EOFException{
		return String.valueOf(getNumber());
	}

	protected String getRequest() throws FoodNetworkException, EOFException {
		try{
			String request = "";
			char charAt;
			charAt = (char) inr.read();
			while(charAt != SPACE_DELIMITER){
				if (charAt == EOF_DELIMITER){
					throw new EOFException("Unexpected end of file");
				}
				request+= charAt;
				charAt = (char) inr.read();
			}
			return request;
		} catch (IOException e){
			throw new FoodNetworkException("IOException: Bad request");
		}
	}

	protected long getTimestamp() throws FoodNetworkException, EOFException {
		try {
			long timestamp;
			String initTimestamp = "";
			char charAt;
			charAt = (char) inr.read();
			while(charAt != SPACE_DELIMITER){
				if (charAt == EOF_DELIMITER){
					throw new EOFException("Unexpected end of file");
				}
				initTimestamp+= charAt;
				charAt = (char) inr.read();
			}
			timestamp = Long.parseLong(initTimestamp);
			return timestamp;
		} catch (IOException e) {
			throw new FoodNetworkException("IOException: Bad timestamp");
		} catch (NumberFormatException e) {
			throw new FoodNetworkException("NumberFormatException: Bad timestamp");
		}
	}

	protected String getErrorMessage() throws FoodNetworkException, EOFException {
		try {
			String errorMessage = "";
			char charAt;
			charAt = (char) inr.read();
			while(charAt != NEWLINE_DELIMITER){
				if (charAt == EOF_DELIMITER){
					throw new EOFException("Unexpected end of file");
				}
				errorMessage+= charAt;
				charAt = (char) inr.read();
			}
			return errorMessage;
		} catch (IOException e) {
			throw new FoodNetworkException("IOException: Bad error message");
		}
	}

	protected String getProtocol() throws FoodNetworkException, EOFException {
		try {
			String protocol = "";
			char charAt;
			charAt = (char)inr.read();
			while(charAt != SPACE_DELIMITER){
				if (charAt == EOF_DELIMITER){
					throw new EOFException("Unexpected end of file");
				}
				if (charAt != NEWLINE_DELIMITER){
					protocol+= charAt;
				}
				charAt = (char)inr.read();
			}
			return protocol;
		} catch (IOException e) {
			throw new FoodNetworkException("IOException: Bad Protocol");
		}	
	}
	
	protected void readNewLine() throws EOFException, FoodNetworkException {
		try {
			if (inr.read() == EOF_DELIMITER){
				throw new EOFException("Unexpected end of file");
			}
		} catch (IOException e) {
			throw new EOFException("IOException: Bad new line");
		}
	}

}
