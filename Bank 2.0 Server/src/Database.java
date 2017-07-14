import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;


public class Database implements Serializable {
	
	public void admin() {
		
	}
	
	public void addPerson(String first, String last,String accname, String user, String pass) {
		Person p = new Person(first, last, inplay.getNew(), accname, user, pass);
		hlist.put(p.getUser() + p.getPass(), p);
		alist.add(p);
		
	}
	
	public void addPerson(Person p) {
		hlist.put(p.getUser()+p.getPass(), p);
		alist.add(p);
	}
	
	public Boolean hasPerson(String userpass) {
		if(hlist.containsKey(userpass)) {
			return true;
		}
		else {
			return false;
		}
	}	
	
	public Person getPerson(String userpassword) {
		return hlist.get(userpassword);
	}
	
	public Person getPerson(int i) {
		return null;
	}
	
	
	public Person find(String s) {
		for(int i = 0; i < alist.size(); i++) {
			if(alist.get(i).getUser() == s) {
				return alist.get(i);
			}
		}
		
		return null;
	}
	
	public void update(Person p) {
		hlist.remove(p.getHash());
		
		for(int i = 0; i < alist.size(); i++) {
			if(alist.get(i).getHash() == p.getHash()) {
				alist.remove(i);
				break;
			}
		}
		
		addPerson(p);
	}
	
	
	public double getTotal() {
		grandTotal = 0;
		for(int i = 0; i < alist.size(); i++) {
			grandTotal += alist.get(i).getAccount().getBalance();
		}
		return grandTotal;
	}
	
	public int getSize() {
		return alist.size();
	}
	
	public int getNum() {
		return inplay.getNew();
	}
	
	public ArrayList getList() {
		
		ArrayList<String> names = new ArrayList<String>();
		
		for(int i = 0; i < alist.size(); i++) {
			names.add(alist.get(i).getUser());
		}

		return names;
	}
	
	public void sumUp() {
		
	}
	

	Hashtable<String, Person> hlist = new Hashtable<String, Person>();
	ArrayList<Person> alist = new ArrayList();
	RandomNums inplay = new RandomNums();
	double grandTotal;
	
}
