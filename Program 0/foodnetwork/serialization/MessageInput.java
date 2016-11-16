/**********************************
 * Author:		John Daniel
 * Assignment:	Program 3
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
	 * @throws FoodNetworkException if deserialization fails
	 * @throws IOException if bad InputStream
	 */
	public MessageInput(InputStream in) throws FoodNetworkException, IOException{
		inr = new InputStreamReader(in, CHARSET);
	}
	
	/**
	 * Finds a string started with a character count
	 * @return String containing characters after the character count
	 * @throws FoodNetworkException if serialization/deserialization fails
	 * @throws EOFException if premature end of file
	 */
	protected String getStringByNumberLength() throws FoodNetworkException, EOFException{
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
	
	/**
	 * returns a double
	 * @return a double
	 * @throws FoodNetworkException if serialization/deserialization fails
	 */
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
	
	/**
	 * returns an integer value
	 * @return an integer value
	 * @throws FoodNetworkException if serialization/deserialization fails
	 */
	protected int getInt() throws FoodNetworkException{
		try {
			return Integer.parseInt(getNumber());
		} catch (NumberFormatException e) {
			throw new FoodNetworkException("Bad number");
		} catch (EOFException e) {
			throw new FoodNetworkException("Bad number");
		} catch (FoodNetworkException e) {
			throw new FoodNetworkException("Bad number");
		}	
	}
	
	/**
	 * returns a string form of a number
	 * @return String representing a number
	 * @throws FoodNetworkException if serialization/deserialization fails
	 * @throws EOFException if premature end of file
	 */
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
	
	/**
	 * Returns a char
	 * @return char received by reading one character
	 * @throws FoodNetworkException if serialization/deserialization fails
	 * @throws EOFException if premature end of file
	 */
	protected char getChar() throws FoodNetworkException, EOFException{
		try{
			char mealCode = (char) inr.read();
			if (mealCode == EOF_DELIMITER){
				throw new EOFException("Unexpected end of file");
			}
			return mealCode;
		} catch (IOException e){
			throw new FoodNetworkException("IOException: get meal type");
		}
	}
	
	/**
	 * returns a converted long from the String returned by getNumber()
	 * @return long converted from String
	 * @throws FoodNetworkException if serialization/deserialization fails
	 * @throws EOFException if premature end of file
	 */
	protected long getLong() throws FoodNetworkException {
		try {
			return Long.valueOf(getNumber());
		} catch (NumberFormatException e) {
			throw new FoodNetworkException("Bad number");
		} catch (EOFException e) {
			throw new FoodNetworkException("Bad number");
		} catch (FoodNetworkException e) {
			throw new FoodNetworkException("Bad number");
		}	
		
	}

	/**
	 * returns a string request
	 * @return String request
	 * @throws FoodNetworkException if serialization/deserialization fails
	 * @throws EOFException if premature end of file
	 */
	protected String getStringWithSpaceDelimiter() throws FoodNetworkException, EOFException {
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
	
	/**
	 * reads a new line to ensure end of a message
	 * @throws EOFException if premature end of file
	 * @throws FoodNetworkException if serialization/deserialization fails
	 */
	protected void readNewLine() throws EOFException, FoodNetworkException {
		try {
			if (inr.read() != NEWLINE_DELIMITER){
				throw new EOFException("Unexpected end of file");
			}
		} catch (IOException e) {
			throw new EOFException("IOException: Bad new line");
		}
	}

}
