package atm.service;

import java.util.List;
import atm.entity.Customer;

public interface CustomerService {
	Customer createCustomer(Customer customer);
	
	Customer getCustomerById(Long customerId);
	
	List<Customer> getAllCustomers();
	
	Customer updateCustomer(Customer customer);
	
	void deleteCustomer(Long customerId);

}
