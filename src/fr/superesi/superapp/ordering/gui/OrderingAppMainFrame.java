package fr.superesi.superapp.ordering.gui;

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
 * Class of the main (and only) frame of the ordering application.
 *
 * @author C. Esswein
 * @author Guillaume ELAMBERT
 */
@SuppressWarnings("serial")
public class OrderingAppMainFrame extends JFrame implements ActionListener {
	/**
	 * Constant defining the app name.
	 */
	private static final String APP_NAME = "Order app",
			PRODUCT = "Product:",
			QUANTITY = "Quantity:",
			UNIT_PRICE = "Unit price (euros): ",
			OK = "Confirm Order",
			CANCEL="Cancel Order",
			DEFAULT_UNIT_PRICE = "0",
			DEFAULT_QUANTITY = "1";

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

		cancelButton.addActionListener(this);

		// Final set up
		pack();
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}

	private void setDefaultValues() {
		productTextField.setText(null);
		unitPriceTextField.setText(DEFAULT_UNIT_PRICE);
		quantityTextField.setText(DEFAULT_QUANTITY);
	}

	/**
	 * One and only constructor of the main frame, called by the main method.
	 */
	public OrderingAppMainFrame() {
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
			@Override
			public void run() {
				new OrderingAppMainFrame();
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();

		if(src.equals(cancelButton)) {
			setDefaultValues();
		}

	}

	/**
	 * @return the productLabel
	 */
	public JLabel getProductLabel() {
		return productLabel;
	}

	/**
	 * @param productLabel the productLabel to set
	 */
	public void setProductLabel(JLabel productLabel) {
		this.productLabel = productLabel;
	}

	/**
	 * @return the quantityLabel
	 */
	public JLabel getQuantityLabel() {
		return quantityLabel;
	}

	/**
	 * @param quantityLabel the quantityLabel to set
	 */
	public void setQuantityLabel(JLabel quantityLabel) {
		this.quantityLabel = quantityLabel;
	}

	/**
	 * @return the unitPriceLabel
	 */
	public JLabel getUnitPriceLabel() {
		return unitPriceLabel;
	}

	/**
	 * @param unitPriceLabel the unitPriceLabel to set
	 */
	public void setUnitPriceLabel(JLabel unitPriceLabel) {
		this.unitPriceLabel = unitPriceLabel;
	}

	/**
	 * @return the productTextField
	 */
	public JTextField getProductTextField() {
		return productTextField;
	}

	/**
	 * @param productTextField the productTextField to set
	 */
	public void setProductTextField(JTextField productTextField) {
		this.productTextField = productTextField;
	}

	/**
	 * @return the quantityTextField
	 */
	public JTextField getQuantityTextField() {
		return quantityTextField;
	}

	/**
	 * @param quantityTextField the quantityTextField to set
	 */
	public void setQuantityTextField(JTextField quantityTextField) {
		this.quantityTextField = quantityTextField;
	}

	/**
	 * @return the unitPriceTextField
	 */
	public JTextField getUnitPriceTextField() {
		return unitPriceTextField;
	}

	/**
	 * @param unitPriceTextField the unitPriceTextField to set
	 */
	public void setUnitPriceTextField(JTextField unitPriceTextField) {
		this.unitPriceTextField = unitPriceTextField;
	}

	/**
	 * @return the okButton
	 */
	public JButton getOkButton() {
		return okButton;
	}

	/**
	 * @param okButton the okButton to set
	 */
	public void setOkButton(JButton okButton) {
		this.okButton = okButton;
	}

	/**
	 * @return the cancelButton
	 */
	public JButton getCancelButton() {
		return cancelButton;
	}

	/**
	 * @param cancelButton the cancelButton to set
	 */
	public void setCancelButton(JButton cancelButton) {
		this.cancelButton = cancelButton;
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
	 * @return the appName
	 */
	public static String getAppName() {
		return APP_NAME;
	}

	/**
	 * @return the product
	 */
	public static String getProduct() {
		return PRODUCT;
	}

	/**
	 * @return the quantity
	 */
	public static String getQuantity() {
		return QUANTITY;
	}

	/**
	 * @return the unitPrice
	 */
	public static String getUnitPrice() {
		return UNIT_PRICE;
	}

	/**
	 * @return the ok
	 */
	public static String getOk() {
		return OK;
	}

	/**
	 * @return the cancel
	 */
	public static String getCancel() {
		return CANCEL;
	}

	/**
	 * @return the defaultUnitPrice
	 */
	public static String getDefaultUnitPrice() {
		return DEFAULT_UNIT_PRICE;
	}

	/**
	 * @return the defaultQuantity
	 */
	public static String getDefaultQuantity() {
		return DEFAULT_QUANTITY;
	}


}
