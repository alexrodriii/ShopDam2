package dao;

import java.util.ArrayList;



import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import model.Employee;
import model.Product;

public class DaoImplHibernate implements Dao{
	
	private SessionFactory sessionFactory;
	private Transaction tx;
	
	 public DaoImplHibernate() {
	        connect();
	    }
	 
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
	
		if(sessionFactory != null) {
			sessionFactory.close();
		}
		
	}

	@Override
	public Employee getEmployee(int employeeId, String password) {
		// TODO Auto-generated method stub
		return null;
	}

	

	@Override
	public boolean writeInventory(ArrayList<Product> inventory) {
	    Transaction transaction = null;
	    try (Session session = sessionFactory.openSession()) {
	        transaction = session.beginTransaction();

	        for (Product product : inventory) {
	      
	            Product existingProduct = session.get(Product.class, product.getId());
	            
	            if (existingProduct == null) {
	          
	                session.save(product);
	            } else {
	            
	            }
	        }
	        
	        transaction.commit(); 
	        return true;
	    } catch (Exception e) {
	        e.printStackTrace();
	        return false; 
	    }
	}

	@Override
	public ArrayList<Product> getInventory() {
	
		Session session = null;
		 ArrayList<Product> products = new ArrayList<>();
	        try {
	            session = sessionFactory.openSession();
	            products = (ArrayList<Product>) session.createQuery("FROM Product", Product.class).list();
	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	            if (session != null) {
	                session.close();
	            }
	        }
	        return new ArrayList<>(products);
	    }

	@Override
	public boolean addProduct(Product product) {
		Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = (Transaction) session.beginTransaction();
            session.save(product);
            transaction.commit();
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
	    Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = (Transaction) session.beginTransaction();
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
