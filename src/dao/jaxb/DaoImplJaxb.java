package dao.jaxb;

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
	public boolean writeInventory(List<Product> inventory) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Product> getInventory() {
		// TODO Auto-generated method stub
		return null;
	}

}
