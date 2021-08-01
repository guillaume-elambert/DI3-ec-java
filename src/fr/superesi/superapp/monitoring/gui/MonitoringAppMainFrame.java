package fr.superesi.superapp.monitoring.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;

import fr.superesi.superapp.Order;
import fr.superesi.superapp.monitoring.model.OrdersTableModel;

/**
 * Class of the main (and only) frame of the monitoring application.
 *
 * @author C. Esswein
 * @author Guillaume ELAMBERT
 */
@SuppressWarnings("serial")
public class MonitoringAppMainFrame extends JFrame implements ActionListener {

	private JTable table;
	private OrdersTableModel tableModel;
	private JPanel mainPanel, buttonPanel;
	
	private static final String[] TOGGLE_BUTTON_TEXT = {"organize by products", "organize by orders"};

	private JButton razButton = new JButton("reset table");
	private JButton toggleButton = new JButton(TOGGLE_BUTTON_TEXT[0]);

	private ArrayList<Order> data = new ArrayList<>();

	public MonitoringAppMainFrame() {
		super("Monitoring app");
		fillInitTableData(); // COMMENT THIS LINE TO REMOVE STUB DATA
		tableModel = new OrdersTableModel(data);
		table = new JTable(tableModel);
		JScrollPane scrollPane = new JScrollPane(table);

		mainPanel = new JPanel();
		mainPanel.add(scrollPane);
		add(mainPanel, BorderLayout.PAGE_START);

		buttonPanel = new JPanel();
		buttonPanel.add(razButton);
		buttonPanel.add(toggleButton);
		add(buttonPanel, BorderLayout.PAGE_END);

		toggleButton.addActionListener(this);
		razButton.addActionListener(this);
		table.setAutoCreateRowSorter(true);

		pack();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);

	}

	/**
	 * Supprime les données de la table des commandes.
	 */
	public void resetData() {
		if(data.isEmpty()) return;
		
		data.clear();
		tableModel.resetTable();
	}
	
	
	/**
	 * Méthode qui remet le model par défaut (<code>tableModel</code>).
	 */
	public void resetTableModel() {
		table.setModel(tableModel);
		toggleButton.setText(TOGGLE_BUTTON_TEXT[0]);
	}

	/**
	 * Stub method filling the table with some data sample.
	 */
	private void fillInitTableData() {
		data.add(new Order("product3",2,1.2f));
		data.add(new Order("product2",3,1.6f));
		data.add(new Order("product4",1,1.6f));
		data.add(new Order("product1",4,1.1f));
	}

	/**
	 * Just the main of the monitoring app...
	 *
	 * @param args
	 *            - unused main arguments
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {

				new MonitoringAppMainFrame();


			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		//Entrée : on a cliqué sur le bouton de réinitialisation
		if(e.getSource().equals(razButton)) {
			resetData();
			resetTableModel();
		}
		// Entrée : on à cliquer sur le bouton pour changer de vue 
		else if(e.getSource().equals(toggleButton)) {
			
			// Entrée : on souhaite avoir la vue normale (table par commandes)
			//		=> On reinitialise le modèle de table et on sort.
			if(!table.getModel().equals(tableModel)) {
				resetTableModel();
				return;
			}
			
			setOrderedProductTable();			
		}
	}
	
	
	/**
	 * Méthode qui définit le contenu de la table comme étant le regroupement 
	 * des produits ayant le même nom. Ainsi le prix unitaire affiché est la 
	 * moyenne des prix unitaire des commandes passées pour ce produit et la 
	 * quantité est la quantité totale commandée.
	 */
	public void setOrderedProductTable(){
		
		// On regroupe les élément du tableau ayant le même nom de produit
		Map<String, List<Order>> orderedByProduct = data.stream().collect(Collectors.groupingBy(order -> order.getProduct()));
		ArrayList<Order> productOrderedList = new ArrayList<>();
		
		// Initialisation des variables
		int productCounter = 0;
		String productName = "";
		float averageProductPrice, currentOrderUnitPrice;
		int totalProductQuantity, currentOrderQuantity;
		
		// On parcourt l'ensemble des produits
		for(Entry<String, List<Order>> entry : orderedByProduct.entrySet()) {
			
			++productCounter;
			
			// On récupère le nom du produit courrant et on initialise le prix et la qte
			productName = entry.getKey();
			averageProductPrice = 0;
			totalProductQuantity = 0;
			
			// On parcourt l'ensemble des commandes d'un produit
			for(Order order : entry.getValue()) {
				
				// On récupère la quantité et le prix unitaire de la commande parcourue
				currentOrderUnitPrice = order.getUnitPrice();
				currentOrderQuantity = order.getQuantity();
				
				// On fait la moyenne du prix unitaire
				averageProductPrice = ( averageProductPrice*totalProductQuantity + currentOrderUnitPrice*currentOrderQuantity ) / ( totalProductQuantity+currentOrderQuantity );
				totalProductQuantity +=  currentOrderQuantity;
				
			}
			
			// On ajoute une "nouvell commande" qui contient la moyenne des prix, et la quantité totale commandée. 
			if(!productOrderedList.add(new Order(productCounter, productName, totalProductQuantity, averageProductPrice))) break;
		}
		
		// Si rien n'a été trié, on ne change pas la vue
		if(!productOrderedList.isEmpty()) {
			// On change la vue (basée sur le nouveau tableau).
			table.setModel(new OrdersTableModel(productOrderedList));
			toggleButton.setText(TOGGLE_BUTTON_TEXT[1]);
		}
	}

	/**
	 * @return the table
	 */
	public JTable getTable() {
		return table;
	}

	/**
	 * @param table the table to set
	 */
	public void setTable(JTable table) {
		this.table = table;
	}

	/**
	 * @return the tableModel
	 */
	public OrdersTableModel getTableModel() {
		return tableModel;
	}

	/**
	 * @param tableModel the tableModel to set
	 */
	public void setTableModel(OrdersTableModel tableModel) {
		this.tableModel = tableModel;
	}

	/**
	 * @return the mainPanel
	 */
	public JPanel getMainPanel() {
		return mainPanel;
	}

	/**
	 * @param mainPanel the mainPanel to set
	 */
	public void setMainPanel(JPanel mainPanel) {
		this.mainPanel = mainPanel;
	}

	/**
	 * @return the buttonPanel
	 */
	public JPanel getButtonPanel() {
		return buttonPanel;
	}

	/**
	 * @param buttonPanel the buttonPanel to set
	 */
	public void setButtonPanel(JPanel buttonPanel) {
		this.buttonPanel = buttonPanel;
	}

	/**
	 * @return the razButton
	 */
	public JButton getRazButton() {
		return razButton;
	}

	/**
	 * @param razButton the razButton to set
	 */
	public void setRazButton(JButton razButton) {
		this.razButton = razButton;
	}

	/**
	 * @return the data
	 */
	public ArrayList<Order> getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(ArrayList<Order> data) {
		this.data = data;
		this.tableModel.fireTableDataChanged();
	}

}
