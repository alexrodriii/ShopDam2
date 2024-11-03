package dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import model.Amount;
import model.Employee;
import model.Product;
import model.Sale;

public class DaoImplFile implements Dao{

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
	     String fileName = "inventory_" + java.time.LocalDate.now() + ".txt";
	        File file = new File(System.getProperty("user.dir") + File.separator + "files" + File.separator + fileName);

	        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
	            int total = 1;
	            for (Product product : inventory) {
	                String line = total + ";Product:" + product.getName() + ";Stock:" + product.getStock() + ";";
	                bw.write(line);
	                bw.newLine();
	                total++;
	            }
	            bw.write("Numero total de productos:" + total + ";");
	            bw.newLine();
	            return true; 
	        } catch (IOException e) {
	            e.printStackTrace();
	            return false;
	        }
	    }

	@Override
	public List<Product> getInventory() {
		  List<Product> inventory = new ArrayList<>();
	        File file = new File("files/inputinventory.txt");

	        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
	            String line;
	            while ((line = br.readLine()) != null) {
	                String[] sections = line.split(";");

	                String name = "";
	                double wholesalerPrice = 0.0;
	                int stock = 0;

	                for (String section : sections) {
	                    String[] data = section.split(":");
	                    switch (data[0]) {
	                        case "Product":
	                            name = data[1];
	                            break;
	                        case "Wholesaler Price":
	                            wholesalerPrice = Double.parseDouble(data[1]);
	                            break;
	                        case "Stock":
	                            stock = Integer.parseInt(data[1]);
	                            break;
	                    }
	                }
	                
	                inventory.add(new Product(name, new Amount(wholesalerPrice), true, stock));
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        return inventory;
	    }

}
