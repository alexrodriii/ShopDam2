package model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "product")
@XmlType(propOrder = { "name", "id", "wholesalerPrice", "stock", "available", "publicPrice" })

public class Product {

	private int id;

	private String name;

	private Amount publicPrice;

	private Amount wholesalerPrice;

	private boolean available;

	private int stock;

	private static int totalProducts;

	public final static double EXPIRATION_RATE = 0.60;

	public Product() {
		this.available = true;
		this.id = id;
	}

	public Product(String name, Amount wholesalerPrice, boolean available, int stock) {

		super();
		this.id = ++totalProducts;

		this.id = totalProducts + 1;

		this.name = name;

		this.wholesalerPrice = wholesalerPrice;

		this.publicPrice = new Amount(wholesalerPrice.getValue() * 2);

		this.available = available;

		this.stock = stock;

		totalProducts++;

	}
	/*
	 * public Product(String name, Amount wholesalerPrice, int stock) {
	 *
	 * super();
	 *
	 * this.name = name;
	 *
	 * this.wholesalerPrice = wholesalerPrice;
	 *
	 * this.stock = stock;
	 *
	 * }
	 */

	public Product(String name) {

		this.name = name;

	}

	@XmlAttribute(name = "id")
	public int getId() {

		return id;

	}

	public void setId(int id) {

		this.id = id;

	}

	@XmlAttribute(name = "name")
	public String getName() {

		return name;

	}

	public void setName(String name) {

		this.name = name;

	}

	@XmlElement(name = "PublicPrice")
	public Amount getPublicPrice() {

		return publicPrice;

	}

	public void setPublicPrice(Amount publicPrice) {

		this.publicPrice = publicPrice;

	}

	@XmlElement(name = "wholesalerPrice")
	public Amount getWholesalerPrice() {

		return wholesalerPrice;

	}

	public void setWholesalerPrice(Amount wholesalerPrice) {

		this.wholesalerPrice = wholesalerPrice;
		this.publicPrice = new Amount(wholesalerPrice.getValue() * 2);

	}

	@XmlElement(name = "available")
	public boolean isAvailable() {
		boolean available = true;
		return available;
	}

	public void setAvailable(boolean available) {

		this.available = available;

	}

	@XmlElement(name = "stock")
	public int getStock() {

		return stock;

	}

	public void setStock(int stock) {

		this.stock = stock;

	}

	public static int getTotalProducts() {

		return totalProducts;

	}

	public static void setTotalProducts(int totalProducts) {

		Product.totalProducts = totalProducts;

	}

	public void expire() {

		this.publicPrice.setValue(this.getPublicPrice().getValue() * EXPIRATION_RATE);
		;

	}

	@Override

	public String toString() {

		return "Product [name=" + name + ", publicPrice=" + publicPrice + ", wholesalerPrice=" + wholesalerPrice

				+ ", available=" + available + ", stock=" + stock + "]";

	}

}
