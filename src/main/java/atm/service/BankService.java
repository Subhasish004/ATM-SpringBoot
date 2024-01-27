package atm.service;

import java.util.List;

import org.springframework.stereotype.Repository;

import atm.entity.Bank;
@Repository
public interface BankService {
	Bank createBank(Bank bank);
		
	Bank getBankById(Long bankId);
		
	List<Bank> getAllBanks();
		
	Bank updateBank(Bank bank);
		
	void deleteBank(Long bankId);


}
