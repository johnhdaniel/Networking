package tefub.serialization;

import java.net.Inet4Address;
import java.net.InetSocketAddress;

public abstract class TeFubRegister extends TeFubMessage {
	
	protected int port;
	protected Inet4Address address;

	/**
	 * Constructs from given values
	 * @param msgId message ID
	 * @param address address to register
	 * @param port port to register
	 * @throws IllegalArgumentException if validation fails (see individual setters)
	 */
	public TeFubRegister(int msgId, Inet4Address address, int port)
								throws IllegalArgumentException {
		super(msgId);
		setAddress(address);
		setPort(port);
	}
	
	/**
	 * Construct from given values - random msgId
	 * @param address address to register
	 * @param port port to register
	 * @throws IllegalArgumentException if validation fails (see individual setters)
	 */
	public TeFubRegister(Inet4Address address, int port) throws IllegalArgumentException {
		super();
		setAddress(address);
		setPort(port);
	}

	/**
	 * Set register port
	 * @param port register port
	 * @throws IllegalArgumentException if port is out of range
	 */
	public void setPort(int port) throws IllegalArgumentException {
		if (port < 0 || port > 65535) {
			throw new IllegalArgumentException("Illegal Argument: Bad port " + port);
		}
		this.port = port;
	}

	/**
	 * Set register address
	 * @param address register address
	 * @throws IllegalArgumentException if validation fails (e.g. null, multicast)
	 */
	public void setAddress(Inet4Address address) throws IllegalArgumentException {
		if (address == null){
			throw new IllegalArgumentException("Illegal Argument: null Address");
		}
		if (address.isMulticastAddress()){
			throw new IllegalArgumentException("Illegal Argument: multicast Address");
		}
		this.address = address;
	}
	
	@Override
	public abstract int getCode();
	
	/**
	 * get the register port
	 * @return register port
	 */
	public int getPort(){
		return port;
	}
	
	/**
	 * get the register address
	 * @return return register address
	 */
	public Inet4Address getAddress(){
		return address;
	}

	/**
	 * Get the register socket address
	 * @return register socket address
	 */
	public InetSocketAddress getSocketAddress(){
		return new InetSocketAddress(address, port);
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
		if (!(obj instanceof TeFubRegister)) {
			return false;
		}
		TeFubRegister other = (TeFubRegister) obj;
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
		return super.toString() + " TeFubRegister [port=" + port + ", address=" + address + "]";
	}

}
