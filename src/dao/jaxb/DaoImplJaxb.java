package dao.jaxb;

import java.util.ArrayList;
import java.util.List;

import dao.Dao;
import model.Employee;
import model.Product;

public class DaoImplJaxb implements Dao {

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
		boolean result = (new JaxbMarshaller()).init(inventory);
		return (result);
	}

	@Override
	public ArrayList<Product> getInventory() {
		ArrayList<Product> products = new ArrayList<>();
		products = (new JaxbUnMarshaller()).init();
		return products;

	}
}
	