package dao.xml;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import dao.Dao;
import model.Amount;
import model.Employee;
import model.Product;

public class DomWriter {
	private Document document;

	public DomWriter() {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newDefaultInstance();
			DocumentBuilder builder;
			builder = factory.newDocumentBuilder();
			document = builder.newDocument();
		} catch (ParserConfigurationException e) {
			System.out.println("ERROR generating document");
		}
	}

	public boolean generateDocument(List<Product> inventory) {
		try {
			Element products = document.createElement("products");
			document.appendChild(products);
			for (Product item : inventory) {

				Element product = document.createElement("product");
				product.setAttribute("name", item.getName());
				products.appendChild(product);

				Element wholesalerPrice = document.createElement("wholesalerPrice");
				wholesalerPrice.setAttribute("currency", "Euro");

				Amount amount = item.getWholesalerPrice();
				wholesalerPrice.setTextContent(String.format("%.2f", amount.getValue())); // Obtener el valor double
				product.appendChild(wholesalerPrice);

				Element stock = document.createElement("stock");
				stock.setTextContent(String.valueOf(item.getStock()));
				product.appendChild(stock);

			}

			return true;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public void generateXml() {
		try {
			TransformerFactory factory = TransformerFactory.newInstance();
			Transformer transformer = factory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			Source source = new DOMSource(document);
			String dia =new SimpleDateFormat("yyyy-mm-dd").format(new Date());
			File file = new File("xml/inventory_" + dia + ".xml");
			try (FileWriter fw = new FileWriter(file); PrintWriter pw = new PrintWriter(fw)) {
				Result result = new StreamResult(pw);
				
				transformer.transform(source, result);
			}
		} catch (IOException e) {
			System.out.println("Error creando el archivo: " );
		} catch (TransformerException e) {
			System.out.println("Error transformando el documento: ");
		}

	}

}
