package atm.controller;
//import java.util.List;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import atm.entity.Bank;
import atm.entity.BankAccount;
import atm.entity.Customer;
import atm.entity.Transaction;
import atm.service.BankAccountService;
import atm.service.BankService;
import atm.service.CustomerService;
import atm.service.TransactionService;
import jakarta.servlet.http.HttpSession;
@Controller
@RequestMapping("/atm")
public class BankAccountController {

	
	@Autowired
	BankAccountService bankAccountService;
	
	@Autowired
	CustomerService customerService;
	
	
	@Autowired
	BankService bankService;
	
	@Autowired
	TransactionService transactionService;
	/*
	 * @GetMapping("/all") public String BankAccount(Model model) {
	 * List<BankAccount> bankAccounts = bankAccountService.getAllAccounts();
	 * model.addAttribute("bankAccounts",bankAccounts); return "account"; }
	 */
	
	@GetMapping
	public String home() {
		return "index";
	}
	
	@GetMapping("/adminlogin")
	public String adminLogin() {
		return "adminlogin";	
	}
	@PostMapping("/adminlogin")
	public String admin(@RequestParam String admin) {
		if(admin.equals("subhAdmin")) {
			return "admin";
		}
		return "redirect:/atm/adminlogin";
	}
	@GetMapping("/account")
	public String displayAccount(HttpSession session , Model model){
		BankAccount bankAccount  = (BankAccount) session.getAttribute("bankAccount");
		
		model.addAttribute("bankAccount",bankAccount);
		return "bankaccount";
	}
	
	//Login 
	@GetMapping("/login")
	public String loginForm() {
		return "loginform";
	}
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        // Invalidate the session on logout
        session.invalidate();

