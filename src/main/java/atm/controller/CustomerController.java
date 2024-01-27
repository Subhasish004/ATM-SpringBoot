package atm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import atm.entity.Customer;
import atm.service.impl.CustomerServiceImpl;

@Controller
@RequestMapping("atm/customer")
public class CustomerController {

	@Autowired
	private CustomerServiceImpl customerServiceImpl;
	
	@GetMapping
	public String customers(Model model) {
		List<Customer> customers = customerServiceImpl.getAllCustomers();
		model.addAttribute("customers",customers);
		return "customer";
	}
	
	@GetMapping("/create")
	public String customerForm(Model model) {
		model.addAttribute("customer",new Customer());
		return "customerForm";
	}
	@PostMapping("/create")	
	public String createCustomer(@RequestBody Customer customer) {
		customerServiceImpl.createCustomer(customer);
		return "redirect:/atm/customer";	
	}
	
	@GetMapping("/edit/{id}")
	public String editCustomerForm(@PathVariable Long id,Model model) {
		Customer customer = customerServiceImpl.getCustomerById(id);
		model.addAttribute("customer",customer);
		return "editCustomer";
	}
	
	@PostMapping("edit/{id}")
	public String editCustomer(@PathVariable Long id , @ModelAttribute Customer updateCustomer) {
		Customer customer = customerServiceImpl.getCustomerById(id);
		customer.setFirstName(updateCustomer.getFirstName());
		customer.setLastName(updateCustomer.getLastName());
		customer.setAddress(updateCustomer.getEmail());
		customer.setAddress(updateCustomer.getAddress());
		customer.setGovId(updateCustomer.getGovId());
		customer.setPhoneNumber(updateCustomer.getPhoneNumber());
		return "redirect:/atm/customer";
	}
	
	@GetMapping("/delete/{id}")
	public String deleteCustomer(@PathVariable Long id){
		customerServiceImpl.deleteCustomer(id);
		return "redirect:/atm/customer";
	}
	
	
	
}
