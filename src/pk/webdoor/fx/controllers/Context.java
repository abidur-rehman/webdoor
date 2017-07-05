package pk.webdoor.fx.controllers;

import pk.webdoor.model.Customer;

public class Context {
	
    private final static Context instance = new Context();

    private Customer customer;
    
    public static Context getInstance() {
        return instance;
    }

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
    
    
    
    
}
