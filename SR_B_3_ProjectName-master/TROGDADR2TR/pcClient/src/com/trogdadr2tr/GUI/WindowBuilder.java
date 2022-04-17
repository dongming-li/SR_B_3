package com.trogdadr2tr.GUI;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import com.trogdadr2tr.database.DatabaseConnector;

/** Make a window and prompt user to play a game.
 * 
 * @author Colt Rogness */
public class WindowBuilder extends JFrame
{

	/** WindowBuilding MK I */
	private static final long serialVersionUID = 1L;

	/** Launch the application.
	 * 
	 * @param args - command line arguments */
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{

			@Override
			public void run()
			{
				WindowBuilder frame = new WindowBuilder();
				// frame.setVisible(true);
				frame.buildMainScreen();
			}
		});
	}

	/** Build a frame, set it to fullscreen, set it to be
	 * visible. */
	public WindowBuilder()
	{

		// buttons = new ArrayList<JButton>();
		// make the program end when the window is closed
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// removes the borders and buttons on the top of the
		// window
		setUndecorated(true);
		// make the window fullscreen
		setExtendedState(Frame.MAXIMIZED_BOTH);
		// setBounds(0, 0, 1920, 1040); // old size setting

		JPanel cp = new JPanel();
		setContentPane(cp);
		getContentPane().setLayout(null);
		setVisible(true);
		// JLabel label = new JLabel("Hello, World!");
		// label.setBounds(1, 1, 90, 20);
		// label.setBorder(new LineBorder(Color.BLACK));
		// contentPane.add(label);

	}

	/** Set up the starting screen. Will have Play, Map Editor,
	 * Credits, and Exit Buttons. */
	public void buildMainScreen()
	{
		class mainScreenActionListener implements ActionListener
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				switch (e.getActionCommand())
				{
					case "Play":
						// buildPlayScreen();
						buildLoginScreen();
						// LoginPage login = new
						// LoginPage(WindowBuilder.this);
						break;
					case "Map Editor":
						buildMapEditorScreen();
						break;
					case "Options":
						buildOptionsScreen();
						break;
					case "Credits":
						buildCreditsScreen();
						break;
					case "Exit":
						System.exit(0);
				}
			}

		}
		ArrayList<JButton> buttons = new ArrayList<JButton>();
		JButton button;

		clear();

		// make a new button
		// set the action command
		// add the button to buttons array

		button = new JButton("Play");
		button.setActionCommand("Play");
		button.setToolTipText("Host a new game, or join an existing one.");
		buttons.add(button);

		button = new JButton("Map Editor");
		button.setActionCommand("Map Editor");
		button.setToolTipText("Create and edit maps.");
		buttons.add(button);

		button = new JButton("Options");
		button.setActionCommand("Options");
		button.setToolTipText(
				"Change game options, graphical and audio settings, and key bindings.");
		buttons.add(button);

		button = new JButton("Credits");
		button.setActionCommand("Credits");
		button.setToolTipText("Who we are and what we do.");
		buttons.add(button);

		button = new JButton("Exit");
		button.setActionCommand("Exit");
		button.setToolTipText("Get out while you can.");
		buttons.add(button);

		// for each button:
		// set the bounds
		// add it to the content plane
		// add the action listener
		for (int n = 0; n < buttons.size(); n++)
		{
			buttons.get(n).setBounds((getWidth() - getWidth() / 6) / 2,
					(getHeight() - buttons.size() * getHeight() / 18) / 2
							+ getHeight() / 18 * n,
					getWidth() / 6,
					getHeight() / 18);
			buttons.get(n).addActionListener(new mainScreenActionListener());
			getContentPane().add(buttons.get(n));
		}

	}

	/** Contains the username as a String */
	private JTextField username;

	/** Contains the password as a String */
	private JTextField password;

	/** Set up login sub-screen, with login credentials, which
	 * leads to the play screen. */
	private void buildLoginScreen()
	{
		class loginScreenActionListener implements ActionListener
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				switch (e.getActionCommand())
				{
					case "Login":
						System.out.println(username.getText()); // This
																// is
																// the
																// string
																// in
																// the
																// username
																// box
						System.out.println(password.getText()); // This
																// is
																// the
																// string
																// in
																// the
																// password
																// box
						// TODO login and go to play screen
						// Client.getInstance().updater.login(username.getText(),
						// password.getText());
						buildPlayScreen();
						break;
					case "Back":
						buildMainScreen();
						break;
				}
			}
		}

		clear();

		username = new JTextField("Username");
		username.setBounds((getWidth() - getWidth() / 4) / 2,
				getHeight() / 2 - getHeight() / 9,
				getWidth() / 4,
				getHeight() / 18);
		getContentPane().add(username);
		password = new JTextField("Password");
		password.setBounds((getWidth() - getWidth() / 4) / 2,
				getHeight() / 2 - getHeight() / 18,
				getWidth() / 4,
				getHeight() / 18);
		getContentPane().add(password);

		JButton login = new JButton("Login");
		login.setBounds((getWidth() - getWidth() / 6) / 2,
				(getHeight() - 2 * getHeight() / 18) / 2
						+ getHeight() / 18,
				getWidth() / 6,
				getHeight() / 18);
		login.setActionCommand("Login");
		login.setToolTipText("Check username and password for authenticity");
		login.addActionListener(new loginScreenActionListener());
		getContentPane().add(login);

		JButton back = new JButton("Back");
		back.setBounds((getWidth() - getWidth() / 6) / 2,
				(getHeight() - 2 * getHeight() / 18) / 2 + getHeight() / 18 * 2,
				getWidth() / 6,
				getHeight() / 18);
		back.setActionCommand("Back");
		back.setToolTipText("Return to the main page");
		back.addActionListener(new loginScreenActionListener());
		getContentPane().add(back);

	}

	/** Set up the play sub-screen, with host game, join Game, and
	 * back. */
	private void buildPlayScreen()
	{
		class playScreenActionListener implements ActionListener
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				switch (e.getActionCommand())
				{
					case "Host Game":
						new LobbyScreen(WindowBuilder.this);
						break;
					case "Join Game":
						break;
					case "Back":
						buildMainScreen();
				}
			}

		}
		ArrayList<JButton> buttons = new ArrayList<JButton>();
		JButton button;

		clear();

		// make a new button
		// set the action command
		// add the button to buttons array

		button = new JButton("Host Game");
		button.setActionCommand("Host Game");
		button.setToolTipText("Host a new game, and invite other players.");
		buttons.add(button);

		// button = new JButton("Join Game");
		// button.setActionCommand("Join Game");
		// button.setToolTipText("Join an already existing
		// game.");
		// buttons.add(button);

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
			buttons.get(n).setBounds((getWidth() - getWidth() / 6) / 2,
					(getHeight() - buttons.size() * getHeight() / 18) / 2
							+ getHeight() / 18 * n,
					getWidth() / 6,
					getHeight() / 18);
			buttons.get(n).addActionListener(new playScreenActionListener());
			getContentPane().add(buttons.get(n));
		}

	}

	/** Set up Map Editor sub-screen, with new map, load map, and
	 * back. */
	private void buildMapEditorScreen()
	{
		JTextField text = new JTextField(50);
		class mapEditorScreenActionListener implements ActionListener
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				switch (e.getActionCommand())
				{
					case "New Map":
						new MapEditorGUI(WindowBuilder.this);
						break;
					case "Load Map":
						try
						{
							DatabaseConnector db = new DatabaseConnector();
							new MapEditorGUI(WindowBuilder.this,
									db.loadMap(text.getText()));
						}
						catch (SQLException t)
						{
							System.out.println(t);
						}
						break;
					case "Back":
						buildMainScreen();
				}
			}

		}
		ArrayList<JButton> buttons = new ArrayList<JButton>();
		JButton button;

		clear();

		// make a new button
		// set the action command
		// add the button to buttons array

		button = new JButton("New Map");
		button.setActionCommand("New Map");
		button.setToolTipText("Create a new map.");
		buttons.add(button);

		button = new JButton("Load Map");
		button.setActionCommand("Load Map");
		button.setToolTipText("Load a pre-existing map.");
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
			buttons.get(n).setBounds((getWidth() - getWidth() / 6) / 2,
					(getHeight() - buttons.size() * getHeight() / 18) / 2
							+ getHeight() / 18 * n,
					getWidth() / 6,
					getHeight() / 18);
			buttons.get(n).addActionListener(new mapEditorScreenActionListener());
			getContentPane().add(buttons.get(n));
		}
		JPanel textContainer = new JPanel();
		// getContentPane().add(text);
		textContainer.setBounds(855, 640, 200, 100);
		textContainer.add(text);
		textContainer.setVisible(true);
		getContentPane().add(textContainer);
	}

	/** Set up Options sub-screen, with graphics, audio, controls,
	 * other, and back. */
	private void buildOptionsScreen()
	{
		class OptionScreenActionListener implements ActionListener
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				switch (e.getActionCommand())
				{
					case "Graphics":
						break;
					case "Audio":
						break;
					case "Controls":
						break;
					case "Other":
						break;
					case "Back":
						buildMainScreen();
				}
			}

		}
		ArrayList<JButton> buttons = new ArrayList<JButton>();
		JButton button;

		clear();

		// make a new button
		// set the action command
		// add the button to buttons array

		button = new JButton("Graphics");
		button.setActionCommand("Graphics");
		button.setToolTipText("Edit display options.");
		buttons.add(button);

		button = new JButton("Audio");
		button.setActionCommand("Audio");
		button.setToolTipText("Edit audio options.");
		buttons.add(button);

		button = new JButton("Controls");
		button.setActionCommand("Controls");
		button.setToolTipText("Edit key bindings and mouse sensitivity.");
		buttons.add(button);

		button = new JButton("Other");
		button.setActionCommand("Other");
		button.setToolTipText("Edit misc. options.");
		buttons.add(button);

		button = new JButton("Back");
		button.setActionCommand("Back");
		button.setToolTipText("Go back to the previous menu.");
		buttons.add(button);

		// for each button:
		// set the bounds
		// add it to the content plane
		// add the action listener
		for (int n = 0; n < buttons.size(); n++)
		{
			buttons.get(n).setBounds((getWidth() - getWidth() / 6) / 2,
					(getHeight() - buttons.size() * getHeight() / 18) / 2
							+ getHeight() / 18 * n,
					getWidth() / 6,
					getHeight() / 18);
			buttons.get(n).addActionListener(new OptionScreenActionListener());
			getContentPane().add(buttons.get(n));
		}
	}

	/** Set up Credits sub-screen, with all our names and famous
	 * quotes and stuff. */
	private void buildCreditsScreen()
	{
		class mainScreenActionListener implements ActionListener
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				switch (e.getActionCommand())
				{
					case "Back":
						buildMainScreen();
				}
			}

		}
		int paneHeight = 500;
		int paneWidth = 500;
		JButton button;
		JPanel subWindow;

		clear();

		subWindow = new JPanel();
		subWindow.setBounds((getWidth() - paneWidth) / 2, (getHeight() - paneHeight) / 2,
				paneWidth, paneHeight);
		subWindow.setBorder(new LineBorder(Color.BLACK));
		subWindow.setLayout(null);
		getContentPane().add(subWindow);

		// make a new button
		// set the action command
		// set mouse-over text
		// add the button to buttons array

		button = new JButton("Back");
		button.setActionCommand("Back");
		button.setToolTipText("Get out while you can.");

		// set the bounds
		// add it to the content plane
		// add the action listener
		button.setBounds((subWindow.getWidth() - getWidth() / 6 / 3 - 20),
				(subWindow.getHeight() - getHeight() / 18 / 3 - 20), getWidth() / 6 / 3,
				getHeight() / 18 / 3);
		button.addActionListener(new mainScreenActionListener());
		subWindow.add(button);
	}

	/** Clean the screen so something else can be displayed. */
	public void clear()
	{
		// remove everything from the content pane
		getContentPane().removeAll();
		// do a magic thing that invalidates old contents
		getContentPane().revalidate();
		// paint over where the old contents were
		getContentPane().repaint();
	}
}
