import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;


public class ServerThread implements Runnable {
	
	public ServerThread(Socket connection, Database base) {
		try {
			this.connection = connection;
			output = new ObjectOutputStream(connection.getOutputStream());
			input = new ObjectInputStream(connection.getInputStream());
			this.base = base;
		} catch(Exception e) { 
			e.printStackTrace();
		};
	}
	
	public void run() {
		Boolean flag = true;
		Object o;
		while(flag) {
			try {
				o = input.readObject();
				if(o instanceof String[]) {
					recieve((String[]) o);
				}
				else if(o instanceof Integer) {
					recieve((Integer) o);
				}
				else if(o instanceof String) {
					recieve((String) o);
				}	
				else if(o instanceof Boolean) {
					input.close();
					output.close();
					connection.close();
					flag = false;
				}
			} catch(Exception e) { };
		} 
	}
	
	public void recieve(String[] info) throws IOException, ClassNotFoundException {
		Person p = new Person(info[0], info[1], base.getNum(), info[2], info[3], info[4]);
		base.addPerson(p); 
	}
	
	public void recieve(int n) throws IOException {
		if(n > 0) {
			person.getAccount().deposit(n);
			sendPerson(person);
		}
		else if(n < 0) {
			person.getAccount().withdraw(n*-1);
			sendPerson(person);
		}
		else { }
		
	}
	
	public void recieve(String s) throws IOException, ClassNotFoundException {
		if(base.hasPerson(s)) {
			sendPerson(base.getPerson(s));
			person = base.getPerson(s);
		}
	}
	
	private void sendPerson(Person p) {
		try {
			output.writeObject(p);
			output.flush();
		} catch(IOException ioException) { };
	}
	
	private void closeDown() {
		try {
			connection.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private Socket connection;
	private ObjectOutputStream output;
	private ObjectInputStream input;
	
	private ArrayList<Person> newAccounts;
	private Database base;
	private Person person;
}
