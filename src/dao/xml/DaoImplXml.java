package dao.xml;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;
import dao.Dao;
import model.Employee;
import model.Product;
import dao.xml.SaxReader;
import dao.xml.DomWriter;

public class DaoImplXml implements Dao {
// Read an existing xml document

	@Override
	public void connect() {
// TODO Auto-generated method stub

	}

	@Override
	public void disconnect() {
// TODO Auto-generated method stub

	}

	@Override
	public Employee getEmployee(int employeeId, String password) {
// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean writeInventory(ArrayList<Product> inventory) {
	    DomWriter domWriter = new DomWriter();
	    domWriter.generateDocument(inventory); 
	    domWriter.generateXml(); 

	    return true;
	}

	@Override
	public ArrayList<Product> getInventory() {
		ArrayList<Product> inventory = null;

		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser parser;
		try {
			parser = factory.newSAXParser();
			File file = new File("xml/inputInventory.xml");
			SaxReader saxReader = new SaxReader();
			parser.parse(file, saxReader);
			inventory = saxReader.getProducts();

		} catch (ParserConfigurationException | SAXException e) {
			System.out.println("ERROR creating the parser");
		} catch (IOException e) {
			System.out.println("ERROR file not found");
		}

		return inventory;

	}

	@Override
	public boolean addProduct(Product product) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateStock(String productName, int newStock){
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteProduct(Product product) {
		// TODO Auto-generated method stub
		return false;
	}

}
