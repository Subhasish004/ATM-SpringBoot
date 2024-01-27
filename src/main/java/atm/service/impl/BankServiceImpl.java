package atm.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import atm.entity.Bank;
import atm.repository.BankRepo;
import atm.service.BankService;
@Service
public class BankServiceImpl implements BankService{

	@Autowired
	private BankRepo bankrepo;
	@Override
	public Bank createBank(Bank bank) {
		
		return bankrepo.save(bank);
	}

	@Override
	public Bank getBankById(Long bankId) {
		Optional<Bank> optionalBank = bankrepo.findById(bankId); //Optional --> used to avoid null pointer Exe
		return optionalBank.get();
	}

	@Override
	public List<Bank> getAllBanks() {
		return bankrepo.findAll();
	}

	@Override
	public Bank updateBank(Bank bank) {
		Bank existingBank= bankrepo.findById(bank.getId()).get();
			existingBank.setBankName(bank.getBankName());
		Bank updatedBank=bankrepo.save(existingBank);		
		return updatedBank;
	}

	@Override
	public void deleteBank(Long bankId) {
		bankrepo.deleteById(bankId);
	}

	
}
