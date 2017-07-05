/**
 * 
 */
package pk.webdoor.fx.dao;

import java.util.List;



import pk.webdoor.model.Customer;


/**
 * @author Abid.Ur-Rehman
 *
 */

public interface ICustomerDao {
	
	List<Customer> getCustomers();
        String updateCustomer(Customer updateCustomer); 
        String deleteCustomer(Customer deleteCustomer);
        String mergeCustomer(Customer customer);
}
