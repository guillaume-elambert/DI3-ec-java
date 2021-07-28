/**
 *
 */
package fr.superesi.superapp.monitoring;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @brief La classe permettant de gérer le serveur TCP.
 * @author Guillaume ELAMBERT
 * @date 2021
 */
class TCPServer implements Runnable {

	public static final int defaultPort = 1024;			/**< Le port par défaut  du serveur TCP. */
	public static final long defaultRetryTime = 10000;	/**< Le temps d'attente en millisecondes avant de retenter la création du serveur. */

	private int port;							/**< Le port du server. */
	private ServerSocket serverSocket; 			/**< La socket server. */
	private ArrayList<Object> receivedData;		/**< La liste des objets reçus. */

	/**
	 * Constructeur par défaut
	 */
	TCPServer() {
		this(defaultPort, new ArrayList<>());
	}

	/**
	 * Constructeur de confort.
	 *
	 * @param host Le nom/ip du serveur TCP.
	 * @param port Le port du server TCP.
	 * @param receivedData La liste des objets reçus.
	 */
	TCPServer(int port, ArrayList<Object> receivedData){
		this.port = port;
		serverSocket = null;
		this.receivedData = receivedData;
	}


	/**
	 * Méthode qui intialise la socket server.
	 * En cas d'échec, on tente une nouvelle initialisation après <code>defaultRetryTime</code> millisecondes.
	 */
	public void initSocket() {

		// Entrée : la socket a été initiée et n'est pas fermée.
		//		=> On sort.
		if(serverSocket != null && serverSocket.isClosed()) return;


		try {
			serverSocket = new ServerSocket(port);
			System.out.println("TCPServer launched ...");
			return;
		} catch (IOException e) {
			e.printStackTrace();
		}

		// On ne passe ici que lorsque'il y a eu une erreur.

		try {
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// On retente la création du serveur dans defaultRetryTime millisecondes.
		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				initSocket();
			}
		}, defaultRetryTime);

	}

	/**
	 * Méthode de lancement du thread.
	 */
	@Override
	public void run() {
		try {
			initSocket();

			// Toujours vrai, permet de tromper Java quant à la boucle infinie
			while(true) {

				// On lance un thread pour traiter la connexion (on évite le mode bloquant).
				new Thread(new TCPServerProcessing(serverSocket.accept(), receivedData)).start();
			}

			//serverSocket.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
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

	/**
	 * @return the defaultport
	 */
	public static int getDefaultport() {
		return defaultPort;
	}

	/**
	 * @return the defaultretrytime
	 */
	public static long getDefaultretrytime() {
		return defaultRetryTime;
	}

}
