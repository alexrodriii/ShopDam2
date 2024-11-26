package dao.jaxb;

import javax.xml.bind.JAXBContext;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.util.ArrayList;
import model.Product;
import model.ProductList;

public class JaxbMarshaller {

	public boolean init(ArrayList<Product> products) {
		boolean result = false;

		try {
			JAXBContext context = JAXBContext.newInstance(ProductList.class);
			Marshaller marshaller = context.createMarshaller();
			System.out.println("marshalling... ");
			ProductList product = new ProductList(products);
			LocalDate date = LocalDate.now();
			String dateFormatted = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
			marshaller.marshal(product, new File("jaxb/inventory_"+dateFormatted+".xml"));
			result = true;

		} catch (JAXBException e) {
			e.printStackTrace();
			result = false;
		}
		return result;

	}

}