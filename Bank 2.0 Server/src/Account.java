	
public class Account implements java.io.Serializable {
	
	public Account(String name) {
		this.name = name;
	}
	
	public void deposit(double cash) {
		balance += cash;
	}
	
	public double withdraw(double cash) {

		if(balance >= cash) {
			balance -= cash;
			return cash;
		}
		else {
			return 0;
		}
	}
	
	public double getBalance() {
		return balance;
	}
	
	public String getName() {
		return name;
	}
	
	double balance;
	String name;
}