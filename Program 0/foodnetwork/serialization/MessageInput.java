/**********************************
 * Author:		John Daniel
 * Assignment:	Program 0
 * Class:		CSI 4321
 **********************************/
package foodnetwork.serialization;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class MessageInput {

	public char messageIn[] = new char[2048];
	/**
	 * Constructs a new input source from an InputStream
	 * @param in - byte input source
	 */
	public MessageInput(java.io.InputStream in){
		try {	
			InputStreamReader inr = new InputStreamReader(in);
			char buffer[] = new char[1024];
			StringBuilder charCount = new StringBuilder();
			int place = 0;
			buffer[place] = (char) inr.read();
			while(buffer[place] != ' '){
				charCount.append(buffer[place]);
				place++;
				buffer[place] = (char) inr.read();
			}
			place++;
			int characterCount = Integer.parseInt(new String(charCount));
			inr.read(messageIn, 0, characterCount);
			place = characterCount;
			messageIn[place] = ' ';
			place++;
			messageIn[place] = (char) inr.read();
			place++;
			messageIn[place] = ' ';
			
			int numSpaces = 0;
			place++;
			while(numSpaces < 2){
				messageIn[place] = (char) inr.read();
				if (messageIn[place] == ' '){
					numSpaces++;
				}
				place++;
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
