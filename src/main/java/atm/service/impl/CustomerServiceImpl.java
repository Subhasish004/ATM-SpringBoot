package atm.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import atm.entity.Customer;
import atm.repository.CustomerRepo;
import atm.service.CustomerService;
@Service
public class CustomerServiceImpl implements CustomerService{
	
	@Autowired
	private CustomerRepo customerrepo;
	@Override
	public Customer createCustomer(Customer customer) {
		return customerrepo.save(customer);
	}

	@Override
	public Customer getCustomerById(Long customerId) {	
		Optional<Customer> optionalCustomer = customerrepo.findById(customerId); //Optional --> used to avoid null pointer Exe
		return optionalCustomer.get();
	}

	@Override
	public List<Customer> getAllCustomers() {
		return customerrepo.findAll();
	}

	@Override
	public Customer updateCustomer(Customer customer) {
		Customer existingCustomer = customerrepo.findById(customer.getId()).get();
			existingCustomer.setFirstName(customer.getFirstName());
			existingCustomer.setLastName(customer.getLastName());
			existingCustomer.setPhoneNumber(customer.getPhoneNumber());
			existingCustomer.setGovId(customer.getGovId());
			existingCustomer.setEmail(customer.getEmail());
			existingCustomer.setAddress(customer.getAddress());
		Customer updatedCustomer = customerrepo.save(existingCustomer);
		return updatedCustomer;
	}

	@Override
	public void deleteCustomer(Long customerId) {
		customerrepo.deleteById(customerId);
	}

}
