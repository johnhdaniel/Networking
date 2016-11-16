/**********************************
 * Author:		John Daniel
 * Assignment:	Program 3
 * Class:		CSI 4321
 **********************************/
package foodnetwork.serialization;

public class Interval extends FoodMessage {
	
	int intervalTime;
	
	/**
	 * Constructs Interval using set values
	 * @param messageTimestamp message timestamp
	 * @param intervalTime minutes into the past for interval EX: If the wall 
	 * 		clock time is now 08:00 and the intervalTime is 5, then this is 
	 * 		effectively a GET request for food eaten from 07:55 to 08:00.
	 * @throws foodnetwork.serialization.FoodNetworkException if validation fails
	 */
	public Interval(long messageTimestamp, int intervalTime)
						throws foodnetwork.serialization.FoodNetworkException {
		setMessageTimestamp(messageTimestamp);
		setIntervalTime(intervalTime);
	}

	/**
	 * Sets the intervalTime
	 * @param intervalTime intervalTime
	 * @throws foodnetwork.serialization.FoodNetworkException if invalid intervalTime
	 */
	public void setIntervalTime(int intervalTime)
            throws foodnetwork.serialization.FoodNetworkException {
		if (intervalTime <= 0) {
			throw new FoodNetworkException("Bad Interval Time");
		}
		this.intervalTime = intervalTime;
	}
	
	@Override
	public String toString() {
		return "Interval [intervalTime=" + intervalTime + ", messageTimestamp=" + messageTimestamp + "]";
	}
	
	/**
	 * returns interal time
	 * @return intervalTime
	 */
	public int getIntervalTime() {
		return intervalTime;
	}

	@Override
	public String getRequest() {
		return INTERVAL_REQUEST;
	}

	@Override
	public void getEncode(MessageOutput out) throws FoodNetworkException {
		out.write(getRequest() + " " + intervalTime + " " + '\n');

	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + intervalTime;
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
		if (!(obj instanceof Interval)) {
			return false;
		}
		Interval other = (Interval) obj;
		if (intervalTime != other.intervalTime) {
			return false;
		}
		return true;
	}

}
