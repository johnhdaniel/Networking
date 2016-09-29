/**********************************
 * Author:		John Daniel
 * Assignment:	Program 2
 * Class:		CSI 4321
 **********************************/
package foodnetwork.serialization;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class MessageOutput {

	public String	  messageOut;
	public OutputStreamWriter messageWriter;
	public static final Charset CHARSET = StandardCharsets.US_ASCII;
	/**
	 * Constructs a new output source from an OutputStream
	 * @param out - byte output source
	 */
	public MessageOutput(OutputStream out){
		messageWriter = new OutputStreamWriter(out, CHARSET);
	}
	
	/**
	 * Writes the serialized string to the output stream
	 * @param serialized - string to be written
	 * @throws FoodNetworkException 
	 */
	public void write(String serialized) throws FoodNetworkException{
		try {
			messageWriter.write(serialized);
			messageWriter.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new FoodNetworkException("Serialization Failed");
		}
	}
}
