package fr.superesi.superapp;
import fr.superesi.superapp.monitoring.MonitoringApp;
import fr.superesi.superapp.ordering.OrderingApp;

/**
 * @brief Classe ayant pour unique but de lancer les applications de commande et de monitoring.
 * @author Guillaume ELAMBERT
 * @date 2021
 */
public class CompleteApp {

	/**
	 * Classe principale.
	 * 
	 * @param args Non utilisé.
	 */
	public static void main(String[] args) {
		MonitoringApp.main(args);
		OrderingApp.main(args);
	}

}
