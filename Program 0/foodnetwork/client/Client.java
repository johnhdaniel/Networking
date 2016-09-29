/**********************************
 * Author:		John Daniel
 * Assignment:	Program 2
 * Class:		CSI 4321
 **********************************/
package foodnetwork.client;

import java.net.Socket;
import java.util.Date;
import java.util.Scanner;

import foodnetwork.serialization.*;

import java.io.EOFException;
import java.io.IOException;

public class Client {
	
	private static Scanner scan;
	private static Socket socket;

	/**
	 * Client method to communicate with FatBat server. Adds food items or requests
	 * 	all food items.
	 * @param args Arrary of strings containing the server address and port number
	 * @throws IOException if socket failure occurs
	 */
	public static void main(String[] args) throws IOException {
		// Test for correct # of args
		if (args.length != 2){ 
			throw new IllegalArgumentException("Parameter(s): <Server> [<Port>]");
		}
		
		try {
		
			String server = args[0]; // Server name or IP address
			int servPort = Integer.parseInt(args[1]); // Port #
			
			// Create socket that is connected to server on specified port
			socket = new Socket(server, servPort);
			
			if (!socket.isConnected()){
				System.err.println("Unable to communicate: Socket is not connected");
				System.exit(-1);
			}
			
			MessageInput in   = new MessageInput(socket.getInputStream());
			MessageOutput out = new MessageOutput(socket.getOutputStream());
			
			scan = new Scanner(System.in);
			scan.useDelimiter("\n");			
			
			while(true) {
				boolean goodInput = true;
				FoodMessage foodMessage = null;
				
				String request = askRequest();
				
				long timestamp = getTimestamp();
				
				if (request.equals("ADD")){
					
					String name = getName();
					
					char mealCode = getMealCode();
					
					long calories = getCalories();
					
					String fat = getFat();
					
					// Create a food item
					FoodItem foodItem = new FoodItem(name, MealType.getMealType(mealCode), calories, fat);
					// Create an Add food message
					foodMessage = new AddFood(timestamp, foodItem);
					
				} else if (request.equals("GET")){
					// Create a Get food message
					foodMessage = new GetFood(timestamp);
				} 
				// Print the timestamp
				System.out.println("Msg TS=" + timestamp + " - FoodItem");
				
				foodMessage.encode(out);
				
				decode(in);
				
				continueRequests();
				
			}
			
			
		} catch (FoodNetworkException e) {
			System.err.println("\nInvalid message: " + e.getMessage());
			socket.close();
			System.exit(-1);
		} catch (EOFException e) {
			System.err.println("\nInvalid message: " + e.getMessage());
			socket.close();
			System.exit(-1);
		} catch (IOException e) {
			System.err.println("\nInvalid message: " + e.getMessage());
			socket.close();
			System.exit(-1);
		}
	}

	/**
	 * Asks client for (y/n) - asks repeatedly until correct input
	 * @throws IOException if socket error occurs
	 */
	private static void continueRequests() throws IOException {
		boolean goodInput = false;
		while (!goodInput){
			goodInput = true;
			System.out.print("\nContinue (y/n)>");
			String next = scan.next();
			if (next.equals("n")){
				socket.close();
				System.exit(0);
			} else if (!next.equals("y")){
				System.err.println("\nInvalid user input: " + next);
				goodInput = false;
			}
		}
	}

	/**
	 * Prints response received from server
	 * @param in byte input source array
	 * @throws EOFException if premature end of file reached
	 * @throws FoodNetworkException if deserialization fails
	 */
	private static void decode(MessageInput in) throws EOFException, FoodNetworkException {
		FoodMessage decodedMessage = FoodMessage.decode(in);
		if (decodedMessage instanceof FoodList){
			for(FoodItem i : ((FoodList)decodedMessage).getFoodItemList()){
				System.out.println(i.toString());
			}
		} else if (decodedMessage instanceof ErrorMessage) {
			System.out.println("\nError: " + ((ErrorMessage)decodedMessage).getErrorMessage());
		} else {
			System.err.println("\nUnexepected message" + decodedMessage.getRequest());
		}
	}

	/**
	 * Asks client repeatedly for a valid fat
	 * @return String containing valid fat
	 */
	private static String getFat() {
		boolean goodInput = false;
		String fat = "";
		while (!goodInput){
			goodInput = true;
			// Get the fat
			System.out.print("Fat> ");
			fat = scan.next();
			try {
				Double.parseDouble(fat);
			} catch (NumberFormatException e) {
				System.err.print("\nInvalid user input: " + fat);
				goodInput = false;
			}
		}
		return fat;
	}

	/**
	 * Asks client repeatedly for valid calories
	 * @return long containing valid calories
	 */
	private static long getCalories() {
		boolean goodInput = false;
		long calories = -1;
		while (!goodInput){
			goodInput = true;
			// Get the Calories
			System.out.print("Calories> ");
			String caloriesString = scan.next();
			try {
				calories = Long.valueOf(caloriesString);
			} catch (NumberFormatException e) {
				System.err.println("\nInvalid user input: " + caloriesString);
				goodInput = false;
			}
		}
		return calories;
	}

	/**
	 * Asks repeatedly for mealCode
	 * @return char containing valid mealCode
	 */
	private static char getMealCode() {
		// Get the Meal Type Code
		boolean goodInput = false;
		char mealCode = ' ';
		while (!goodInput){
			goodInput = true;
			System.out.print("Meal type (B, L, D, S)> ");
			String mealCodeString = scan.next();
			if (mealCodeString.length() != 1){
				System.err.println("\nInvalid user input: " + mealCodeString);
				goodInput = false;
			} else if (!mealCodeString.equals("B") && !mealCodeString.equals("L") 
					&& !mealCodeString.equals("D") && !mealCodeString.equals("S")) {
				System.err.println("\nInvalid user input: " + mealCodeString);
				goodInput = false;
			}
			if (goodInput) {
				mealCode = mealCodeString.charAt(0);
			}
		}
		return mealCode;
		
	}

	/**
	 * asks client repeatedly for name
	 * @return String containing valid name
	 */
	private static String getName() {
		// Get the name
		boolean goodInput = false;
		String name = "";
		while (!goodInput){
			goodInput = true;
			System.out.print("Name> ");
			name =  scan.next();
			if (name == null){
				goodInput = false;
				System.err.println("\nInvalid user input: null name");
			}
		}
		return name;
	}

	/**
	 * returns a long with the current timestamp
	 * @return long with the current timestamp
	 */
	private static long getTimestamp() {
		// Get the timestamp
		Date date = new Date();
		return date.getTime();
	}

	/**
	 * asks client repeatedly for (ADD|GET)
	 * @return String containing valid request
	 */
	private static String askRequest() {
		boolean goodInput = false;
		String request = "";
		while (!goodInput){
			goodInput = true;
			// Prompt the user for the type of message to send to the server (GET or ADD)
			System.out.print("\nRequest (ADD|GET)> ");
			request = scan.next();
			if (!request.equals("ADD") && !request.equals("GET")){
				goodInput = false;
				System.err.println("\nInvalid user input: " + request);
			}
		}
		return request;
	}
	
	
}
