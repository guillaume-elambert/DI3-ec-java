package fr.superesi.superapp.monitoring;

import java.util.ArrayList;

import fr.superesi.superapp.Order;
import fr.superesi.superapp.monitoring.gui.MonitoringAppMainFrame;

/**
 * @brief La classe permettant de gérer l'application de monitoring.
 * @author Guillaume ELAMBERT
 * @date 2021
 */
public class MonitoringApp {

	private TCPServer server;					/**< Le serveur TCP.*/
	private ArrayList<Order> listOfOrders;		/**< Liste des commandes reçues par le serveur TCP. */
	private MonitoringAppMainFrame mainFrame;	/**< L'interface. */

	/**
	 * Constructeur par défaut
	 */
	public MonitoringApp() {
		this(TCPServer.defaultPort, new ArrayList<>());
	}
	
	/**
	 * Constructeur de confort.
	 * 
	 * @param port Le port sur lequel on veut créé le serveur TCP.
	 * @param listOfOrders La liste où seront stockées les commandes reçues par le serveur TCP.
	 */
	@SuppressWarnings("unchecked")
	public MonitoringApp(int port, ArrayList<Order> listOfOrders) {
		this.listOfOrders = listOfOrders;
		server = new TCPServer(port, (ArrayList<Object>)(ArrayList<?>)listOfOrders);
		mainFrame = new MonitoringAppMainFrame();
	}

	/**
	 * Méthode principale.
	 *
	 * @param args Non utilisé
	 */
	public static void main(String[] args) {
		new MonitoringApp().run();
	}


	/**
	 * Méthode qui permet d'ajouter une commande à la liste des commandes existante.
	 *
	 * @param orderToAdd La commande à ajouter.
	 * @return true si l'ajout s'est effectué correctement, false sinon.
	 */
	public boolean addOrderMainFrame(Order orderToAdd) {
		ArrayList<Order> orders = new ArrayList<>();
		if(!orders.add(orderToAdd)) return false;

		return addOrdersMainFrame(orders);
	}


	/**
	 * Méthode qui permet d'ajouter une liste de commandes à la liste des commandes existante.
	 *
	 * @param ordersToAdd La liste de commandes à ajouter.
	 * @return true si l'ajout s'est effectué correctement, false sinon.
	 */
	public boolean addOrdersMainFrame(ArrayList<Order> ordersToAdd) {
		ArrayList<Order> orders = mainFrame.getData();
		
		if(!orders.addAll(ordersToAdd)) return false;
		mainFrame.setData(orders);

		return true;
	}


	/**
	 * Méthode de lancement.
	 */
	public void run() {

		// On lance le serveur TCP.
		new Thread(server).start();

		// On créé un thread qui partage le tableau des commandes avec le serveur TCP
		// de sorte que l'on puisse récupérer les commandes reçues.
		new Thread(new Runnable() {


			/**
			 * Méthode de lancement.
			 */
			@Override
			public void run() {
				// On synchronisation la variable listOfOrders partagées.
				synchronized(listOfOrders) {
					while(true) {
						try {

							// Tant que la liste de commandes est vide, on attend.
							while(listOfOrders.isEmpty()) listOfOrders.wait();

							// On ajoute l'ensemble des commande dans le tableau.
							addOrdersMainFrame(listOfOrders);
							listOfOrders.clear();
							
							// Entrée : on visualise la vue triée par produits
							//		=> On met à jour la vue.
							if(!mainFrame.getTable().getModel().equals(mainFrame.getTableModel())) {
								mainFrame.setOrderedProductTable();
							}

							// On libère le tableau.
							listOfOrders.notifyAll();

						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}

		}).start();

	}

}
