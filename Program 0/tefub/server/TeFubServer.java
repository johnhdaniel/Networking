/**********************************
 * Author:		John Daniel
 * Assignment:	Program 6
 * Class:		CSI 4321
 **********************************/
package tefub.server;

import java.util.List;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import tefub.serialization.*;
import tefub.serialization.Error;

/**
 * TeFubServer class - TeFubServer protocol using UDP to receive
 * 	and send datagram packets to and from the client. 
 * @author John Daniel 
 *
 */
public class TeFubServer implements Runnable {

	private int servPort;
	private Logger logger;
	private List<TeFubPair<Integer, Inet4Address> > clientList;
	private DatagramSocket socket;

	/**
	 * Construct a TeFubServer from the given values
	 * @param servPort port of the server
	 * @param logger Logger for log files
	 */
	public TeFubServer(int servPort, Logger logger) {
		this.servPort = servPort;
		this.logger = logger;
		clientList = new ArrayList<>();
		try {
			socket = new DatagramSocket(servPort);
		} catch (SocketException e) {
			String log = "Cannot establish socket connection, SocketException: "
							+ e.getMessage();
			logger.log(Level.FINER, log);
			System.exit(1);
		}

	}

	/**
	 * TeFubServer Protocol. 
	 * Receive Register: 	Send error if register is already
	 * 							registered 
	 * 						Send error if address/port does not 
	 * 							match socket 
	 * 						Send ACK to client if correct protocol 
	 * Receive Deregister: 	Send error if address/port is not
	 * 							in clientList 
	 * 						Remove specified client from list if 
	 * 							correct protocol 
	 * Receive any other 
	 * Tefub Message: 		Send error to client 
	 * Receive unexpected 
	 * 	code: 				Send error to client 
	 * Problem parsing 
	 * 	message: 			Send error to client
	 */
	public void servUDP() {

		byte[] buffer = new byte[100];

		while (true) {
			try {
				DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

				socket.receive(packet);

				TeFubMessage tfm;

				try {
					tfm = TeFubMessage.decode(packet.getData());

					if (tfm.getCode() == TeFubMessage.REGISTER_CODE 
							|| tfm.getCode() == TeFubMessage.DEREGISTER_CODE) {
						String log = "RECEIVED: Source IP: [" + packet.getAddress() +"] ";
						log+= "Source Port: [" + packet.getPort() + "] ";
			
						Inet4Address address = ((TeFubRegister) tfm).getAddress();
						int port = ((TeFubRegister) tfm).getPort();
						if (tfm.getCode() == TeFubMessage.REGISTER_CODE) {
							log+= "MESSAGE: " + ((Register)tfm).toString() + "\n";
							logger.log(Level.FINE, log);
							
							// Check if packet is already registered
							if (inList(address, port)) {
								// send error msg
								String errorMessage = "Already registered";
								Error error = new Error(tfm.getMsgId(), errorMessage);

								sendErrorMsg(error, packet);
							}
							// Check that address/port match packet
							else if (!addressPortMatches(address, port)) {
								// send error msg
								String errorMessage = "Incorrect Address or Port";
								Error error = new Error(tfm.getMsgId(), errorMessage);

								sendErrorMsg(error, packet);
							}
							// Add address/port to client list
							// Respond w/ ACK w/ msgId from packet
							else {
								addClient(address, port);

								ACK ack = new ACK(tfm.getMsgId());
								sendACKMsg(ack, packet);
							}

						} else if (tfm.getCode() == TeFubMessage.DEREGISTER_CODE) {
							log+= "MESSAGE: " + ((Deregister)tfm).toString() + "\n";
							logger.log(Level.FINE, log);
							// Check that address/port is in client list
							if (!inList(address, port)) {
								// send error message
								String errorMessage = "Unknown client";
								Error error = new Error(tfm.getMsgId(), errorMessage);

								sendErrorMsg(error, packet);
							}
							// Remove address/port from client list
							else {
								removeClient(address, port);
							}
						}
					} else if (tfm.getCode() == TeFubMessage.ACK_CODE 
							|| tfm.getCode() == TeFubMessage.ADDITION_CODE
							|| tfm.getCode() == TeFubMessage.ERROR_CODE) {
						String log = "RECEIVED: Source IP: [" + packet.getAddress() + "] ";
						log+= "Source Port: [" + packet.getPort() + "] ";
						if (tfm.getCode() == TeFubMessage.ACK_CODE){
							log+= "MESSAGE: " + ((ACK)tfm).toString() + "\n";
						} else if (tfm.getCode() == TeFubMessage.ADDITION_CODE){
							log+= "MESSAGE: " + ((Addition)tfm).toString() + "\n";
						} else if (tfm.getCode() == TeFubMessage.ERROR_CODE){
							log+= "MESSAGE: " + ((Error)tfm).toString() + "\n";
						}
						logger.log(Level.FINE, log);
						
						// Respond w/ Error message
						String errorMessage = "Unexpected message type: " + tfm.getCode();
						Error error = new Error(tfm.getMsgId(), errorMessage);

						sendErrorMsg(error, packet);
					} 
				} catch (IllegalArgumentException | IOException e) {
					String errorMessage;
					if (e.getMessage().contains("Unknown code")){
						errorMessage = e.getMessage(); 
					} else {
						// Unable to parse message
						errorMessage = "Unable to parse message";
					}
					Error error = new Error(0, errorMessage);

					sendErrorMsg(error, packet);
				}
			} catch (IOException e) {
				String log = "Unable to receive packet, IOException: " + e.getMessage();
				logger.log(Level.FINER, log);
			}
		}
	}

