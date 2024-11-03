package dao.xml;

import java.io.IOException;

import java.util.ArrayList;

import org.xml.sax.Attributes;

import org.xml.sax.SAXException;

import org.xml.sax.helpers.DefaultHandler;

import model.Amount;

import model.Product;

public class SaxReader extends DefaultHandler {

	ArrayList<Product> products;
	Product product;
	String value;
	String currency;
	String parsedElement;

	public ArrayList<Product> getProducts() {
		return products;

	}

	public void setProducts(ArrayList<Product> products) {
		this.products = products;

	}

	@Override
	public void startDocument() throws SAXException {
		this.products = new ArrayList<>();
	}

	@Override

	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		switch (qName) {
		case "product":
			this.product = new Product(attributes.getValue("name") != null ? attributes.getValue("name") : "empty");
			break;

		case "wholesalerPrice":
			this.currency = attributes.getValue("currency") != null ? attributes.getValue("currency") : "empty";
			break;

		case "stock":
			break;

		}

		this.parsedElement = qName;

	}
	@Override

	public void endElement(String uri, String localName, String qName) throws SAXException {

		if (qName.equals("product")) {

			this.products.add(product);

			this.parsedElement = "";

			product.setAvailable(true);

		}

		this.parsedElement = "";

	}

	@Override

	public void characters(char[] ch, int start, int length) throws SAXException {

		value = new String(ch, start, length);

		switch (parsedElement) {

		case "product":

			break;

		case "wholesalerPrice":

			double priceValue = Double.valueOf(value);

			Amount nuevo = new Amount(priceValue);

			product.setWholesalerPrice(nuevo);

			Amount publicAmount = new Amount(nuevo.getValue() * 2);

			product.setPublicPrice(publicAmount);

			break;

		case "stock":

			this.product.setStock(Integer.valueOf(value));

			break;

		}

	}

	@Override

	public void endDocument() throws SAXException {

		printDocument();

	}
	private void printDocument() {
		for (Product p : products) {
			System.out.println(p.toString());

		}

	}

}