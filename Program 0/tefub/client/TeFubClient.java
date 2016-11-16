/**********************************
 * Author:		John Daniel
 * Assignment:	Program 6
 * Class:		CSI 4321
 **********************************/
package tefub.client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Scanner;

import tefub.serialization.*;
import tefub.serialization.Error;
import tefub.utility.AddressUtility;

/**
 * TeFubClient class - Client Protocol for TeFubMessage
 * 	using UDP. 
 * @author John Daniel
 *
 */
public class TeFubClient {

	private static final int TIMEOUT = 3000;
	private static final int MAX_TRIES = 2;
	private static final int NUM_ARGS = 2;
	private static final int NO_TIMEOUT = 0;
	private static final int BUFFER_SIZE = 100;

	/**
	 * TeFubClient main - send a register packet and wait for an ACK response,
	 * 	handle any other messages received during waiting time.
	 * If no ACK times out twice, terminate.
	 * When correct ACK received, handle all packets afterward.
	 * Terminate on "quit" given by user input.
	 * @param args Server IP/name - Server Port
	 */
	public static void main(String[] args) {

		if (args.length != NUM_ARGS) {
			throw new IllegalArgumentException("Parameters: <Server IP/name> <Server Port>");
		}

		try {
			Inet4Address serverAddress = (Inet4Address) InetAddress.getByName(args[0]);
			int servPort = Integer.parseInt(args[1]);
			Inet4Address localAddress = (Inet4Address) AddressUtility.getAddress();

			DatagramSocket socket = new DatagramSocket();
			socket.connect(serverAddress, servPort);

			Register register = new Register(localAddress, socket.getLocalPort());

			byte[] outPacket = register.encode();

			socket.setSoTimeout(TIMEOUT);

			DatagramPacket sendPacket = new DatagramPacket(outPacket, outPacket.length, 
															serverAddress, servPort);

			byte[] inPacket = new byte[BUFFER_SIZE];

			DatagramPacket receivePacket = new DatagramPacket(inPacket, inPacket.length);

			// Thread to check for user input "quit" - send deregister message
			Thread getQuit = new Thread() {
				public void run() {
					Scanner scanner = new Scanner(System.in);
					String quit;
					while (true) {
						quit = scanner.nextLine();
						if (quit.endsWith("quit")) {
							scanner.close();
							try {
								Deregister deReg = new Deregister(localAddress, 
																socket.getLocalPort());
								byte[] buffer = deReg.encode();
								sendPacket.setData(buffer);
								socket.send(sendPacket);
							} catch (IOException e) {
								System.err.println("Unable to send deregister packet");
								socket.close();
								System.exit(1);
							}
							socket.close();
							System.exit(1);
						}
					}
				}
			};
			getQuit.start();

			int tries = 0;

			TeFubMessage tfm;

			boolean connection = false;
			do {
				try {
					socket.send(sendPacket);

					try {
						// Get the packet - data
						socket.receive(receivePacket);
						byte[] data = receivePacket.getData();

						// Decode data
						tfm = TeFubMessage.decode(data);
						// Check for corresponding ACK
						if (tfm.getCode() == TeFubMessage.ACK_CODE) {
							if (tfm.getMsgId() != register.getMsgId()) {
								System.err.print("Unexpected MSG ID: ");
								System.err.print("expected <" + register.getMsgId());
								System.err.println("> actual <" + tfm.getMsgId() + ">");
							} else {
								connection = true;
							}
						} else {
							// Handle the tfm from the packet
							handlePacket(tfm);
						}
					} catch (SocketTimeoutException e) {
						tries++;
					} catch (IOException | IllegalArgumentException e) {
						System.err.println("Unable to parse message: " + e.getMessage());
					}
					if (tries == MAX_TRIES) {
						System.err.println("Unable to register");
						socket.close();
						System.exit(1);
					}
				} catch (IOException e) {
					System.err.println("Unable to send packet: " + e.getMessage());
				}
			} while (tries < MAX_TRIES && !connection);

			// Set timeout to 0 for infinite timeout
			socket.setSoTimeout(NO_TIMEOUT);
			while (connection) {
				// Get next packet
				try {
					socket.receive(receivePacket);

					byte[] data = receivePacket.getData();

					try {
						tfm = TeFubMessage.decode(data);
						handlePacket(tfm);
					} catch (IllegalArgumentException | IOException e) {
						System.err.println("Unable to parse message: " + e.getMessage());
					}
				} catch (IOException e) {
					System.err.println("Error receiving packet: " + e.getMessage());
				}
			}
		} catch (SocketException e) {
			System.err.println("Problem connecting sockets: " + e.getMessage());
			System.exit(1);
		} catch (UnknownHostException e) {
			System.err.println("Invalid address parameter: " + e.getMessage());
			System.exit(1);
		}

	}

	/**
	 * Protocol for handling TeFubMessage
	 * @param tfm TeFubMessage to be handled
	 */
	private static void handlePacket(TeFubMessage tfm) {
		if (tfm.getCode() == TeFubMessage.ADDITION_CODE) {
			System.out.println("Addition: " + ((Addition) tfm).toString());
		} else if (tfm.getCode() == TeFubMessage.ERROR_CODE) {
			System.out.println(((Error) tfm).getErrorMessage());
		} else if (tfm.getCode() == TeFubMessage.REGISTER_CODE 
				|| tfm.getCode() == TeFubMessage.DEREGISTER_CODE) {
			System.err.println("Unexpected message type");
		}
	}

}
