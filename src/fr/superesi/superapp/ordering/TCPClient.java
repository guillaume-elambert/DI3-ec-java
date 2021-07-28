package fr.superesi.superapp.ordering;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import fr.superesi.superapp.Order;

/**
 * @brief Classe permettant de gérer un client TCP.
 * @author Guillaume ELAMBERT
 * @date 2021
 */
public class TCPClient {

	public static final String defaultHost = "localhost";	/**< Le nom par défaut du serveur TCP distant. */
	public static final int defaultPort = 1024;				/**< Le port par défaut  du serveur TCP distant. */
	public static final long defaultRetryTime = 10000;		/**< Le temps d'attente en millisecondes avant de retenter une connexion avec le serveur. */

	private String host;	/**< Le nom/ip du serveur TCP distant. */
	private int port;		/**< Le port du serveur TCP distant. */
	private Socket socket;	/**< La connexion au serveur TCP distant. */


	/**
	 * Constructeur par défaut
	 */
	public TCPClient() {
		this(defaultHost, defaultPort);
	}


	/**
	 * Constructeur de confort
	 *
	 * @param host Le nom/ip du serveur TCP distant.
	 * @param port Le port du serveur TCP distant.
	 */
	public TCPClient(String host, int port) {
		this.host = host;
		this.port = port;
		socket = null;
	}


	/**
	 * Méthode qui initialise la socket du client TCP.
	 * En cas d'échec, on tente une nouvelle initialisation après <code>defaultRetryTime</code> millisecondes.
	 */
	public void initSocket() {

		// Entrée : la socket a pas été initiée et n'est pas fermée
		//		=> on en créé une nouvelle
		if( socket != null && !socket.isClosed()) return;

		socket = new Socket();

		try {
			socket.connect(new InetSocketAddress(host, port));
			return;
		} catch (IOException e) {
			e.printStackTrace();
		}

		// On ne passe ici que lorsque'il y a eu une erreur.

		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// On retente la connexion avec le serveur dans defaultRetryTime millisecondes
		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				initSocket();
			}
		}, defaultRetryTime);
	}


	/**
	 * Méthode qui permet d'envoyer une commande au serveur TCP distant.
	 *
	 * @param orderToSend La commande à envoyer.
	 */
	public void sendOrder(Order orderToSend) {
		ArrayList<Order> listOfOrders = new ArrayList<>();
        listOfOrders.add(orderToSend);
        sendOrders(listOfOrders);
	}


	/**
	 * Méthode qui permet d'envoyer une liste de commandes au serveur TCP distant.
	 *
	 * @param orderToSend La liste de commandes à envoyer.
	 */
	public void sendOrders(ArrayList<Order> ordersToSend) {
		if( !this.getConnexionStatus() ) {
			System.err.println("TCPClient : can't send order because socket isn't connected to server.");
			return;
		}


        try {
			// get the output stream from the socket.
	        OutputStream outputStream = socket.getOutputStream();

	        // create an object output stream from the output stream so we can send an object through it
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);

	        objectOutputStream.writeObject(ordersToSend);
	        objectOutputStream.flush();
	        objectOutputStream.close();

	        System.out.println("Sent !");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Méthode qui retourne l'état de connexion du client TCP.
	 *
	 * @return L'état de connexion du client TCP.
	 */
	public boolean getConnexionStatus()
	{
		boolean status = false;

		if (this.socket == null || !this.socket.isConnected() || this.socket.isClosed()) return status;

		try {
			status = new InetSocketAddress(host, port).getAddress().isReachable(30);
		} catch (IOException e) {
			e.printStackTrace();
		}


		return status;
	}

	/**
	 * @return the host
	 */
	public String getHost() {
		return host;
	}

	/**
	 * @param host the host to set
	 */
	public void setHost(String host) {
		this.host = host;
	}

	/**
	 * @return the port
	 */
	public int getPort() {
		return port;
	}

	/**
	 * @param port the port to set
	 */
	public void setPort(int port) {
		this.port = port;
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
	 * @return the defaulthost
	 */
	public static String getDefaulthost() {
		return defaultHost;
	}

	/**
	 * @return the defaultport
	 */
	public static int getDefaultport() {
		return defaultPort;
	}

}