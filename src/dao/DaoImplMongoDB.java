package dao;

import java.util.ArrayList;
import java.util.List;
import static com.mongodb.client.model.Filters.eq;
import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import model.Employee;
import model.Product;
import model.Amount;

public class DaoImplMongoDB implements Dao {


	
	 private MongoCollection<Document> inventoryCollection;
	    private MongoCollection<Document> historicalInventoryCollection;
	    private MongoCollection<Document> usersCollection;
	@Override
	public void connect() {
		String uri = "mongodb://localhost:27017";
		MongoClientURI mongoClientURI = new MongoClientURI(uri);
		MongoClient mongoClient = new MongoClient(mongoClientURI);
		MongoDatabase mongoDatabase = mongoClient.getDatabase("Shop");
		
		inventoryCollection = mongoDatabase.getCollection("inventory");
        historicalInventoryCollection = mongoDatabase.getCollection("historical_inventory");
        usersCollection = mongoDatabase.getCollection("users");
	}

	@Override
	public void disconnect() {

	}

	@Override
	public Employee getEmployee(int employeeId, String password) {
	    String uri = "mongodb://localhost:27017";
	    try (MongoClient mongoClient = new MongoClient(new MongoClientURI(uri))) {
	        MongoDatabase database = mongoClient.getDatabase("Shop");
	        MongoCollection<Document> employeesCollection = database.getCollection("users");

	        Document query = new Document("employeeeId", employeeId).append("password", password);
	        Document result = employeesCollection.find(query).first();

	        if (result != null) {
	            return new Employee(result.getInteger("employeeeId"), result.getString("name"), result.getString("password"));
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return null;
	}

	@Override
	public boolean writeInventory(ArrayList<Product> inventory) {
	    for (Product product : inventory) {
	      
	        Document wholesalerPriceDoc = new Document("value", product.getWholesalerPrice().getValue())
	                .append("currency", product.getWholesalerPrice().getCurrency());
	        
	        Document document = new Document("_id", new ObjectId())
	       .append("name", product.getName()).append("wholesalerPrice", wholesalerPriceDoc)
	       .append("available", product.isAvailable()).append("stock", product.getStock())
	       .append("created_at", new java.util.Date());
	        
	        historicalInventoryCollection.insertOne(document);
	    }
	    return true;
	}


	@Override
	public ArrayList<Product> getInventory() {
		ArrayList<Product> inventory = new ArrayList<>();
		
		for (Document documento : inventoryCollection.find()) {
			String name = documento.getString("name");

			Document wholesalerPriceM = (Document) documento.get("wholesalerPrice");
			double wholesalerPriceValue = wholesalerPriceM.getDouble("value");
			
			String wholesalerPriceCurrency = wholesalerPriceM.getString("currency");
			Amount wholesalerPrice = new Amount(wholesalerPriceValue);
			
			boolean available = documento.getBoolean("available");
			int stock = documento.getInteger("stock");
		
			Product product = new Product(name, wholesalerPrice, available, stock);
			inventory.add(product);

		}
		return inventory;
	}

	@Override
	public boolean addProduct(Product product) {
	    try {
	        Document wholesalerPriceDoc = new Document("value", product.getWholesalerPrice().getValue())
	                .append("currency", product.getWholesalerPrice().getCurrency());

	        Document document = new Document("_id", new ObjectId())
	      .append("name", product.getName()).append("wholesalerPrice", wholesalerPriceDoc)
	       .append("available", product.isAvailable()).append("stock", product.getStock())
	         .append("created_at", new java.util.Date());

	        inventoryCollection.insertOne(document);
	       
	        return true;
	        
	    } 
	    catch (Exception e) {
	        e.printStackTrace();
	        return false;
	    }
	}

	@Override
	public boolean updateStock(String productName, int newStock) {
	    try {
	        Document filter = new Document("name", productName);
	        Document update = new Document("$set", new Document("stock", newStock));

	        inventoryCollection.updateOne(filter, update);
	        System.out.println("Stock updated for product: " + productName + " to " + newStock);
	        return true;
	    } catch (Exception e) {
	        e.printStackTrace();
	        return false;
	    }
	}


	@Override
	public boolean deleteProduct(Product product) {
	    try {
	        Document result = new Document("name", product.getName());
	        inventoryCollection.deleteOne(result);
	        System.out.println("Product deleted: " + product.getName());
	        return true;
	    } catch (Exception e) {
	        e.printStackTrace();
	        return false;
	    }
	}

}
