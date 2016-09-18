/**********************************
 * Author:		John Daniel
 * Assignment:	Program 0
 * Class:		CSI 4321
 **********************************/
package foodnetwork.serialization;

import java.io.IOException;
import java.io.OutputStreamWriter;

public class MessageOutput {

	public java.lang.String	  messageOut;
	public OutputStreamWriter messageWriter;
	/**
	 * Constructs a new output source from an OutputStream
	 * @param out - byte output source
	 */
	public MessageOutput(java.io.OutputStream out){
		messageWriter = new OutputStreamWriter(out);
	}
	
	/**
	 * Writes the serialized string to the output stream
	 * @param serialized - string to be written
	 */
	public void write(java.lang.String serialized){
		try {
			messageWriter.write(serialized);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
