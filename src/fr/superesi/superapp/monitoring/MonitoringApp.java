package fr.superesi.superapp.monitoring;

import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

import javax.swing.SwingUtilities;

import fr.superesi.superapp.Order;
import fr.superesi.superapp.order.OrderApp;

public class MonitoringApp {

	private ArrayList<Object> listOfOrders;
	private TCPServer server;
	private MonitoringAppMainFrame mainFrame;
	
	public MonitoringApp() {
		listOfOrders = new ArrayList<Object>();
		server = new TCPServer("localhost", 1024, listOfOrders);
		mainFrame = new MonitoringAppMainFrame();
		
		new Thread(server).start();
		
		new Thread(new Runnable() {

			@Override
			public void run() {
				synchronized(listOfOrders) {
					while(true) {
						try {
							while(listOfOrders.isEmpty()) listOfOrders.wait();
							
							ArrayList<Order> orders = mainFrame.getData();
							listOfOrders.forEach((order)-> orders.add((Order)order));
							listOfOrders.clear();
							listOfOrders.notifyAll();
							
							mainFrame.setData(orders);
							System.out.println("Added to main frame");
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}
			
		}).start();
		
		//new Thread(new Runnable())
	}

	public static void main(String[] args) {
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new MonitoringApp();
			}
		});

	}
	
	public void addOrder(Order orderToAdd) {
		mainFrame.data.add(orderToAdd);
	}

}
