package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Amount;
import model.Employee;
import model.Product;

public class DaoImplJDBC implements Dao {
	private Connection connection;

	@Override
	public void connect() {
		// Define connection parameters
		String url = "jdbc:mysql://localhost:3306/shop";
		String user = "root";
		String pass = "";
		try {
			this.connection = DriverManager.getConnection(url, user, pass);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void disconnect() {
		// TODO Auto-generated method stub
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	@Override
	public Employee getEmployee(int employeeId, String password) {
		Employee employee = null;
		String query = "select * from employee where employeeId= ? and password = ? ";

		try (PreparedStatement ps = connection.prepareStatement(query)) {
			ps.setInt(1, employeeId);
			ps.setString(2, password);
			// System.out.println(ps.toString());
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					employee = new Employee(rs.getInt(1), rs.getString(2), rs.getString(3));
				}
			}
		} catch (SQLException e) {
			// in case error in SQL
			e.printStackTrace();
		}
		return employee;
	}

	@Override

	public boolean writeInventory(ArrayList<Product> inventory) {
	ArrayList<Product> inventory1 = new ArrayList<>();
	String query = "INSERT INTO historical_inventory (id_product, name, wholesalerPrice, available, stock,created_at) " +
	               "VALUES (?, ?, ?, ?, ?,NOW())";
		try (PreparedStatement pst = connection.prepareStatement(query)) {
			
			for (Product product :inventory) {
		pst.setInt(1, product.getId());	pst.setString(2, product.getName());pst.setDouble(3, product.getWholesalerPrice().getValue());
		pst.setBoolean(4, product.isAvailable());pst.setInt(5, product.getStock());pst.addBatch();
			}
		     pst.executeBatch();
			return true;
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			return false;
		}
}

	@Override
	public ArrayList<Product> getInventory() {
		ArrayList<Product> inventory = new ArrayList<>();
		
		String query = "SELECT * FROM inventory";
		try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
		 while (rs.next()) {
	Product product = new Product(rs.getString(2), new Amount(rs.getDouble(4)), rs.getBoolean(5),rs.getInt(3));
				product.setId(rs.getInt(1));
				
				inventory.add(product);
			}
		 
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return inventory;

	}
	
	public boolean addProduct(Product product) {
		ArrayList<Product> inventory = new ArrayList<>();
		String Query = "INSERT INTO inventory (name, wholesalerPrice, available, stock)"+"VALUES (?, ?, ?, ?)";
		
	try (PreparedStatement pst = connection.prepareStatement(Query)) {
		
   pst.setString(1, product.getName());pst.setDouble(2, product.getWholesalerPrice().getValue());
	pst.setBoolean(3, product.isAvailable());pst.setInt(4, product.getStock());
	     pst.executeUpdate();
		return true;
	} catch (SQLException e) {
		
		e.printStackTrace();
		return false;
	}
	}	

	@Override
	public boolean updateStock(String productName, int newStock) {

		 String query = "UPDATE inventory SET stock = ? WHERE name = ?";
		    try (PreparedStatement pst = connection.prepareStatement(query)) {   	
		       pst.setInt(1, newStock); pst.setString(2, productName);
		        pst.executeUpdate();
		         return true;
		        	
		    } catch (SQLException e) {
		        e.printStackTrace();
		        return false;
		    }
		}

	@Override
	public boolean deleteProduct(Product product) {
	 String query = "DELETE FROM inventory WHERE name = ?";
	
	 try(PreparedStatement pst = connection.prepareStatement(query)) {
		pst.setString(1, product.getName());
		pst.executeUpdate();

      return true;
	} 
	  catch (SQLException e) {
	   	e.printStackTrace();
	     return false;
		
	   }
	}
	
  }