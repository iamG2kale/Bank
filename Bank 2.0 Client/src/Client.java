import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.Console;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class Client extends JFrame {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new Client();
			}
		});
	}

	// set up frame details
	public Client() {
		super("Client");

		setSize(500, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setVisible(true);
		
		addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                flag = false;
            }
        });

		panel = new JPanel();
		add(panel);

		launch();
	}

	// init frame with components
	public void launch() {

		panel.removeAll();

		panel.setLayout(new FlowLayout());

		// background picture
		JLabel background = new JLabel();
		background.setIcon(new ImageIcon(getClass().getResource("anime.png")));
		background.setLayout(new BorderLayout());

		// transparant panel
		JPanel top = new JPanel();
		top.setLayout(new FlowLayout());
		top.setOpaque(false);

		final JLabel user = new JLabel("Username:");
		top.add(user);

		final JTextField username = new JTextField();
		username.setColumns(7);
		top.add(username);

		final JLabel pass = new JLabel("Password:");
		top.add(pass);

		final JPasswordField password = new JPasswordField();
		password.setColumns(7);
		top.add(password);

		background.add(top, BorderLayout.NORTH);

		JPanel bottom = new JPanel();
		bottom.setLayout(new FlowLayout());
		bottom.setOpaque(false);

		bottom.add(new JLabel());

		bottom.add(new JButton(new AbstractAction("Login") {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				sendUserPass(username.getText() + password.getText());
				System.out.println(username.getText() + password.getText());
	
			}
		}));

		bottom.add(new JButton(new AbstractAction("Register") {
			public void actionPerformed(ActionEvent e) {
				registerScreen();
			}
		}));


		background.add(bottom, BorderLayout.CENTER);

		panel.add(background, BorderLayout.NORTH);

		panel.updateUI();

	}

	private void loginScreen() {

		panel.removeAll();

		panel.setLayout(new BorderLayout());

		JLabel background = new JLabel();
		background.setIcon(new ImageIcon(getClass().getResource("anime.png")));
		background.setLayout(new BorderLayout());

		final JPanel content = new JPanel();
		content.setOpaque(false);
		content.setLayout(new FlowLayout(0, 10, 170));
		content.add(new JLabel("Current Balance:", SwingConstants.CENTER));
		content.add(new JLabel("Rs" + person.getAccount().getBalance()));
		background.add(content, BorderLayout.CENTER);

		JPanel tabs = new JPanel();
		GridLayout grid = new GridLayout(1, 4);
		grid.setHgap(15);
		tabs.setLayout(grid);
		tabs.setOpaque(false);
		tabs.add(new JButton(new AbstractAction("Balance") {
			public void actionPerformed(ActionEvent ae) {
				content.removeAll();

				content.add(new JLabel("Current Balance:",SwingConstants.CENTER));
				content.add(new JLabel("Rs" + person.getAccount().getBalance() + ""));

				content.updateUI();
			}
		}));
		tabs.add(new JButton(new AbstractAction("Deposit") {
			public void actionPerformed(ActionEvent ae) {
				content.removeAll();

				content.add(new JLabel("Amount:"));
				final JTextField money = new JTextField(10);
				content.add(money);
				content.add(new JButton(new AbstractAction("Deposit") {
					public void actionPerformed(ActionEvent ae) {
						try {
							int amount = Integer.parseInt(money.getText());
							output.writeObject(amount);
							person.getAccount().deposit(amount);
						} catch (IOException ioException) { };
					}
				}));

				content.updateUI();
			}
		}));
		tabs.add(new JButton(new AbstractAction("Withdraw") {
			public void actionPerformed(ActionEvent ae) {
				content.removeAll();

				content.add(new JLabel("Amount:"));
				final JTextField money = new JTextField(10);
				content.add(money);
				content.add(new JButton(new AbstractAction("Withdraw") {
					public void actionPerformed(ActionEvent ae) {
						try {
							int amount = Integer.parseInt(money.getText());
							output.writeObject(amount*-1);
							person.getAccount().withdraw(amount);
						} catch (IOException ioException) { };
					}
				}));

				content.updateUI();
			}
		}));
		tabs.add(new JButton(new AbstractAction("Logout") {
			public void actionPerformed(ActionEvent ae) {
				content.removeAll();

				content.add(new JButton(new AbstractAction("Logout") {
					public void actionPerformed(ActionEvent ae) {
						person = null;
						launch();
					}
				}));

				content.updateUI();
				
			}
		}));
		background.add(tabs, BorderLayout.NORTH);

		panel.add(background, BorderLayout.CENTER);
		panel.updateUI();
	}

	private void registerScreen() {

		panel.removeAll();

		panel.setLayout(new BorderLayout());

		JLabel background = new JLabel();
		background.setIcon(new ImageIcon(getClass().getResource("anime.png")));
		background.setVisible(false);

		// Information form components

		background.setVisible(false);
		GridLayout grid = new GridLayout(0, 2, 1, 1);
		grid.setHgap(10);
		grid.setVgap(40);
		background.setLayout(grid);

		background.add(new JLabel("First Name:"));
		final TextField fn = new TextField();
		background.add(fn);

		background.add(new JLabel("Last Name:"));
		final TextField ln = new TextField();
		background.add(ln);

		background.add(new JLabel("Account Name:"));
		final JComboBox an = new JComboBox();
		an.addItem("Checkings");
		an.addItem("Savings");
		background.add(an);

		background.add(new JLabel("Username:"));
		final TextField un = new TextField();
		background.add(un);

		background.add(new JLabel("Password"));
		final JPasswordField pwd1 = new JPasswordField();
		background.add(pwd1);

		background.add(new JLabel("Verify Password"));
		final JPasswordField pwd2 = new JPasswordField();
		background.add(pwd2);

		final JButton status = new JButton("Back");
		status.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				launch();
			}
		});
		background.add(status);

		JButton create = new JButton("Create");
		create.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent ae) {
				if (pwd1.getText().equals(pwd2.getText())) {
				
					String[] temp = new String[5];
			
					temp[0]=fn.getText();
					temp[1]=ln.getText();
					temp[2]=an.getSelectedItem().toString();
					temp[3]=un.getText();
					temp[4]=pwd1.getText();
							
					
					sendInfo(temp);
					
					launch();

				}
			}
		});
		background.add(create);

		background.setVisible(true);

		panel.add(background);
		panel.updateUI();
	}

	// connect to server
	public void startRunning() {
		
		
		//serverIP = "76.117.48.247";
		serverIP = "127.0.0.1";
		
		flag = true;

		try {
			connectToServer();
			setupStreams();
			whileConnected();
		} catch (EOFException eofException) {
			
		} catch (IOException ioException) {
			ioException.printStackTrace();
		} finally {
			closeDown();
		}
	}

	// connecting to a server
	private void connectToServer() throws IOException {
		connection = new Socket(InetAddress.getByName(serverIP), 1337);
	}

	// Setting up the stream
	private void setupStreams() throws IOException {
		output = new ObjectOutputStream(connection.getOutputStream());
		output.flush();
		input = new ObjectInputStream(connection.getInputStream());
	}

	// while chatting with server
	private void whileConnected() throws IOException {
		Object o;
		do {
			try {
				o = input.readObject();
				if(o instanceof Person) {
					person = (Person) o;
					loginScreen();
				}
			} catch (ClassNotFoundException classNotFoundException) { };
		} while(flag);
	}
	
	private void sendInfo(String[] info) {
		try {
			output.writeObject(info);
			output.flush();
		} catch(IOException ioException) {
			
		}
	}
	
	private void sendInt(int n) {
		try {
			output.writeObject(n);
			output.flush();
		} catch(IOException ioException) { }
	}
	
	private void sendUserPass(String userpass) {
		try {
			output.writeObject(userpass);
			output.flush();
		} catch(IOException ioException) { }
	}

	// close Stream and Socket
	private void closeDown() {
		try {
			System.out.println("Client closed program!");
			output.writeObject(false);
			input.close();
			output.close();
			connection.close();
			System.exit(0);
		} catch (IOException ioException) { };
	}

	
	
	// Frame variables
	private JPanel panel;
	private Person person = null;
	private Boolean flag;
	
	// Server variables
	private ServerSocket Server;
	private Socket connection;
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private String serverIP;
	
	

}
