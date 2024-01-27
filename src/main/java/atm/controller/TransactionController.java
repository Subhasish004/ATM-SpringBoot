package atm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import atm.entity.BankAccount;
import atm.entity.Transaction;
import atm.service.TransactionService;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("atm/transactions")
public class TransactionController {
	
	@Autowired
	TransactionService transactionService;
	
	@GetMapping("/account")
	public String viewTransactionByAccountId(HttpSession session,Model model) {
		BankAccount bankAccount = (BankAccount) session.getAttribute("bankAccount");
		//System.out.println(bankAccount.getAccountNumber());
		List<Transaction> Transactions = transactionService.getAllTransactionsByAccountId(bankAccount);
		model.addAttribute("transactions",Transactions);
		return "accounttransactions";
	}
	
	
	/*
	 * public Transaction generateTransaction(String type,Bank bank,BankAccount
	 * bankAccount , double amount , double balance) { Transaction transaction = new
	 * Transaction(); transaction.setType(type); transaction.setAmount(amount);
	 * transaction.setBalance(balance); transaction.setBank(bank);
	 * transaction.setBankAccount(bankAccount); return
	 * transactionService.createTransaction(transaction); }
	 */
	
	
	
}
