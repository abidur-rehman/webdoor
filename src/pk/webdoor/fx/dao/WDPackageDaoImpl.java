package pk.webdoor.fx.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import pk.webdoor.model.WDPackage;

public class WDPackageDaoImpl implements IWDPackageDao {

	@Override
	public List<WDPackage> getPackages() {
		System.out.println("Invoking WDPackageDaoImpl.getPackages()...");
	      Session session = HibernateUtil.getInstance().openSession();
	      Transaction tx = null;
	      Query query = null;
	      List<WDPackage> packages = new ArrayList<WDPackage>();
	      try{
	          tx = session.beginTransaction();
	          query = session.getNamedQuery("WDPackage.findAll");
	          List<?> results =(List<?>) query.list();
	          for(Object result:results) {
	        	  WDPackage pack = (WDPackage) result;
	        	  packages.add(pack);
	           }
	          
	          tx.commit();
	       }catch (HibernateException e) {
	          if (tx!=null) tx.rollback();
	          e.printStackTrace(); 
	       }finally {
	          session.close(); 
	       }
		return packages;
	}

	@Override
	public WDPackage getPackageByDetails(Integer details) {
		System.out.println("Invoking WDPackageDaoImpl.getPackageByDetails()...");
	      Session session = HibernateUtil.getInstance().openSession();
	      Transaction tx = null;
	      Query query = null;
	      WDPackage wdPackage = null;
	      try{
	    	  tx = session.beginTransaction();
	    	  query = session.getNamedQuery("WDPackage.findByDetails");
	    	  query.setInteger(0, details);
	    	  wdPackage = (WDPackage) query.uniqueResult();
	    	  
	    	  tx.commit();
	      }catch (HibernateException e) {
	          if (tx!=null) tx.rollback();
	          e.printStackTrace(); 
	       }finally {
	          session.close(); 
	       }  
		return wdPackage;
	}

	@Override
	public WDPackage getPackageByName(String name) {
		System.out.println("Invoking WDPackageDaoImpl.getPackageByName(String detais)...");
	      Session session = HibernateUtil.getInstance().openSession();
	      Transaction tx = null;
	      Query query = null;
	      WDPackage wdPackage = null;
	      try{
	    	  tx = session.beginTransaction();
	    	  query = session.getNamedQuery("WDPackage.findByName");
	    	  query.setString(0, name);
	    	  wdPackage = (WDPackage) query.uniqueResult();
	    	  
	    	  tx.commit();
	      }catch (HibernateException e) {
	          if (tx!=null) tx.rollback();
	          e.printStackTrace(); 
	       }finally {
	          session.close(); 
	       }  
		return wdPackage;
	}        
}
