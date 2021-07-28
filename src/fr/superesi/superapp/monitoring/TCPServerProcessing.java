package fr.superesi.superapp.monitoring;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;

/**
 * @brief Classe permettant de gérer le traitement d'une connexion TCP entrante sur un serveur. 
 * @author Guillaume ELAMBERT
 * @date 2021
 */
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
	 * @param receivedData Le contenu reçu par le server.
	 */
	TCPServerProcessing(Socket socket, ArrayList<Object> receivedData){
		this.socket = socket;
		this.receivedData = receivedData;
	}


	/**
	 * Méthode de lancement du thread.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void run() {
		System.out.println("\nConnection from " + socket + "!");


		try {

			// On récupère l'input stream de la socket connectée
			InputStream inputStream;
			inputStream = socket.getInputStream();

			// On créé un DataInputStream pour lire ce qui à été reçu
			ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);

			// On lit la list des objets reçus
			Object receivedObject = objectInputStream.readObject();
			ArrayList<Object> listOfObjects = new ArrayList<>();

			// Entrée : l'objet reçu n'est pas de la bonne classe.
			//		=> On sort.
			if (!listOfObjects.getClass().isInstance(receivedObject)) {
				System.err.println("TCPServerProcessing : Expects an object of the \"" + listOfObjects.getClass().getSimpleName()
						+ "\" class but the received object is of the \"" + receivedObject.getClass().getSimpleName()
						+ "\" class.");

				objectInputStream.close();
				socket.close();
				return;
			}

			listOfObjects = (ArrayList<Object>) receivedObject;
			System.out.println("Received [" + listOfObjects.size() + "] objects from: " + socket);

			objectInputStream.close();
			socket.close();

			// On utilise la synchronisation sur le tableau des objets reçus.
			synchronized (receivedData) {
				receivedData.addAll(listOfObjects);
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
