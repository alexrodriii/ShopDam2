package model;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "products")
public class ProductList {
	private ArrayList<Product> products = new ArrayList<>();

	public ProductList() {
	};

	public ProductList(ArrayList<Product> products) {
		this.products = products;
	}

	@XmlElement(name = "product")
	public ArrayList<Product> getProducts() {
		return products;
	}

	public void setProducts(ArrayList<Product> products) {
		this.products = products;
	}

}