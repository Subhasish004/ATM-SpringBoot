package atm.service;

import java.util.List;

import atm.entity.BankAccount;
import atm.entity.Transaction;

public interface TransactionService {
	
	Transaction createTransaction(Transaction transaction);
	
	Transaction getTransactionById(Long transactionId);
		
	List<Transaction> getAllTransactionsByAccountId(BankAccount bankAccount);
	
	//List<Transaction> getAllTransactionsByBank(Long bankId);
	
	List<Transaction> getAllTransactions();
			
	
	
}
