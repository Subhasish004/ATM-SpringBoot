package atm.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;



@Entity
@Table(name="bankaccount")
public class BankAccount {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private double balance;
	 @ManyToOne
	 @JoinColumn(name = "customer")
	 private Customer customer;
	 
	 @ManyToOne
	 @JoinColumn(name = "bank")
	 private Bank bank;
	 
	 @Column(nullable = false , unique = true)
	 private String accountNumber;
	 
	 @Column(nullable = false)
	 private String password;
	 
	
	public BankAccount() {
		super();	
	}


	public BankAccount(Long id, double balance, Customer customer, Bank bank, String accountNumber, String password) {
		super();
		this.id = id;
		this.balance = balance;
		this.customer = customer;
		this.bank = bank;
		this.accountNumber = accountNumber;
		this.password = password;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public double getBalance() {
		return balance;
	}


	public void setBalance(double balance) {
		this.balance = balance;
	}


	public Customer getCustomer() {
		return customer;
	}


	public void setCustomer(Customer customer) {
		this.customer = customer;
	}


	public Bank getBank() {
		return bank;
	}


	public void setBank(Bank bank) {
		this.bank = bank;
	}


	public String getAccountNumber() {
		return accountNumber;
	}


	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}
	
		 
}
