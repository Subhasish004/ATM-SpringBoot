package atm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import atm.entity.Bank;
import atm.service.impl.BankServiceImpl;
@Controller
@RequestMapping("atm/bank")
public class BankController {

	@Autowired
	BankServiceImpl bankServiceImpl;
	
	@GetMapping
	public String Bank(Model model) {
		List<Bank> banks = bankServiceImpl.getAllBanks();
		model.addAttribute("banks",banks);
		return "bank";
	}
	
	@GetMapping("/create")
	public String BankForm(Model model) {
		model.addAttribute("bank",new Bank());
		return "bankform";
	}
	@PostMapping("/create")	
	public String createBank(@ModelAttribute Bank bank) {
		bankServiceImpl.createBank(bank);
		return "redirect:/atm/bank";	
	}
	
	@GetMapping("/delete/{id}")
	public String deleteBank(@PathVariable Long id){
		bankServiceImpl.deleteBank(id);
		return "redirect:/atm/bank";
	}
	
	
	
}
