/**
 * 
 */
package fr.superesi.superapp.monitoring;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import fr.superesi.superapp.Order;

/**
 * @brief La classe permettant de gérer le serveur TCP.
 * @author Guillaume ELAMBERT
 * @date 2021
 */
class TCPServer implements Runnable {
	
	public static final String defaultHost = "localhost";	/**< Le nom par défaut du serveur TCP. */
	public static final int defaultPort = 1024;				/**< Le port par défaut  du serveur TCP. */
	
	private String host;						/**< Le nom/ip du serveur TCP. */
	private int port;							/**<  */ 
	private ServerSocket serverSocket; 			/**< La socket server */
	private Socket socket; 						/**< La socket de connexion */
	private InetSocketAddress socketAddress; 	/**< L'addresse */
	private ArrayList<Object> receivedData;		/**< La liste des objets reçus */

	/**
	 * Constructeur par défaut
	 */
	TCPServer() {
		this(defaultHost, defaultPort, new ArrayList<Object>());
	}
	
	/**
	 * Constructeur de confort.
	 * 
	 * @param host Le nom/ip du serveur TCP.
	 * @param port Le port du server TCP.
	 * @param receivedData La liste des objets reçus.
	 */
	TCPServer(String host, int port, ArrayList<Object> receivedData){
		serverSocket = null;
		socket = null;
		socketAddress = new InetSocketAddress(host, port);
		this.receivedData = receivedData;
	}

	/** The main method for threading. */
	public void run() {
		try {
			serverSocket = new ServerSocket(socketAddress.getPort());
			System.out.println("TCPServer launched ...");
			
			// Toujours vrai, permet de tromper Java quant à la boucle infinie
			while(socket == null || socket.isClosed()) {
				//socket = serverSocket.accept();	
				new Thread(new TCPServerProcessing(serverSocket.accept(), receivedData)).start();
			}
			
			serverSocket.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @return the serverSocket
	 */
	public ServerSocket getServerSocket() {
		return serverSocket;
	}

	/**
	 * @param serverSocket the serverSocket to set
	 */
	public void setServerSocket(ServerSocket serverSocket) {
		this.serverSocket = serverSocket;
	}

	/**
	 * @return the socket
	 */
	public Socket getSocket() {
		return socket;
	}

	/**
	 * @param socket the socket to set
	 */
	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	/**
	 * @return the socketAddress
	 */
	public InetSocketAddress getSocketAddress() {
		return socketAddress;
	}

	/**
	 * @param socketAddress the socketAddress to set
	 */
	public void setSocketAddress(InetSocketAddress socketAddress) {
		this.socketAddress = socketAddress;
	}

	/**
	 * @return the receivedData
	 */
	public ArrayList<Object> getReceivedData() {
		return receivedData;
	}

	/**
	 * @param receivedData the receivedData to set
	 */
	public void setReceivedData(ArrayList<Object> receivedData) {
		this.receivedData = receivedData;
	}

}
