import java.io.Serializable;


public class Person implements java.io.Serializable {
	
	public Person(String first, String last, int accnum, String accname, String user, String pass) {
		this.first = first;
		this.last = last;
		this.accnum = accnum;
		this.user = user;
		this.pass = pass;
		a = new Account(accname);
		hash = first + last;
	}
	
	public String getUser() {
		return user;
	}
	
	public String getFirst() {
		return first;
	}
	
	public String getLast() {
		return last;
	}
	
	public int getAccNum() {
		return accnum;
	}
	
	
	public Account getAccount() {
		return a;
	}
	
	public String getInfo() {
		return "First: " + first + "\n" + "Last: " + last + "\n" + "Username: " + user + "\n" + "Account Number: " + accnum + "\n" + "Accounts: " + a.getName();
	}
	
	public String getHash() {
		return hash;
	}
	
	public String getPass() {
		return pass;
	}
	

	private String hash;
	private String first;
	private String last;
	private String user;
	private String pass;
	private boolean open = false;
	private int accnum;
	private Account a;

}