	@Override
	public void run() {
		servUDP();
	}

	/**
	 * Send the ACK message to the client
	 * 
	 * @param ack
	 *            ACK to be sent
	 * @param packet
	 *            Packet to be sent to client
	 * @throws IOException
	 *             if I/O error in sending packet
	 */
	private void sendACKMsg(ACK ack, DatagramPacket packet) throws IOException {
		String log = "SENT: Source IP: [" + socket.getLocalAddress() + "] ";
		log+= "Source Port: [" + socket.getLocalPort() + "] ";
		log+= "MESSAGE: " + ack.toString() + "\n";
		logger.log(Level.FINE, log);
		
		byte[] buffer = ack.encode();
		packet.setData(buffer);
		socket.send(packet);
	}

	/**
	 * Send the error message to the client
	 * 
	 * @param error
	 *            Error to be sent to the client
	 * @param packet
	 *            packet to be sent to the client
	 * @throws IOException
	 *             if I/O error in sending packet
	 */
	private void sendErrorMsg(Error error, DatagramPacket packet) throws IOException {
		String log = "SENT: Source IP: [" + socket.getLocalAddress() + "] ";
		log+= "Source Port: [" + socket.getLocalPort() + "] ";
		log+= "MESSAGE: " + error.toString() + "\n";
		logger.log(Level.FINE, log);
		
		byte[] buffer = error.encode();
		packet.setData(buffer);
		socket.send(packet);
	}

	/**
	 * Remove a client from the clientList
	 * 
	 * @param address
	 *            address of client
	 * @param port
	 *            port of client
	 */
	private void removeClient(Inet4Address address, int port) {
		clientList.remove(new TeFubPair<Integer, Inet4Address>(port, address));
	}

	/**
	 * Add client to clientList
	 * 
	 * @param address
	 *            address of client
	 * @param port
	 *            port of client
	 */
	private void addClient(Inet4Address address, int port) {
		clientList.add(new TeFubPair<Integer, Inet4Address>(port, address));
	}

	/**
	 * Returns true if address and port match the socket
	 * 
	 * @param address
	 *            address in message
	 * @param port
	 *            port in message
	 * @return true if equal, false otherwise
	 */
	private boolean addressPortMatches(Inet4Address address, int port) {
		if (address == (Inet4Address) socket.getInetAddress() && port == socket.getPort()) {
			return true;
		}
		return false;
	}

	/**
	 * Returns true if client is in clientList
	 * 
	 * @param address
	 *            address in message
	 * @param port
	 *            port in message
	 * @return true if in clientList, false otherwise
	 */
	private boolean inList(Inet4Address address, int port) {
		return clientList.contains(new TeFubPair<Integer, Inet4Address>(port, address));
	}

	/**
	 * get the server port
	 * 
	 * @return server port
	 */
	public int getServPort() {
		return servPort;
	}

	/**
	 * set the server port
	 * 
	 * @param servPort
	 *            server port to set
	 */
	public void setServPort(int servPort) {
		this.servPort = servPort;
	}

	/**
	 * get the logerr
	 * 
	 * @return logger
	 */
	public Logger getLogger() {
		return logger;
	}

	/**
	 * set the logger
	 * 
	 * @param logger
	 *            logger to be set
	 */
	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	/**
	 * get the client list
	 * 
	 * @return client list
	 */
	public List<TeFubPair<Integer, Inet4Address>> getClientList() {
		return clientList;
	}

	/**
	 * set the clientList
	 * 
	 * @param clientList
	 *            clientList to be set
	 */
	public void setClientList(List<TeFubPair<Integer, Inet4Address>> clientList) {
		this.clientList = clientList;
	}

	/**
	 * get the socket
	 * 
	 * @return socket
	 */
	public DatagramSocket getSocket() {
		return socket;
	}

	/**
	 * set the socket
	 * 
	 * @param socket
	 *            socket to be set
	 */
	public void setSocket(DatagramSocket socket) {
		this.socket = socket;
	}

}
