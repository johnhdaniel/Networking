package foodnetwork.client;

import java.net.Socket;
import java.net.SocketException;
import java.util.Date;
import java.util.Scanner;

import foodnetwork.serialization.AddFood;
import foodnetwork.serialization.ErrorMessage;
import foodnetwork.serialization.FoodItem;
import foodnetwork.serialization.FoodList;
import foodnetwork.serialization.FoodMessage;
import foodnetwork.serialization.FoodNetworkException;
import foodnetwork.serialization.GetFood;
import foodnetwork.serialization.MealType;
import foodnetwork.serialization.MessageInput;
import foodnetwork.serialization.MessageOutput;

//public class TCPEchoClient {
//
//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//
//	}
//
//}
import java.net.*; // for Socket
import java.io.*; // for lOException and Input/OutputStream
import java.io.IOException;

public class Client {

	public static void main(String[] args) throws IOException {
		// Test for correct # of args
		if (args.length != 2){ 
			throw new IllegalArgumentException("Parameter(s): <Server> [<Port>]");
		}
		
		try {
		
			String server = args[0]; // Server name or IP address
			int servPort = Integer.parseInt(args[1]); // Port #
			
			// Create socket that is connected to server on specified port
			Socket socket = new Socket(server, servPort);
			
			if (!socket.isConnected()){
				System.err.println("Unable to communicate: Socket is not connected");
				System.exit(-1);
			}
			
			MessageInput in   = new MessageInput(socket.getInputStream());
			MessageOutput out = new MessageOutput(socket.getOutputStream());
			
			Scanner scan = new Scanner(System.in);
			scan.useDelimiter("\n");			
			
			while(true) {
				boolean goodInput = true;
				FoodMessage foodMessage = null;
				// Prompt the user for the type of message to send to the server (GET or ADD)
				System.out.print("Request (ADD|GET)> ");
				
				// Get the timestamp
				Date date = new Date();
				long timestamp = date.getTime();
				
				String request = scan.next();
				if (request.equals("ADD")){
					// Get the name
					System.out.print("Name> ");
					String name = scan.next();
					
					// Get the Meal Type Code
					System.out.print("Meal type (B, L, D, S)> ");
					String mealCodeString = scan.next();
					if (mealCodeString.length() != 1){
						System.err.println("Invalid user input: " + mealCodeString);
						goodInput = false;
					}
					char mealCode = mealCodeString.charAt(0);
					System.out.println(mealCodeString.length());
					
					// Get the Calories
					System.out.print("Calories> ");
					long calories = scan.nextLong();
					
					// Get the fat
					System.out.print("Fat> ");
					String fat = scan.next();
					
					// Print the timestamp
					System.out.print("Msg TS=" + timestamp + " - FoodItem");
					
					// Create a food item
					FoodItem foodItem = new FoodItem(name, MealType.getMealType(mealCode), calories, fat);
					if (goodInput){
						// Create an Add food message
						foodMessage = new AddFood(timestamp, foodItem);
					}
				} else if (request.equals("GET")){
					// Print the timestamp
					System.out.println("Msg TS=" + timestamp + " - FoodItem");
					
					// Create a Get food message
					foodMessage = new GetFood(timestamp);
				} else {
					System.err.println("Invalid user input: " + request);
					goodInput = false;
				}
				if (goodInput){
					foodMessage.encode(out);
					FoodMessage decodedMessage = FoodMessage.decode(in);
					if (decodedMessage instanceof FoodList){
						for(FoodItem i : ((FoodList)decodedMessage).getFoodItemList()){
							System.out.println(i.toString());
						}
					} else if (decodedMessage instanceof ErrorMessage) {
						System.out.println("Error: " + ((ErrorMessage)decodedMessage).getErrorMessage());
					} else {
						System.err.println("Unexepected message" + decodedMessage.getRequest());
					}
					
					System.out.print("\nContinue (y/n)>");
					String next = scan.next();
					if (next.equals("n")){
						socket.close();
						System.exit(0);
					}
				}
			}
			
			
		} catch (FoodNetworkException e) {
			System.err.println("Invalid message: " + e.getMessage());
			System.exit(-1);
		}
	}
}
