/**********************************
 * Author:		John Daniel
 * Assignment:	Program 0
 * Class:		CSI 4321
 **********************************/
package foodnetwork.serialization;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class MessageInput {

	private InputStreamReader inr;
	private static final Charset CHARSET = StandardCharsets.US_ASCII;

	/**
	 * Constructs a new input source from an InputStream
	 * @param in - byte input source
	 * @throws FoodNetworkException 
	 * @throws IOException 
	 */
	public MessageInput(java.io.InputStream in) throws FoodNetworkException, IOException{
		inr = new InputStreamReader(in, CHARSET);
	}
	
	protected String getName() throws FoodNetworkException{
		try{
		int charCode = (int)getNumber();
		String name = "";
		for (int i = 0; i < charCode; i++){
			name+= (char)inr.read();
		}
		return name;
		} catch (IOException e){
			throw new FoodNetworkException("IOException: Get name");
		}
	}
	
	protected double getNumber() throws FoodNetworkException{
		try{
			double number;
			String initNumber = "";
			char charReader;
			charReader = (char)inr.read();
			while (charReader != ' '){
				initNumber += charReader;
				charReader = (char)inr.read();
			}
			number = Double.parseDouble(initNumber);
			return number;
		} catch (IOException e){
			throw new FoodNetworkException("IOException: get Character Code");
		} catch (NumberFormatException e) {
			throw new FoodNetworkException("NumberFormatException");
		}
	}
	
	protected MealType getMealType() throws FoodNetworkException{
		try{
			char mealCode = (char) inr.read();
			MealType mealType = MealType.getMealType(mealCode);
			return mealType;
		} catch (IOException e){
			throw new FoodNetworkException("IOException: get meal type");
		}
	}
	
	protected long getCalories() throws FoodNetworkException{
		return (long)getNumber();
	}
	
	protected String getFat() throws FoodNetworkException{
		return String.valueOf(getNumber());
	}

	protected String getRequest() throws FoodNetworkException {
		try{
			String request = "";
			char charAt;
			charAt = (char) inr.read();
			while(charAt != ' '){
				request+= charAt;
				charAt = (char) inr.read();
			}
			return request;
		} catch (IOException e){
			throw new FoodNetworkException("IOException: Bad request");
		}
	}

	protected long getTimestamp() throws FoodNetworkException {
		try {
			long timestamp;
			String initTimestamp = "";
			char charAt;
			charAt = (char) inr.read();
			while(charAt != ' '){
				initTimestamp+= charAt;
				charAt = (char) inr.read();
			}
			timestamp = Long.parseLong(initTimestamp);
			return timestamp;
		} catch (IOException e) {
			throw new FoodNetworkException("IOException: Bad timestamp");
		}
	}

	protected String getErrorMessage() throws FoodNetworkException {
		try {
			String errorMessage = "";
			char charAt;
			charAt = (char) inr.read();
			while(charAt != '\n'){
				errorMessage+= charAt;
				charAt = (char) inr.read();
			}
			return errorMessage;
		} catch (IOException e) {
			throw new FoodNetworkException("IOException: Bad error message");
		}
	}

	protected void getProtocol() throws FoodNetworkException {
		try {
			String protocol = "";
			char charAt;
			charAt = (char)inr.read();
			while(charAt != ' ' || charAt != '\0'){
				if (charAt != '\n'){
					protocol+= charAt;
				}
			}
		} catch (IOException e) {
			throw new FoodNetworkException("IOException: Bad Protocol");
		}	
	}

}
