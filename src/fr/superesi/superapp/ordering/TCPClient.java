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

	private String host;					/**< Le nom/ip du serveur TCP distant. */
	private int port;						/**< Le port du serveur TCP distant. */
	private Socket socket;					/**< La connexion au serveur TCP distant. */
	private ArrayList<Order> unsentOrders;	/**< Liste des commandes qui n'ont pa pu être envoyées. */
	private boolean resendScheduled;		/**< Définit si l'on à prévu de renvoyé les commandes dont l'envoi a échoué. */


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
		unsentOrders = new ArrayList<>();
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

		System.out.println("Scheduling reconnetion in "+defaultRetryTime+" milliseconds.");
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
	 * @param ordersToSend La liste de commandes à envoyer.
	 */
	public void sendOrders(ArrayList<Order> ordersToSend) {
		
		// Entrée : la socket n'est pas connectée au serveur
		//		=> on reprogramme l'envoi
		if( !getConnexionStatus() ) {
			System.err.println("TCPClient : can't send order because socket isn't connected to server.");
			unsentOrders.addAll(ordersToSend);
			sheduleSendUnsentOrders();
			return;
		}


        try {
			// On récupère l'output stream de la socket connectée
	        OutputStream outputStream = socket.getOutputStream();

	        // On créé un objet ObjectOutputStream qui va contenir l'ensemble de nos commandes
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
			
			// On envoie les commandes au server
	        objectOutputStream.writeObject(ordersToSend);
	        objectOutputStream.flush();
	        objectOutputStream.close();

	        System.out.println("TCPClient : Orders sent :");
	        ordersToSend.forEach((order) -> System.out.println(order.toString()));

		} catch (IOException e) {
			e.printStackTrace();
			
			// On reprogramme l'envoi
			unsentOrders.addAll(ordersToSend);
			sheduleSendUnsentOrders();
		}
	}
	
	
	/**
	 * Méthode qui permet de programmer l'envoi des commandes n'ayant pas pu être 
	 * envoyées précédemment.
	 */
	public void sheduleSendUnsentOrders(){
		
		// Entrée : On a déjà programmé le fait de renvoyer les commandes dont l'envoi à échoué
		//		=> On sort.
		if(resendScheduled) {
			System.out.println("TCPClient : Already scheduled.");
			return;
		}
		
		// Entrée : Renvoi programmé alors que la liste est vide
		//		=> On sort.
		if( unsentOrders.isEmpty()) {
			System.out.println("TCPClient : Unset orders list is empty, unscheduling.");
			resendScheduled = false;
			return;
		}
		
		resendScheduled = true;
		System.out.println("TCPClient : Sheduling to resend unsent orders.");
		
		
		// Tâche programmée à éxecuter après defaultRetryTime millisecondes
		new Timer().schedule(new TimerTask() {

			@Override
			public void run() {
				resendScheduled = false;	
				
				// Entrée : Le client TCP n'est pas connecté au serveur
				//		=> On reprogramme l'envoi
				if (!getConnexionStatus()) {
					System.err.println("TCPClient : can't send order because socket isn't connected to server.");
					sheduleSendUnsentOrders();
					return;
				}
				
				
				System.out.println("TCPClient : Running scheduling");
				sendOrders(unsentOrders);
			}
			
		}, defaultRetryTime);
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
