package com.trogdadr2tr.GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 * Login Page which can submit login information or create a profile on the database.
 * @author Nick Schmidt
 *
 */
public class LoginPage extends JFrame
{

	/** Yeah java */
	private static final long serialVersionUID = 1L;

	/**
	 * Sets up the LoginPage 
	 * @param screen The WindowBuilder this screen is created in
	 */
	public LoginPage(WindowBuilder screen)
	{
		screen.clear();
		screen.setLayout(null);
		buildLoginScreen(screen);
	}

	/**
	 * Builds the login screen on top of screen
	 * @param screen The WindowBuilder the login screen is built on
	 */
	public void buildLoginScreen(WindowBuilder screen)
	{
		JPanel viewContainer = new JPanel();

		JTextField username = new JTextField(20);
		JPasswordField password = new JPasswordField(20);
		viewContainer.setBounds(860, 300, 200, 75);

		class buildScreenActionListener implements ActionListener
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				switch (e.getActionCommand())
				{
					case "Submit Login":
						// submitLogin(username.getText(), new
						// String(password.getPassword()));
						break;
					case "Back":
						screen.buildMainScreen();
						break;
					case "Create Profile":
						buildCreateProfileScreen(screen);
						break;
				}
			}
		}
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
		for (int n = 0; n < buttons.size(); n++)
		{
			buttons.get(n).setBounds((screen.getWidth() - buttonWidth) / 2,
					(screen.getHeight() - buttons.size() * buttonHeight) / 2
							+ buttonHeight * n,
					buttonWidth,
					buttonHeight);
			buttons.get(n).addActionListener(new buildScreenActionListener());
			screen.add(buttons.get(n));
		}

		viewContainer.setLayout(new BoxLayout(viewContainer, BoxLayout.PAGE_AXIS));

		JLabel userLabel = new JLabel("Username");
		JLabel passLabel = new JLabel("Password");

		viewContainer.add(userLabel);
		viewContainer.add(username);
		viewContainer.add(passLabel);
		viewContainer.add(password);

		screen.add(viewContainer);
	}

	/**
	 * Builds the screen to create a new profile
	 * @param screen The WindowBuilder this screen is built on top of
	 */
	public void buildCreateProfileScreen(WindowBuilder screen)
	{
		JPanel viewContainer = new JPanel();

		JTextField username = new JTextField(20);
		JPasswordField password = new JPasswordField(20);
		viewContainer.setBounds(860, 300, 200, 75);

		class buildScreenActionListener implements ActionListener
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				switch (e.getActionCommand())
				{
					case "Submit Profile":
						// submitProfile(username.getText(), new
						// String(password.getPassword()));
						break;
					case "Back":
						buildLoginScreen(screen);
						break;
				}
			}
		}

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
		for (int n = 0; n < buttons.size(); n++)
		{
			buttons.get(n).setBounds((getWidth() - buttonWidth) / 2,
					(getHeight() - buttons.size() * buttonHeight) / 2 + buttonHeight * n,
					buttonWidth, buttonHeight);
			buttons.get(n).addActionListener(new buildScreenActionListener());
			screen.add(buttons.get(n));
		}

		viewContainer.setLayout(new BoxLayout(viewContainer, BoxLayout.PAGE_AXIS));

		JLabel userLabel = new JLabel("Username");
		JLabel passLabel = new JLabel("Password");

		viewContainer.add(userLabel);
		viewContainer.add(username);
		viewContainer.add(passLabel);
		viewContainer.add(password);

		screen.add(viewContainer);
	}
}
