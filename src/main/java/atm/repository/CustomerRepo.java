package atm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import atm.entity.Customer;
@Repository
public interface CustomerRepo extends JpaRepository<Customer, Long>{

}
