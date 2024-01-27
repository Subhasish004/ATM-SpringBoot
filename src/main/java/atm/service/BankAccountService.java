package atm.service;

import java.util.List;

import atm.entity.BankAccount;

public interface BankAccountService {
	BankAccount createAccount(BankAccount bankAccount);
	
	BankAccount getAccountById(Long bankAccountId);
		
	List<BankAccount> getAllAccounts();
		
	BankAccount updateAccount(BankAccount bankAccount);
	
	BankAccount resetPassword(BankAccount bankAccount);
		
	void deleteAccount(Long bankAccountId);
	
	boolean debit(BankAccount bankAccount,double balance , double amount);
	
	String credit(BankAccount bankAccount,double balance ,double amount);
	
	public BankAccount getAccountByAccountNumber(String account);

	boolean fundTransfer(BankAccount fromBankAccount, BankAccount toBankAccount, double amount);
	
	//public Long validateLogin(String accountNumber , String password);
	
}
