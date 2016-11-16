/**********************************
 * Author:		John Daniel
 * Assignment:	Program 6
 * Class:		CSI 4321
 **********************************/
package tefub.server;


/**
 * TeFubPair class - Class holding pair of ports
 * 	and addresses, each represented by an Integer and 
 * 	an Inet4Address
 * @author John Daniel 
 * 
 * @param <L> Left side of pair, represents port
 * @param <R> Right side of pair, represents address
 */
public class TeFubPair<L, R> {

	private final L port;
	private final R address;

	/**
	 * Construct a TeFubPair from given values
	 * @param port port of TeFubPair
	 * @param address address of TeFubPair
	 */
	public TeFubPair(L port, R address) {
		this.port = port;
		this.address = address;
	}

	/**
	 * get the port
	 * @return port
	 */
	public L getPort() {
		return port;
	}

	/**
	 * get the address
	 * @return address
	 */
	public R getAddress() {
		return address;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((port == null) ? 0 : port.hashCode());
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
		if (!(obj instanceof TeFubPair)) {
			return false;
		}
		TeFubPair<?, ?> other = (TeFubPair<?, ?>) obj;
		if (address == null) {
			if (other.address != null) {
				return false;
			}
		} else if (!address.equals(other.address)) {
			return false;
		}
		if (port == null) {
			if (other.port != null) {
				return false;
			}
		} else if (!port.equals(other.port)) {
			return false;
		}
		return true;
	}

	
}
