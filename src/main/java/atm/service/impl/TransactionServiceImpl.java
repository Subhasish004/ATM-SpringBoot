package atm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import atm.entity.BankAccount;
import atm.entity.Transaction;
import atm.repository.TransactionRepo;
import atm.service.TransactionService;
@Service
public class TransactionServiceImpl implements TransactionService{

	@Autowired
	private TransactionRepo transactionRepo;
	
	@Override
	public Transaction createTransaction(Transaction transaction) {
		return transactionRepo.save(transaction);
	}

	@Override
	public Transaction getTransactionById(Long transactionId) {
		return transactionRepo.findById(transactionId).get();
	}

	@Override
	public List<Transaction> getAllTransactionsByAccountId(BankAccount bankAccount) {
		return transactionRepo.findAllByAccountId(bankAccount);
    }

	/*
	 * @Override public List<Transaction> getAllTransactionsByBank(Long bankId) {
	 * 
	 * return transactionRepo.findAllByBankId(bankId); }
	 */

	@Override
	public List<Transaction> getAllTransactions() {
        return transactionRepo.findAll();
	}

	
	
	

}
