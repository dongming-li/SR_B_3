import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

public class LoginPage extends JFrame{
	
	public LoginPage(Container screen){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// removes the borders and buttons on the top of the window
		setUndecorated(true);
		// make the window fullscreen
		// setExtendedState(JFrame.MAXIMIZED_BOTH);
		setBounds(0, 0, 1920, 1040); // old size setting

//		JPanel loginScreen = new JPanel();
//		loginScreen.setLayout(new BoxLayout(loginScreen, BoxLayout.PAGE_AXIS));
//		setContentPane(loginScreen);
		// remove everything from the content pane
		getContentPane().removeAll();
		// do a magic thing that invalidates old contents
		getContentPane().revalidate();
		// paint over where the old contents were
		getContentPane().repaint();
		
		getContentPane().setLayout(null);
		setVisible(true);
		buildLoginScreen();
		System.out.println("Built Login Screen");
	}
	
	public void buildLoginScreen() {
		JPanel viewContainer = new JPanel();
		
		JTextField username = new JTextField(20);
		JPasswordField password = new JPasswordField(20);
		viewContainer.setBounds(860, 300, 200, 75);
		
		class buildScreenActionListener implements ActionListener {
			@Override
			public void actionPerformed(ActionEvent e) {
				switch (e.getActionCommand()) {
				case "Submit Login":
					System.out.println("Submit Login Button was Pressed");
					submitLogin(username.getText(), new String(password.getPassword()));
					break;
				case "Back":
					System.out.println("Back button was pressed.");
					break;
				case "Create Profile":
					System.out.println("Create Profile button was pressed");
					buildCreateProfileScreen();
					break;
				}
			}
		}
		
		// remove everything from the content pane
		getContentPane().removeAll();
		// do a magic thing that invalidates old contents
		getContentPane().revalidate();
		// paint over where the old contents were
		getContentPane().repaint();

		// make a new button
		// set the action command
		// add the button to buttons array
		
		int buttonHeight = 60;
		int buttonWidth = 300;
		ArrayList<JButton> buttons = new ArrayList<JButton>();
		JButton button;


		button = new JButton("Submit Login");
		button.setActionCommand("Submit Login");
		button.setToolTipText("Submit Login Info To Database");
		buttons.add(button);
		
		button = new JButton("Create Profile");
		button.setActionCommand("Create Profile");
		button.setToolTipText("Create a Brand New Profile");
		buttons.add(button);

		button = new JButton("Back");
		button.setActionCommand("Back");
		button.setToolTipText("Get out while you can.");
		buttons.add(button);

		// for each button:
		// set the bounds
		// add it to the content plane
		// add the action listener
		for (int n = 0; n < buttons.size(); n++) {
			buttons.get(n).setBounds((getWidth() - buttonWidth) / 2, (getHeight() - buttons.size() * buttonHeight) / 2 + buttonHeight * n, buttonWidth, buttonHeight);
			buttons.get(n).addActionListener(new buildScreenActionListener());
			getContentPane().add(buttons.get(n));
		}
		
		viewContainer.setLayout(new BoxLayout(viewContainer, BoxLayout.PAGE_AXIS));
		
		JLabel userLabel = new JLabel("Username");
		JLabel passLabel = new JLabel("Password");
		
		viewContainer.add(userLabel);
		viewContainer.add(username);
		viewContainer.add(passLabel);
		viewContainer.add(password);
		
		getContentPane().add(viewContainer);
	}
	
	public void buildCreateProfileScreen() {
		JPanel viewContainer = new JPanel();
		
		JTextField username = new JTextField(20);
		JPasswordField password = new JPasswordField(20);
		viewContainer.setBounds(860, 300, 200, 75);
		
		class buildScreenActionListener implements ActionListener {
			@Override
			public void actionPerformed(ActionEvent e) {
				switch (e.getActionCommand()) {
				case "Submit Profile":
					System.out.println("Submit Login Button was Pressed");
					submitProfile(username.getText(), new String(password.getPassword()));
					break;
				case "Back":
					System.out.println("Back button was pressed.");
					buildLoginScreen();
					break;
				}
			}
		}
		
		// remove everything from the content pane
		getContentPane().removeAll();
		// do a magic thing that invalidates old contents
		getContentPane().revalidate();
		// paint over where the old contents were
		getContentPane().repaint();

		// make a new button
		// set the action command
		// add the button to buttons array
		
		int buttonHeight = 60;
		int buttonWidth = 300;
		ArrayList<JButton> buttons = new ArrayList<JButton>();
		JButton button;


		button = new JButton("Submit Profile");
		button.setActionCommand("Submit Profile");
		button.setToolTipText("Submit Profile Info To Database");
		buttons.add(button);

		button = new JButton("Back");
		button.setActionCommand("Back");
		button.setToolTipText("Get out while you can.");
		buttons.add(button);

		// for each button:
		// set the bounds
		// add it to the content plane
		// add the action listener
		for (int n = 0; n < buttons.size(); n++) {
			buttons.get(n).setBounds((getWidth() - buttonWidth) / 2, (getHeight() - buttons.size() * buttonHeight) / 2 + buttonHeight * n, buttonWidth, buttonHeight);
			buttons.get(n).addActionListener(new buildScreenActionListener());
			getContentPane().add(buttons.get(n));
		}
		
		viewContainer.setLayout(new BoxLayout(viewContainer, BoxLayout.PAGE_AXIS));
		
		JLabel userLabel = new JLabel("Username");
		JLabel passLabel = new JLabel("Password");
		
		viewContainer.add(userLabel);
		viewContainer.add(username);
		viewContainer.add(passLabel);
		viewContainer.add(password);
		
		getContentPane().add(viewContainer);
	}
	
	private void submitLogin(String username, String password) {
		MysqlDataSource database = connectToDatabase();
		String command = "SELECT * FROM Users WHERE Username =\'" + username + "\' AND Password = \'" + password + "\'";
		try {
			Connection conn = database.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(command);
			if(rs.next() != false) {
				System.out.println("Correct Login");
//				getContentPane().add(new JPanel().add(new JLabel("Correct Login")));
				JOptionPane.showMessageDialog(getContentPane(), "Login Succesful!");
			}
			else {
				System.out.println("Incorrect Login");
				JOptionPane.showMessageDialog(getContentPane(), "Login Unsuccesful!");
			}
		}
		catch(SQLException e) {
			System.out.println(e);
		}
	}
	
	private void submitProfile(String username, String password) {
		MysqlDataSource database = connectToDatabase();
		String command = "INSERT INTO Users(Username, Password) VALUES (\'" + username + "\', \'" + password + "\')";
		System.out.println(command);
		try {
			Connection conn = database.getConnection();
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(command);
		}
		catch(SQLException e) {
			System.out.println(e);
		}
	}
	
	private MysqlDataSource connectToDatabase() {
		MysqlDataSource dataSource = new MysqlDataSource();
		dataSource.setUser("dbu309srb3");
		dataSource.setPassword("GaTax1EX");
		dataSource.setServerName("mysql.cs.iastate.edu");
		dataSource.setDatabaseName("db309srb3");
		try {
			Connection conn = dataSource.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Users");	
			while(rs.next()) {
				System.out.println(rs.getString("Username"));
				System.out.println(rs.getString("Password"));
			}
		}
		catch(SQLException e) {
			System.out.print(e);
		}
		return dataSource;
	}
}
