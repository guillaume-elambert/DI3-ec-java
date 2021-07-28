package fr.superesi.superapp.ordering;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import fr.superesi.superapp.Order;
import fr.superesi.superapp.ordering.gui.OrderingAppMainFrame;

/**
 * @brief La classe permettant de gérer l'application de commande.
 * @author Guillaume ELAMBERT
 * @date 2021
 */
public class OrderingApp implements ActionListener {

	private TCPClient client;						/**< Le client TCP. */
	private static OrderingAppMainFrame mainFrame;	/**< L'interface. */

	/**
	 * Constructeur par défaut
	 */
	public OrderingApp() {
		this(TCPClient.defaultHost, TCPClient.defaultPort);
	}
	
	/**
	 * Constructeur de confort.
	 * 
	 * @param host Le nom/ip du serveur TCP distant.
	 * @param port Le port du serveur TCP distant.
	 */
	public OrderingApp(String host, int port) {
		client = new TCPClient(host, port);
		mainFrame = new OrderingAppMainFrame();
	}


	/**
	 * Méthode principale.
	 * 
	 * @param args Non utilisé.
	 */
	public static void main(String[] args) {
		new OrderingApp().run();
	}
	
	/**
	 * Méthode de lancement.
	 */
	public void run() {
		client.initSocket();
		mainFrame.getOkButton().addActionListener(this);
	}

	/**
	 * Méthode qui vérifie si le nom du produit est bien formatté
	 *
	 * @param productName Le nom du produit à tester
	 * @return true si le nom du produit est valide, false si non
	 */
	public boolean checkProductName(String productName) {
		return productName.length() > 0;
	}


	/**
	 * Méthode qui vérifie que la quantité du produit est bonne.
	 *
	 * @param productQuantity La quantité quantité à vérifier.
	 * @return true si la quantité du produit est valide, false si non
	 */
	public boolean checkProductQuantity(String productQuantity) {

		// Si le champ ne comporte pas que des chiffres, on sort
		// On vérifie qu'il y a au moins un produit
		if(!productQuantity.matches("[0-9]+") || (Float.valueOf(productQuantity) <=0)) return false;

		return true;
	}


	/**
	 * Méthode qui vérifie que le prix du produit est bon.
	 *
	 * @param productPrice Le prix du produit à vérifier.
	 * @return true si le prix du produit est valide, false si non
	 */
	public boolean checkProductPrice(String productPrice) {

		// Si le champ ne correspond pas à un float on sort
		if(!productPrice.matches("([0-9]*[.,])?[0-9]+")) return false;

		productPrice = productPrice.replace(',', '.');

		// On vérifie qu'il y a au moins un produit
		if(Float.valueOf(productPrice) <=0) return false;

		return true;
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();

		//Entrée : l'action provient d'un clic sur le bouton de validation du formulaire de saisie du produit
		if(src.equals(mainFrame.getOkButton())) {

			String name = mainFrame.getProductTextField().getText();

			// Entrée : l'un des champs de saisie a mal été saisi
			//		=> On sort
			if ( 	!checkProductName(name)
				|| 	!checkProductQuantity(mainFrame.getQuantityTextField().getText())
				||	!checkProductPrice(mainFrame.getUnitPriceTextField().getText())
			   ) {
				return;
			}

			// On récupère les valeurs des champs de saisie
			int qte = Integer.valueOf(mainFrame.getQuantityTextField().getText());
			float price = Float.valueOf(mainFrame.getUnitPriceTextField().getText().replace(',', '.'));

			// Comme les champs ont été correctement saisis on envoie la commande
			client.sendOrder(new Order(name, qte, price));

			// On reconnect le client avec le serveur
			client.initSocket();
		}

	}

}
