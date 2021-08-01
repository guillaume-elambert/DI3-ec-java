package fr.superesi.superapp;

import java.io.Serializable;

/**
 * Main data class representing an order (e.g. 2 sodas at 5euros each or 4 wheels at 35euros each or 1 dress at 45 euros)
 *
 * @author C. Esswein
 */
@SuppressWarnings("serial")
public class Order implements Serializable {

	private static int totalOrders = 0;
	private int id;
	private String product;
	private int quantity;
	private float unitPrice;
	
	
	/**
	 * 
	 * @param id Le numéro d'identifiant du produit
	 * @param product Le nom du produit.
	 * @param quantity La quantité du produit.
	 * @param unitPrice Le prix unitaire du produit.
	 */
	public Order(int id, String product, int quantity, float unitPrice) {
		super();
		this.id = id;
		this.product = product;
		this.quantity = quantity;
		this.unitPrice = unitPrice;
	}

	
	/**
	 * Constructeur de confort.
	 * 
	 * @param product Le nom du produit.
	 * @param quantity La quantité du produit.
	 * @param unitPrice Le prix unitaire du produit.
	 */
	public Order(String product, int quantity, float unitPrice) {
		this(++totalOrders, product, quantity, unitPrice);
	}
	
	
	/**
	 * Constructeur par défaut
	 */
	public Order() {
		this("", 0, 0);		
	}


	public String toString() {
		return id +"\t" +
			product + "\t" +
			quantity + "\t" +
			unitPrice + "€\n";
	}
	
	/**
	 * @return the totalOrders
	 */
	public static int getTotalOrders() {
		return totalOrders;
	}


	/**
	 * @param totalOrders the totalOrders to set
	 */
	public static void setTotalOrders(int totalOrders) {
		Order.totalOrders = totalOrders;
	}


	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}


	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
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
