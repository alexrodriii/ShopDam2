package model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@Entity
@Table(name = "inventory")

@XmlRootElement(name = "product")
@XmlType(propOrder = { "name", "id", "wholesalerPrice", "stock", "available", "publicPrice" })

public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "name")
	private String name;

	@Embedded
	private Amount publicPrice;

	@Embedded
	private Amount wholesalerPrice;

	@Column(name = "available")
	private boolean available;

	@Column(name = "stock")
	private int stock;

	@Transient
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
