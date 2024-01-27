package atm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import atm.entity.BankAccount;
import atm.entity.Transaction;
@Repository
public interface TransactionRepo extends JpaRepository<Transaction, Long>{

    @Query("SELECT t FROM Transaction t WHERE t.bankAccount = :bankAccount")
    List<Transaction> findAllByAccountId(BankAccount bankAccount);

    /*@Query("SELECT t FROM Transaction t WHERE t.bankId = :bankId")
    List<Transaction> findAllByBankId(Long bankId);}*/
}
