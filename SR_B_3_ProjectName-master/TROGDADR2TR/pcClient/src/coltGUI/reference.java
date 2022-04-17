package coltGUI;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.border.LineBorder;

import com.trogdadr2tr.socket.Client;

public class reference extends JFrame
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void loginScreen()
	{
		clear();

		JPanel loginBox = new JPanel();

		JTextField username = new JTextField(20);
		loginBox.add(username);
		JLabel userLabel = new JLabel("Username");
		loginBox.add(userLabel);

		JPasswordField password = new JPasswordField(20);
		loginBox.add(password);
		JLabel passLabel = new JLabel("Password");
		loginBox.add(passLabel);

		JButton loginButton = new JButton("Login");
		loginButton.setActionCommand("Login");
		loginButton.setToolTipText("Login to the game server.");
		loginButton.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent actionEvent)
			{
				try
				{
					Client.getInstance().updater.login(username.getText(),
							new String(password.getPassword()));
				}
				catch (NullPointerException e)
				{
					// if the fields are blank, do nothing.
				}
			}
		});
		loginBox.add(loginButton);

		JButton profileButton = new JButton("Create Profile");
		profileButton.setActionCommand("Create Profile");
		profileButton.setToolTipText("Make a new user profile.");
		profileButton.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				// TODO - make a profile
				System.err.println("You can't make new profiles yet.");
			}
		});
		loginBox.add(profileButton);

		JButton backButton = new JButton("Back");
		backButton.setActionCommand("Back");
		backButton.setToolTipText("Get out while you can.");
		backButton.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
			}

		});
		loginBox.add(backButton);

		SpringLayout subLayout = new SpringLayout();

		// userLabel under profileButton, left side of loginBox
		subLayout.putConstraint(SpringLayout.NORTH, userLabel, 10,
				SpringLayout.NORTH, loginBox);
		subLayout.putConstraint(SpringLayout.WEST, userLabel, 10,
				SpringLayout.WEST, loginBox);

		// username left of userLabel
		subLayout.putConstraint(SpringLayout.NORTH, username, 0,
				SpringLayout.NORTH, userLabel);
		subLayout.putConstraint(SpringLayout.WEST, username, 5,
				SpringLayout.EAST, userLabel);

		// passLabel below userLabel
		subLayout.putConstraint(SpringLayout.NORTH, passLabel, 10,
				SpringLayout.SOUTH, userLabel);
		subLayout.putConstraint(SpringLayout.WEST, passLabel, 0,
				SpringLayout.WEST, userLabel);

		// password left of passLabel
		subLayout.putConstraint(SpringLayout.NORTH, password, 0,
				SpringLayout.NORTH, passLabel);
		subLayout.putConstraint(SpringLayout.WEST, password, 0,
				SpringLayout.WEST, username);

		// back button in bottom right corner of loginBox
		subLayout.putConstraint(SpringLayout.NORTH, backButton, 10,
				SpringLayout.SOUTH, password);
		subLayout.putConstraint(SpringLayout.EAST, backButton, -10,
				SpringLayout.EAST, loginBox);

		// profileButton left of backButton
		subLayout.putConstraint(SpringLayout.NORTH, profileButton, 0,
				SpringLayout.NORTH, backButton);
		subLayout.putConstraint(SpringLayout.EAST, profileButton, -10,
				SpringLayout.WEST, backButton);

		// login button left of profileButton
		subLayout.putConstraint(SpringLayout.NORTH, loginButton, 0,
				SpringLayout.NORTH, backButton);
		subLayout.putConstraint(SpringLayout.EAST, loginButton, -10,
				SpringLayout.WEST, profileButton);

		// set loginBox to fit it's elements
		subLayout.putConstraint(SpringLayout.EAST, loginBox, 50,
				SpringLayout.EAST, username);
		subLayout.putConstraint(SpringLayout.SOUTH, loginBox, 10,
				SpringLayout.SOUTH, backButton);

		loginBox.setBorder(new LineBorder(Color.BLACK));
		loginBox.setLayout(subLayout);
		getContentPane().add(loginBox);

		// loginBox centered on the screen
		SpringLayout mainLayout = new SpringLayout();
		mainLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, loginBox, 0,
				SpringLayout.HORIZONTAL_CENTER, getContentPane());
		mainLayout.putConstraint(SpringLayout.VERTICAL_CENTER, loginBox, 0,
				SpringLayout.VERTICAL_CENTER, getContentPane());

		getContentPane().setLayout(mainLayout);
		repaint();
	}

	private void clear()
	{
		// TODO Auto-generated method stub

	}

}
