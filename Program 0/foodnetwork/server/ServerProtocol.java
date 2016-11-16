/**********************************
 * Author:		John Daniel
 * Assignment:	Program 6
 * Class:		CSI 4321
 **********************************/
package foodnetwork.server;

import java.io.EOFException;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.baylor.googlefit.FoodManager;
import edu.baylor.googlefit.GFitFoodManager;
import foodnetwork.serialization.*;
import tefub.serialization.Addition;
import tefub.server.TeFubPair;
import tefub.server.TeFubServer;

/**
 * ServerProtocol class - TCP server protocol for the FoodNetworkServer
 * @author John Daniel 
 *
 */
public class ServerProtocol implements Runnable {

	private static final String JSON_PATH = "./FatBat.json";
	private static final String PROJECT_NUMBER = "43556664153";
	private Socket clntSock;
	private Logger logger;
	private TeFubServer tfs;

	/**
	 * Construct a server protocol out of the given values
	 * @param clntSock socket used for TCP communication
	 * @param logger Logger used to write log files
	 * @param tfs TeFubServer used to send Addition messages for AddFood
	 */
	public ServerProtocol(Socket clntSock, Logger logger, TeFubServer tfs) {
		this.clntSock = clntSock;
		this.logger = logger;
		this.tfs = tfs;
	}

	/**
	 * Handle communication between server and client
	 */
	public void handleServerClient() {
		FoodMessage result = null;
		MessageInput in = null;
		MessageOutput out = null;
		try {
			in = new MessageInput(clntSock.getInputStream());
			out = new MessageOutput(clntSock.getOutputStream());
		} catch (FoodNetworkException e1) {
			String log = "Could not create MessageInput";
			logger.log(Level.FINER, log);
			System.exit(1);
		} catch (IOException e1) {
			String log = "Unable to open socket input/output stream";
			logger.log(Level.FINER, log);
			System.exit(1);
		}
		InetAddress sockIP = clntSock.getInetAddress();
		int sockPort = clntSock.getPort();

		FoodManager mgr = null;
		try {
			mgr = new GFitFoodManager(PROJECT_NUMBER, JSON_PATH);
		} catch (FoodNetworkException e1) {
			String log = "Unable to connect to GFitFoodManager";
			logger.log(Level.FINER, log);
			System.exit(1);
		}
		
		String log = "New Connection: Handling client " + clntSock.getInetAddress();
		log += " - " + clntSock.getPort() + " with thread id " 
						+ Thread.currentThread().getId() + "\n";
		logger.log(Level.FINE, log);

		

		while (true) {
			try {
				FoodMessage foodMessage = FoodMessage.decode(in);

				log = "Receive Message: Received from " + sockIP + " - ";
				log += sockPort + " " + foodMessage.toString() + "\n";
				logger.log(Level.FINE, log);

				if (foodMessage.getRequest().equals(FoodMessage.ADD_REQUEST)) {
					// Handle an ADD request
					addRequest(foodMessage, mgr, tfs);
					result = getRequest(foodMessage, mgr);
				} else if (foodMessage.getRequest().equals(FoodMessage.GET_REQUEST)) {
					// Handle a GET request
					result = getRequest(foodMessage, mgr);
				} else if (foodMessage.getRequest().equals(FoodMessage.INTERVAL_REQUEST)) {
					// Handle an INTERVAL request
					result = intervalRequest(foodMessage, mgr);
				} else if (foodMessage.getRequest().equals(FoodMessage.LIST_REQUEST)
						|| foodMessage.getRequest().equals(FoodMessage.ERROR_REQUEST)) {
					// Handle an incorrect request
					result = badRequest(foodMessage);
				}

				log = "Send Message: Sent to " + sockIP + " - ";
				log += sockPort + " " + result.toString() + "\n";
				logger.log(Level.FINE, log);

				result.encode(out);
			} catch (FoodNetworkException | EOFException e) {
				try {
					if (e.getMessage().startsWith("Unexpected Version")) {
						result = new ErrorMessage(getTimestamp(), e.getMessage());
					} else if (e.getMessage().startsWith("Unexpected Request")) {
						result = new ErrorMessage(getTimestamp(), "Unknown operation: " 
												+ e.getMessage());
					} else {
						result = new ErrorMessage(getTimestamp(), "Unable to parse message");
					}
					result.encode(out);
				} catch (FoodNetworkException e1) {
					log = "Unable to send error message";
					logger.log(Level.FINER, log);
				}
			}
		}

	}

