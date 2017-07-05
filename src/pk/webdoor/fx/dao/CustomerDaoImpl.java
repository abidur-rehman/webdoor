/**
 *
 */
package pk.webdoor.fx.dao;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.Query;
import pk.webdoor.model.Customer;

/**
 * @author Abid.Ur-Rehman
 *
 */
public class CustomerDaoImpl implements ICustomerDao {

    private static final Logger log = Logger.getLogger(CustomerDaoImpl.class.getName());
    /*
     private SessionFactory sessionFactory;
	
     public SessionFactory getSessionFactory() {
		
     try{
     sessionFactory = new AnnotationConfiguration().
     configure().
     configure(new File("D:/EclipseJFXWS/at.bestsolution.efxclipse.runtime.examples.e4/hibernate.cfg.xml")).
     addAnnotatedClass(WDPackage.class).
     addAnnotatedClass(Customer.class).
     addAnnotatedClass(WDPackage.class).
     buildSessionFactory();
			
     }catch (Throwable ex) { 
     System.err.println("Failed to create sessionFactory object." + ex);
     throw new ExceptionInInitializerError(ex); 
     }
		
     return sessionFactory;
     }
	
     public void setSessionFactory(SessionFactory sessionFactory) {
     this.sessionFactory = sessionFactory;
     }
	
     */

    public List<Customer> getCustomers() {
        log.info("Invoking CustomerDaoImpl.getCustomers()...");
        Session session = HibernateUtil.getInstance().openSession();
        Transaction tx = null;
        Query query = null;
        List<Customer> customers = new ArrayList<Customer>();
        try {
            tx = session.beginTransaction();
            query = session.getNamedQuery("Customer.findAll");
            List<?> results = (List<?>) query.list();
            for (Object result : results) {
                Customer customer = (Customer) result;
                customers.add(customer);
            }
            /*
             customers = query.list(); 
             Collections.sort(customers);
             for (Iterator iterator = 
             customers.iterator(); iterator.hasNext();){
             Customer customer = (Customer) iterator.next(); 
             System.out.println(customer.getWdpackages());
             }*/
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return customers;
    }

    public String updateCustomer(Customer updateCustomer) {
        log.info("Invoking CustomerDaoImpl.updateCustomer()...");
        Session session = HibernateUtil.getInstance().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.saveOrUpdate(updateCustomer);
            tx.commit();
            return "SUCCESS";
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
            return getMessage(e);
        } finally {
            session.close();
        }
    }

    public String deleteCustomer(Customer deleteCustomer) {
        log.info("Invoking CustomerDaoImpl.deleteCustomer()...");
        Session session = HibernateUtil.getInstance().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Customer getCustomer = (Customer) session.load(Customer.class, deleteCustomer.getCid());
            getCustomer.getWdpackages().clear();
            session.delete(getCustomer);
            tx.commit();
            return "SUCCESS";
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
            return getMessage(e);
        } finally {
            session.close();
        }
    }

    private String getMessage(HibernateException e) {
        String message = "";
        if (e.getMessage().contains("PACKAGETOCUST")) {
            message = "DB Error! Please select Package!";
        } else if (e.getMessage().contains("CUSTOMER")) {
            message = "DB Error! Customer alreay exists, please enter new data!";
        } else if (e.getMessage().contains("INVOICETOCUST")) {
            message = "DB Error! Customer cant be deleted. "
                    + "This Customer has invoices!";
        }                
        return message;
    }

    @Override
    public String mergeCustomer(Customer updateCustomer) {
        log.info("Invoking CustomerDaoImpl.updateCustomer()...");
        Session session = HibernateUtil.getInstance().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.merge(updateCustomer);
            tx.commit();
            return "SUCCESS";
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
            return getMessage(e);
        } finally {
            session.close();
        }
    }

}
