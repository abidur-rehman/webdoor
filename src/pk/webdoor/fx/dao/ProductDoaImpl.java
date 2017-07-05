package pk.webdoor.fx.dao;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import pk.webdoor.model.Product;

public class ProductDoaImpl implements IProductDao {

    private static final Logger log = Logger.getLogger(ProductDoaImpl.class.getName());

    @Override
    public Product getProductByMonth(String month) {
        log.debug("Invoking ProductDoaImpl.getProductByMonth()...");
        Session session = HibernateUtil.getInstance().openSession();
        Transaction tx = null;
        Query query = null;
        Product product = null;
        try {
            tx = session.beginTransaction();
            query = session.getNamedQuery("Product.findByMonth");
            query.setString(0, month);
            product = (Product) query.uniqueResult();
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
        return product;
    }

    @Override
    public List<Product> getAllProducts() {
        log.debug("Invoking ProductDoaImpl.getAllProducts()...");
        Session session = HibernateUtil.getInstance().openSession();
        Transaction tx = null;
        Query query = null;
        List<Product> products = new ArrayList<Product>();
        try {
            tx = session.beginTransaction();
            query = session.getNamedQuery("Product.findAll");
            List<?> results = (List<?>) query.list();
            for (Object result : results) {
                Product product = (Product) result;
                products.add(product);
            }
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return products;
    }

    @Override
    public String updateProduct(Product product) {
        log.debug("Invoking ProductDoaImpl.updateProduct()...");
        Session session = HibernateUtil.getInstance().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.saveOrUpdate(product);
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
        if (e.getMessage().contains("PRODUCT")) {
            message = "DB Error! Month alreay exists, please enter new data!";
        } else if (e.getMessage().contains("INVOICETOCUST")) {
            message = "DB Error! There are invoices created for this Month, so deletion is not possible!";
        }
        return message;
    }

    @Override
    public String deleteProduct(Product product) {
        log.debug("Invoking ProductDoaImpl.deleteProduct()...");
        Session session = HibernateUtil.getInstance().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.delete(product);
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
