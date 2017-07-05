/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pk.webdoor.fx.dao;

import java.net.URL;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import pk.webdoor.model.Customer;
import pk.webdoor.model.InvoiceToCust;
import pk.webdoor.model.Product;
import pk.webdoor.model.WDPackage;




public class HibernateUtil {

    private static org.hibernate.SessionFactory sessionFactory;
    private static ServiceRegistry serviceRegistry;
    
    private HibernateUtil(){
    	
    }
    
    static {
    	
		URL myurl = Thread.currentThread().getContextClassLoader().getResource("pk/webdoor/fx/hibernate.cfg.xml");
		
		Configuration configuration = new Configuration();
		configuration.configure(myurl).addAnnotatedClass(Customer.class).addAnnotatedClass(WDPackage.class)
		.addAnnotatedClass(InvoiceToCust.class).addAnnotatedClass(Product.class);
		serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry(); 
		sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		
    }
    

    
    public static SessionFactory getInstance(){
    	return sessionFactory;
    }
    
    
    public Session openSession(){
    	return sessionFactory.openSession();
    }
    
    public Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }
    
    public static void close(){
    	if(sessionFactory != null){
    		sessionFactory.close();
    	}
    	
    	sessionFactory = null;
    }
}
