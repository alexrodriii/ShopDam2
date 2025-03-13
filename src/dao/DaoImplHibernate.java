package dao;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import model.Employee;
import model.Product;
import model.ProductHistory;

public class DaoImplHibernate implements Dao {

    private SessionFactory sessionFactory;
    private Session session;
    private Transaction tx;

    @Override
	public void connect() {
		try {
			  sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();		
		} catch(Exception e) {
			  e.printStackTrace();
	            System.err.println("Error al conectar con Hibernate.");
		}
		
	}

    @Override
    public void disconnect() {
        if (session != null && session.isOpen()) {
            session.close();
        }
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }

    @Override
    public Employee getEmployee(int employeeId, String password) {
        return null; 
    }

    @Override
    public boolean writeInventory(ArrayList<Product> inventory) {
        connect();
        boolean exportado = false;
        try {
            tx = session.beginTransaction();
            LocalDateTime today = LocalDateTime.now();
          
            for (Product product : inventory) {
                ProductHistory historial = new ProductHistory();
                historial.setProductId(product.getId());
                historial.setName(product.getName());
                historial.setWholesalerPrice(product.getWholesalerPrice());
                historial.setStock(product.getStock());
                historial.setDate(today.toLocalDate().toString());
                session.save(historial);

             
                Product existingProduct = session.get(Product.class, product.getId());
                if (existingProduct != null) {
                    existingProduct.setStock(product.getStock());
                    session.update(existingProduct);
                }
            }

	            
            tx.commit();
            exportado = true;
        } 
        finally {
        	
            disconnect();
        }
        return exportado;
    }

    @Override
    public ArrayList<Product> getInventory() {
        ArrayList<Product> products = new ArrayList<>();
        try (Session session = sessionFactory.openSession()) {
            products = (ArrayList<Product>) session.createQuery("FROM Product", Product.class).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return products;
    }

    @Override
    public boolean addProduct(Product product) {
        try (Session session = sessionFactory.openSession()) {
        	
            Transaction transaction = session.beginTransaction();
            session.save(product);
            
            transaction.commit();
            System.out.println("Product added successfully.");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
	public boolean updateStock(String productName, int newStock) {
	     Transaction transaction = null;
	        try (Session session = sessionFactory.openSession()) {
	            transaction = (Transaction) session.beginTransaction();
	            Product product = session.createQuery("FROM Product WHERE name = :name", Product.class)
	                    .setParameter("name", productName)
	                    .uniqueResult();
	            if (product != null) {
	                product.setStock(newStock);
	                session.update(product);
	                transaction.commit();
	                return true;
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return false;
	    }

    @Override
    public boolean deleteProduct(Product product) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Product productToRemove = session.get(Product.class, product.getId());
            if (productToRemove != null) {
                session.delete(productToRemove);
                transaction.commit();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