        return "redirect:/atm/login"; // Redirect to the login page
    }
    
	@PostMapping("/login")
	public String login(String account ,String pass,
			HttpSession session,RedirectAttributes  rt) {
		
		BankAccount bankAccount = bankAccountService.getAccountByAccountNumber(account);
		
		if(bankAccount != null && pass.equals(bankAccount.getPassword())) {
			session.setAttribute("bankAccount", bankAccount);
			rt.addFlashAttribute("msg","Login Sucessfull");
			
			return "redirect:/atm/account";
		}
		
		rt.addFlashAttribute("msg","Invalid Account Number or Password");
		
		return "redirect:/atm/login";
	}
	
	
	//Bank Account Creation 
	 @GetMapping("/createcustomer")
	    public String showCustomerForm(Model model) {
		 	model.addAttribute("customer",new Customer());
	        return "customerform";
	    }
	 
	 @PostMapping("/createcustomer")
	    public String createCustomer(@ModelAttribute Customer customer,HttpSession session) {
		 	//customer object creation
		 	session.setAttribute("customer", customerService.createCustomer(customer));
		 	
	        return "redirect:/atm/createaccount";  // Redirect to a success page
	    }
	 @GetMapping("/createaccount")
	 public String showAccountForm(Model model,HttpSession session) {
		 	model.addAttribute("bankAccount",new BankAccount());
	        model.addAttribute("banks", bankService.getAllBanks() );
	        Customer customer = (Customer) session.getAttribute("customer");
	        model.addAttribute("customer",customer);
	        return "accountform";
	    }
	 @PostMapping("/createaccount")
	    public String createBankAccount(@ModelAttribute BankAccount bankAccount,
	            @RequestParam("bank") Long id, HttpSession session) {
		 	Bank bank = bankService.getBankById(id);
		 	//customer is associated with bank account
		 	Customer customer = (Customer) session.getAttribute("customer");
		 	bankAccount.setCustomer(customer);
		 	bankAccount.setBank(bank);
	        
			session.setAttribute("bankAccount",bankAccountService.createAccount(bankAccount));
			
	        return "redirect:/atm/account";  // Redirect to a success page
	    }
	 
	 public Transaction generateTransaction(String type,Bank bank,BankAccount bankAccount , double amount , double balance) {
		 Transaction transaction = new Transaction();
		 transaction.setType(type);
		 transaction.setAmount(amount);
		 transaction.setBalance(balance);
		 transaction.setBank(bank);
		 transaction.setBankAccount(bankAccount);
		 return transactionService.createTransaction(transaction);
	 }
	//Deposite functionalities ===========================
	@GetMapping("/debit")
	public String debitForm(HttpSession session ,Model model) {
		BankAccount bankAccount = (BankAccount) session.getAttribute("bankAccount");
		model.addAttribute("bankAccount",bankAccount);
		
		return "debitform";
	}
	
	@PostMapping("/debit")
	public String debit
			(@RequestParam Double amount 
			,HttpSession session,RedirectAttributes redir){
		
		BankAccount bankAccount = (BankAccount) session.getAttribute("bankAccount");

            String msg="";
            Transaction transaction =null;
		if(bankAccountService.debit(bankAccount,bankAccount.getBalance(),amount)) {
            	//generateTransaction("Debit", bankAccount.getBank(), bankAccount, amount, bankAccount.getBalance());
            	msg = "Debited Sucessfully";
            	transaction = generateTransaction("Debit", bankAccount.getBank(), bankAccount, amount, bankAccount.getBalance());
            }else {msg="Insufficient Funds";}
		
		//System.out.println(transaction.getId());
		session.setAttribute("transaction", transaction);
		//model.addAttribute("transaction",transaction);
		redir.addFlashAttribute("msg",msg);
        
		return "redirect:/atm/debitsucessfully";
	}
	@GetMapping("/debitsucessfully")
	public String debitSucessfully(HttpSession session,Model model) {
		Transaction transaction = (Transaction) session.getAttribute("transaction");
		model.addAttribute("transaction", transaction);
		return "funded";
	}
	
	//Credit Functionalities ====================================
	@GetMapping("/credit")
	public String creditForm(HttpSession session ,Model model) {
		BankAccount bankAccount = (BankAccount) session.getAttribute("bankAccount");
		model.addAttribute("bankAccount",bankAccount);
		
		return "creditform";
	}
	
	@PostMapping("/credit")
	public String credited
			(HttpSession session
			,@RequestParam Double amount 
			,RedirectAttributes redir){
		BankAccount bankAccount = (BankAccount) session.getAttribute("bankAccount");
			
		String msg = bankAccountService.credit(bankAccount,bankAccount.getBalance(),amount);
		Transaction transaction = generateTransaction("Credit", bankAccount.getBank(), bankAccount, amount, bankAccount.getBalance());
			
		//model.addAttribute("transaction",transaction);
		session.setAttribute("transaction", transaction);
		redir.addFlashAttribute("msg",msg);
        
		return "redirect:/atm/creditsucessfully";
	}
	
	@GetMapping("/creditsucessfully")
	public String creditSucessfully(HttpSession session,Model model) {
		Transaction transaction = (Transaction) session.getAttribute("transaction");
		model.addAttribute("transaction", transaction);
		return "funded";
	}
	
	@GetMapping("/fundtransfer")
	public String fundForm(HttpSession session , Model model) {
		BankAccount bankAccount = (BankAccount) session.getAttribute("bankAccount");
		model.addAttribute("bankAccount",bankAccount);
		List<BankAccount> bankAccounts = bankAccountService.getAllAccounts();
		model.addAttribute("bankAccounts",bankAccounts);
		 
		return "fundtransfer";
	}
	@PostMapping("/fundtransfer")
	public String fundTransfer(HttpSession session ,
			@RequestParam Long id,
			@RequestParam Double amount ,
			RedirectAttributes redir ) {
		BankAccount fromBankAccount = (BankAccount) session.getAttribute("bankAccount");
		BankAccount toBankAccount = bankAccountService.getAccountById(id);
		String msg="";
		Transaction transaction =null;
		if(bankAccountService.fundTransfer(
				fromBankAccount,toBankAccount,amount)) {
			msg = "Fund Transferd Sucessfully";
			transaction = generateTransaction("Fund Transfer", fromBankAccount.getBank(),fromBankAccount, amount,fromBankAccount.getBalance());
			generateTransaction("Fund Recieved", toBankAccount.getBank(),toBankAccount, amount,toBankAccount.getBalance());
		}else {msg="Insufficient Balance";}
		
		//model.addAttribute("transaction",transaction);
		session.setAttribute("transaction", transaction);
		redir.addFlashAttribute("msg",msg);
		
		return "redirect:/atm/fundedsucessfully";
	}
	@GetMapping("/fundedsucessfully")
	public String fundedSucessfully(HttpSession session,Model model) {
		Transaction transaction = (Transaction) session.getAttribute("transaction");
		model.addAttribute("transaction", transaction);
		return "funded";
	}
	
	/*
	 * @GetMapping("/delete/{id}") public String deleteAccount(@PathVariable Long
	 * id){ bankAccountService.deleteAccount(id); return "redirect:/atm/account"; }
	 */
}
