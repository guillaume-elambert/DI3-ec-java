package fr.superesi.superapp.order.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/**
 * Main Frame class for the order app.
 * 
 * @author C. Esswein
 */
@SuppressWarnings("serial")
public class OrderAppMainFrame extends JFrame implements ActionListener{
	/**
	 * Constant defining the app name.
	 */
	private static final String APP_NAME = "Order app", PRODUCT = "Product:", QUANTITY = "Quantity:",
			UNIT_PRICE = "Unit price (euros): ", OK = "Confirm Order", CANCEL="Cancel Order",
			DEFAULT_UNIT_PRICE = "0", DEFAULT_QUANTITY = "1";

	private JLabel productLabel, quantityLabel, unitPriceLabel;
	private JTextField productTextField, quantityTextField, unitPriceTextField;
	private JButton okButton, cancelButton;
	private JPanel mainPanel, buttonPanel;

	/**
	 * Creates GUI elements and set up the layout ; it then displays the main frame.
	 */
	private void initGUI() {

		// First part : input fields and labels
		productLabel = new JLabel(PRODUCT);
		productTextField = new JTextField(25);
		unitPriceLabel = new JLabel(UNIT_PRICE);
		unitPriceTextField = new JTextField(DEFAULT_UNIT_PRICE);
		quantityLabel = new JLabel(QUANTITY);
		quantityTextField = new JTextField(DEFAULT_QUANTITY);
		mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(6, 1, 4, 0));
		mainPanel.add(productLabel);
		mainPanel.add(productTextField);
		mainPanel.add(unitPriceLabel);
		mainPanel.add(unitPriceTextField);
		mainPanel.add(quantityLabel);
		mainPanel.add(quantityTextField);
		add(mainPanel, BorderLayout.PAGE_START);
		
		// Second part : buttons
		buttonPanel = new JPanel();
		okButton = new JButton(OK);
		cancelButton = new JButton(CANCEL);
		buttonPanel.add(okButton);
		buttonPanel.add(cancelButton);
		add(buttonPanel, BorderLayout.PAGE_END);
		
		// Final set up
		pack();
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	/**
	 * One and only constructor of the main frame, called by the main method.
	 */
	public OrderAppMainFrame() {
		super(APP_NAME);
		initGUI();
	}

	/**
	 * Just the main of the bar app...
	 * 
	 * @param args
	 *            - unused main arguments
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new OrderAppMainFrame();
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO to be continued...
		
	}

}
