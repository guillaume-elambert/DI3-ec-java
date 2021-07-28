package fr.superesi.superapp;

import java.io.Serializable;

/**
 * Main data class representing an order (e.g. 2 sodas at 5euros each or 4 wheels at 35euros each or 1 dress at 45 euros)
 *
 * @author C. Esswein
 */
@SuppressWarnings("serial")
public class Order implements Serializable {

	private String product;
	private int quantity;
	private float unitPrice;

	/**
	 * @param product
	 * @param quantity
	 * @param unitPrice
	 */
	public Order(String product, int quantity, float unitPrice) {
		super();
		this.product = product;
		this.quantity = quantity;
		this.unitPrice = unitPrice;
	}
	/**
	 * @return the product
	 */
	public String getProduct() {
		return product;
	}
	/**
	 * @param product the product to set
	 */
	public void setProduct(String product) {
		this.product = product;
	}
	/**
	 * @return the quantity
	 */
	public int getQuantity() {
		return quantity;
	}
	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	/**
	 * @return the unitPrice
	 */
	public float getUnitPrice() {
		return unitPrice;
	}
	/**
	 * @param unitPrice the unitPrice to set
	 */
	public void setUnitPrice(float unitPrice) {
		this.unitPrice = unitPrice;
	}

	//TODO to be continued if needed

}
