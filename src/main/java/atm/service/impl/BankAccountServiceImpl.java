package atm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import atm.entity.BankAccount;
import atm.repository.BankAccountRepo;
import atm.service.BankAccountService;
@Service
public class BankAccountServiceImpl implements BankAccountService{

	@Autowired
	private BankAccountRepo bankaccountrepo;

	@Override
	public BankAccount createAccount(BankAccount bankAccount) {
		return bankaccountrepo.save(bankAccount);
	}

	@Override
	public BankAccount getAccountById(Long bankAccountId) {
		BankAccount bankAccount = bankaccountrepo.findById(bankAccountId).get();
		return bankAccount;
	}

	@Override
	public List<BankAccount> getAllAccounts() {
		return bankaccountrepo.findAll();
	}

	@Override
	public BankAccount updateAccount(BankAccount bankAccount) {
		BankAccount existingBankAccount=bankaccountrepo.findById(bankAccount.getId()).get(); 
			existingBankAccount.setBank(bankAccount.getBank());
		return bankaccountrepo.save(existingBankAccount);
			
	}

	@Override
	public BankAccount resetPassword(BankAccount bankAccount) {
		BankAccount existingBankAccount=bankaccountrepo.findById(bankAccount.getId()).get(); 
		existingBankAccount.setPassword(bankAccount.getPassword());
	return bankaccountrepo.save(existingBankAccount);
	
	}
	

	@Override
	public void deleteAccount(Long bankAccountId) {
		bankaccountrepo.deleteById(bankAccountId);
	}	


	@Override
	public boolean debit(BankAccount bankAccount, double balance, double amount) {
		
		if(balance >= amount) {
			double debitBalance = balance-amount;
			bankAccount.setBalance(debitBalance);
			bankaccountrepo.save(bankAccount);
			return true;
		}
		
		return false;
		
	}

	@Override
	public String credit(BankAccount bankAccount, double balance, double amount) {
		
			double creditBalance = balance+amount;
			bankAccount.setBalance(creditBalance);
			bankaccountrepo.save(bankAccount);
			
		
		return "Credited Sucessfully";
	}

	@Override
	public boolean fundTransfer(BankAccount fromBankAccount, BankAccount toBankAccount, double amount) {
		double balance = fromBankAccount.getBalance();
		if(balance >= amount) {
			double debitBalance = balance-amount;
			fromBankAccount.setBalance(debitBalance);
			bankaccountrepo.save(fromBankAccount);
			//
			double creditBalance = balance+amount;
			toBankAccount.setBalance(creditBalance);
			bankaccountrepo.save(toBankAccount);
			return true;
		}
		return false;
	}
	
	@Override
	public BankAccount getAccountByAccountNumber(String account) {
		BankAccount bankAccount = bankaccountrepo.findByAccountNumber(account);
		return bankAccount;
	}

	


}
