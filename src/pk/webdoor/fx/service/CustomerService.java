/**
 *
 */
package pk.webdoor.fx.service;

import java.util.List;
import javax.inject.Inject;
import org.apache.log4j.Logger;

import pk.webdoor.fx.dao.ICustomerDao;
import pk.webdoor.model.Customer;

/**
 * @author Abid.Ur-Rehman
 *
 */
public class CustomerService {

    private static final Logger log = Logger.getLogger(CustomerService.class.getName());
    //private CustomerDaoImpl customerDao = new CustomerDaoImpl();
    @Inject
    private ICustomerDao customerDao;

    public List<Customer> getCustomers() {
        return customerDao.getCustomers();
    }

    public String updateCustomer(Customer updateCustomer) {
        log.info("Invoking CustomerService.updateCustomer()...");
        return customerDao.updateCustomer(updateCustomer);
    }

    public String mergeCustomer(Customer updateCustomer) {
        log.info("Invoking CustomerService.mergeCustomer()...");
        return customerDao.mergeCustomer(updateCustomer);
    }    
    
    public String deleteCustomer(Customer deleteCustomer) {
        log.info("Invoking CustomerService.deleteCustomer()...");
        return customerDao.deleteCustomer(deleteCustomer);
    }

}