	/**
	 * Handle a bad request from the client
	 * 
	 * @param foodMessage
	 *            FoodMessage object
	 * @return ErrorMessage
	 * @throws FoodNetworkException
	 *             if serialization fails
	 */
	private static FoodMessage badRequest(FoodMessage foodMessage) throws FoodNetworkException {
		return new ErrorMessage(foodMessage.getMessageTimestamp(),
				"Unexpected message type: " + foodMessage.getRequest());
	}

	/**
	 * Handle an Interval request
	 * 
	 * @param foodMessage
	 *            FoodMessage object
	 * @param mgr
	 *            FoodManager object, responsible for adding to the server
	 * @return FoodList containing all the foodItems in the given interval
	 * @throws FoodNetworkException
	 *             if FoodList creation fails
	 */
	private static FoodMessage intervalRequest(FoodMessage foodMessage, 
												FoodManager mgr) throws FoodNetworkException {
		FoodList foodList = new FoodList(getTimestamp(), mgr.getLastModified());
		for (FoodItem foodItem : mgr.getFoodItems(((Interval) foodMessage).getIntervalTime())) {
			foodList.addFoodItem(foodItem);
		}
		return foodList;
	}

	/**
	 * Handles a Get request
	 * 
	 * @param foodMessage
	 *            FoodMessage object
	 * @param mgr
	 *            FoodManager object, responsible for adding to the server
	 * @return FoodList containing all foodItems stored
	 * @throws FoodNetworkException
	 *             if FoodList creation fails
	 */
	private static FoodMessage getRequest(FoodMessage foodMessage, 
											FoodManager mgr) throws FoodNetworkException {
		FoodList foodList = new FoodList(getTimestamp(), mgr.getLastModified());
		for (FoodItem foodItem : mgr.getFoodItems()) {
			foodList.addFoodItem(foodItem);
		}
		return foodList;
	}

	/**
	 * Handles an Add request
	 * 
	 * @param foodMessage
	 *            FoodMessage object
	 * @param mgr
	 *            FoodManager object, responsible for adding to the server
	 * @throws FoodNetworkException
	 *             if FoodItem creation fails
	 */
	private void addRequest(FoodMessage foodMessage, FoodManager mgr, TeFubServer tfs)
			throws FoodNetworkException {
		FoodItem foodItem = ((AddFood) foodMessage).getFoodItem();
		mgr.addFood(foodItem.getName(), foodItem.getMealType(), 
					foodItem.getCalories(), foodItem.getFat());
		addFood(foodItem);
	}

	/**
	 * Broadcast Addition to TeFubServer's clients
	 * 
	 * @param foodItem
	 *            food item being added
	 */
	private void addFood(FoodItem foodItem) {
		Addition add = new Addition(foodItem.getName(), 
									foodItem.getMealType(), 
									foodItem.getCalories());
		byte[] buffer = add.encode();

		for (TeFubPair<Integer, Inet4Address> pair : tfs.getClientList()) {
			try {
				DatagramPacket packet = new DatagramPacket(buffer, buffer.length,
						new InetSocketAddress(pair.getAddress(), pair.getPort()));
				String log = "SENT: Source IP: [" + tfs.getSocket().getLocalAddress() + "] ";
				log+= "Source Port: [" + tfs.getSocket().getLocalPort() + "] ";
				log+= "MESSAGE: " + add.toString() + "\n";
				logger.log(Level.FINE, log);
				tfs.getSocket().send(packet);
			} catch (IOException e) {
				String log = "Unable to send packet, IOException: " + e.getMessage();
				logger.log(Level.FINER, log);
			}
		}
	}

	/**
	 * returns current timestamp
	 * 
	 * @return current timestamp
	 */
	private static long getTimestamp() {
		// Get the timestamp
		Date date = new Date();
		return date.getTime();
	}

	@Override
	public void run() {
		handleServerClient();
	}

}
