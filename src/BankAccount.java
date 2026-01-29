import java.util.Objects;

public class BankAccount {
	
	private String accountNumber;
	
	private String accountHolderName;
	
	private double initialBalance;

	public BankAccount(String accountNumber, String accountHolderName, double initialBalance) {
		super();
		this.accountNumber = accountNumber;
		this.accountHolderName = accountHolderName;
		this.initialBalance = initialBalance;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getAccountHolderName() {
		return accountHolderName;
	}

	public void setAccountHolderName(String accountHolderName) {
		this.accountHolderName = accountHolderName;
	}

	public double getInitialBalance() {
		return initialBalance;
	}

	public void setInitialBalance(double initialBalance) {
		this.initialBalance = initialBalance;
	}

	@Override
	public int hashCode() {
		return Objects.hash(accountNumber);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BankAccount other = (BankAccount) obj;
		return Objects.equals(accountNumber, other.accountNumber);
	}
	
	
	
	
		
		

}
