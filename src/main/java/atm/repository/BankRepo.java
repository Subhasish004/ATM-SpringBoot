package atm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import atm.entity.Bank;
@Repository
public interface BankRepo extends JpaRepository<Bank, Long>{

}
