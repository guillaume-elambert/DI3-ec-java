package fr.superesi.superapp.monitoring;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;

import fr.superesi.superapp.Order;

public class TCPServerProcessing implements Runnable {

	private Socket socket;					/**< La socket de connexion. */
	private ArrayList<Object> receivedData;	/**< La liste des objets reçus */
	
	/**
	 * Constructeur par défaut.
	 */
	TCPServerProcessing(){
		socket = null;
	}
	
	/**
	 * Constructeur de confort
	 * 
	 * @param socket La socket de connexion.
	 */
	TCPServerProcessing(Socket socket, ArrayList<Object> receivedData){
		this.socket = socket;
		this.receivedData = receivedData;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public void run() {
		System.out.println("Connection from " + socket + "!");

		
		try {
			
			// get the input stream from the connected socket
			InputStream inputStream;
			inputStream = socket.getInputStream();

			// create a DataInputStream so we can read data from it.
			ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
	
			// read the list of messages from the socket
			Object receivedObject = objectInputStream.readObject();
			ArrayList<Order> listOfOrders = new ArrayList<Order>();
	
			if (!listOfOrders.getClass().isInstance(receivedObject)) {
				System.err.println("TCPServer : Expects an object of the \"" + listOfOrders.getClass().getSimpleName()
						+ "\" class but the received object is of the \"" + receivedObject.getClass().getSimpleName()
						+ "\" class.");
				objectInputStream.close();
				socket.close();
				return;
			}
	
			listOfOrders = (ArrayList<Order>) receivedObject;
			System.out.println("Received [" + listOfOrders.size() + "] orders from: " + socket);
	
			// print out the text of every message
			System.out.println("All orders:");
			listOfOrders.forEach((order) -> System.out
					.println(order.getProduct() + "\t" + order.getQuantity() + "\t" + order.getUnitPrice() + "€"));
	
			objectInputStream.close();
			socket.close();
	
			synchronized (receivedData) {
				receivedData.addAll(listOfOrders);
				receivedData.notifyAll();
			}
			
			socket.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

}
