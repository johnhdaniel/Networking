/**********************************
 * Author:		John Daniel
 * Assignment:	Program 3
 * Class:		CSI 4321
 **********************************/
package tefub.serialization;

import java.net.Inet4Address;

public class Deregister extends TeFubRegister {

	/**
	 * Constructs from given values
	 * @param msgId message ID
	 * @param address address to deregister
	 * @param port port to deregister
	 * @throws IllegalArgumentException if validation fails (see individual setters)
	 */
	public Deregister(int msgId, Inet4Address address, int port)
     throws IllegalArgumentException {
		super(msgId, address, port);
		setAddress(address);
		setPort(port);
	}
	
	/**
	 * Construct from given values - random msgId
	 * @param address address to register
	 * @param port port to register
	 * @throws IllegalArgumentException if validation fails (see individual setters)
	 */
	public Deregister(Inet4Address address, int port) throws IllegalArgumentException {
		super(address, port);
		setAddress(address);
		setPort(port);
	}

	@Override
	public int getCode(){
		return DEREGISTER_CODE;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + port;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (!(obj instanceof Deregister)) {
			return false;
		}
		Deregister other = (Deregister) obj;
		if (address == null) {
			if (other.address != null) {
				return false;
			}
		} else if (!address.equals(other.address)) {
			return false;
		}
		if (port != other.port) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return super.toString() + " Deregister []";
	}

}
