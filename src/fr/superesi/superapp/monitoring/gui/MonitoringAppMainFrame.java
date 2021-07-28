package fr.superesi.superapp.monitoring.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

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

	private JButton razButton = new JButton("reset table");

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
		add(buttonPanel, BorderLayout.PAGE_END);

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
		this.data.clear();
		tableModel.resetTable();
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
		if(e.getSource().equals(razButton)) {
			resetData();
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
