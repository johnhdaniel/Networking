/**********************************
 * Author:		John Daniel
 * Assignment:	Program 3
 * Class:		CSI 4321
 **********************************/
package tefub.serialization;

public class ACK extends TeFubMessage {

	/**
	 * Constructs from given values
	 * @param msgId message ID
	 * @throws IllegalArgumentException if bad msgId
	 */
	public ACK(int msgId) throws IllegalArgumentException {
		super(msgId);
	}
	
	@Override
	public int getCode(){
		return ACK_CODE;
	}

	@Override
	public String toString() {
		return super.toString() + " ACK []";
	}

}
