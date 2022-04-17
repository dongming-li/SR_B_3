package coltGUI;

import java.awt.Color;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.border.LineBorder;

import com.trogdadr2tr.game.*;
import com.trogdadr2tr.socket.Client;

/** @author Colt Rogness */
public class WindowBuilder extends JFrame
{

	/** yeah java */
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
				frame.startMenuScreen();
				// frame.gameLobbyScreen(new GameRoom(new
				// Player(PlayerType.RTS), "Cancer"));
			}
		});
	}

	/** Build a frame, set it to fullscreen, set it to be
	 * visible. */
	public WindowBuilder()
	{
		// make the program end when the window is closed
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// removes the borders and buttons on the top of the
		// window
		setUndecorated(true);
		// make the window fullscreen
		setExtendedState(Frame.MAXIMIZED_BOTH);

		JPanel cp = new JPanel();
		cp.setLayout(null);
		setContentPane(cp);
		setVisible(true);
	}

	/** Set up the starting screen. Will have Play, Map Editor,
	 * Credits, and Exit Buttons. */
	public void startMenuScreen()
	{
		class mainScreenActionListener implements ActionListener
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				switch (e.getActionCommand())
				{
					case "Play":
						try
						{
							if (Client.getInstance() == null)
							{
								Client.init();
							}
							Client.getInstance()
									.addListener(new ClientGameLogic(WindowBuilder.this));
							clear();
						}
						catch (IOException e1)
						{
							startMenuScreen();
							// TODO - queue an error message about
							// the server.
						}
						break;
					case "Map Editor":
						mapEditorMenuScreen();
						break;
					case "Options":
						optionsMenuScreen();
						break;
					case "Credits":
						creditsMenuScreen();
						break;
					case "Exit":
						System.exit(0);
				}
			}

		}
		ArrayList<JButton> buttons = new ArrayList<JButton>();
		JButton button;

		clear();
		// getContentPane().setLayout(new
		// BoxLayout(getContentPane(), 1));

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
			// button.setAlignmentX(Component.CENTER_ALIGNMENT);
			buttons.get(n).addActionListener(new mainScreenActionListener());
			getContentPane().add(buttons.get(n));
		}
		repaint();

	}

	/** Set up the play sub-screen, with host game, join Game, and
	 * back. */
	public void playMenuScreen()
	{
		class playScreenActionListener implements ActionListener
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				switch (e.getActionCommand())
				{
					case "Host Game":
						Client.getInstance().updater.hostRoom("TODO");
						break;
					case "Join Game":
						break;
					case "Back":
						startMenuScreen();
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

		button = new JButton("Join Game");
		button.setActionCommand("Join Game");
		button.setToolTipText("Join an already existing game.");
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
			buttons.get(n).addActionListener(new playScreenActionListener());
			getContentPane().add(buttons.get(n));
		}
		repaint();
	}

	/** Set up Map Editor sub-screen, with new map, load map, and
	 * back. */
	public void mapEditorMenuScreen()
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
						break;
					case "Load Map":
						break;
					case "Back":
						startMenuScreen();
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
		textContainer.setBounds(0, 0, 100, 100);
		textContainer.add(text);
		textContainer.setVisible(true);
		getContentPane().add(textContainer);
		repaint();
	}

	/** Set up Options sub-screen, with graphics, audio, controls,
	 * other, and back. */
	public void optionsMenuScreen()
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
						startMenuScreen();
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
		repaint();
	}

	/** Set up Credits sub-screen, with all our names and famous
	 * quotes and stuff. */
	public void creditsMenuScreen()
	{
		class mainScreenActionListener implements ActionListener
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				switch (e.getActionCommand())
				{
					case "Back":
						startMenuScreen();
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
		repaint();
	}

	/** Set up Login screen, with options to log in to the server
	 * or make a new profile. */
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
				playMenuScreen();
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

	/** Set up Server select screen.
	 * 
	 * @param gameRooms */
	public void serverSelectScreen(ArrayList<GameRoom> gameRooms)
	{
		// TODO - display available servers with the number of
		// players
		repaint();
	}

	/** Set up a game lobby screen, in a attempted copy of Age of
	 * Empires II.
	 * 
	 * @param gameRoom */
	public void gameLobbyScreen(GameRoom gameRoom)
	{
		clear();
		Container contentPane = getContentPane();

		JPanel roomInfoBar = new JPanel();
		roomInfoBar.setBorder(new LineBorder(Color.BLACK));

		JTextField roomName = new JTextField(gameRoom.getRoomName());
		roomInfoBar.add(roomName);
		JTextField roomPassword =
				new JTextField((gameRoom.getUnsecurePassword() == null ? "No Password"
						: gameRoom.getUnsecurePassword()));
		roomInfoBar.add(roomPassword);

		SpringLayout roomInfoBarLayout = new SpringLayout();

		// roomName in the top/bottom left of roomInfoBar
		roomInfoBarLayout.putConstraint(SpringLayout.NORTH, roomName, 10,
				SpringLayout.NORTH, roomInfoBar);
		roomInfoBarLayout.putConstraint(SpringLayout.WEST, roomName, 10,
				SpringLayout.WEST, roomInfoBar);
		roomInfoBarLayout.putConstraint(SpringLayout.SOUTH, roomInfoBar, 10,
				SpringLayout.SOUTH, roomName);

		// roomPassword to the right of roomName
		roomInfoBarLayout.putConstraint(SpringLayout.NORTH, roomPassword, 10,
				SpringLayout.NORTH, roomInfoBar);
		roomInfoBarLayout.putConstraint(SpringLayout.WEST, roomPassword, 10,
				SpringLayout.EAST, roomName);

		// bind roomInfoBar to the right of roomPassword
		roomInfoBarLayout.putConstraint(SpringLayout.EAST, roomInfoBar, 10,
				SpringLayout.EAST, roomPassword);

		roomInfoBar.setLayout(roomInfoBarLayout);
		contentPane.add(roomInfoBar);

		// break

		JPanel playerWindow = new JPanel();
		playerWindow.setBorder(new LineBorder(Color.BLACK));

		SpringLayout playerWindowLayout = new SpringLayout();
		playerWindow.setLayout(playerWindowLayout);
		contentPane.add(playerWindow);

		// break

		JPanel chatWindow = new JPanel();
		chatWindow.setBorder(new LineBorder(Color.BLACK));

		JTextArea chatHistory = new JTextArea(10, 100);
		chatWindow.add(chatHistory);
		JTextField messageBar = new JTextField();
		chatWindow.add(messageBar);
		JButton sendButton = new JButton("Send");
		chatWindow.add(sendButton);

		SpringLayout chatWindowLayout = new SpringLayout();

		// chatHistory in top left corner of chatWindow
		chatWindowLayout.putConstraint(SpringLayout.NORTH, chatHistory, 10,
				SpringLayout.NORTH, chatWindow);
		chatWindowLayout.putConstraint(SpringLayout.WEST, chatHistory, 10,
				SpringLayout.WEST, chatWindow);

		// messageBar below chatHistory
		chatWindowLayout.putConstraint(SpringLayout.NORTH, messageBar, 10,
				SpringLayout.SOUTH, chatHistory);
		chatWindowLayout.putConstraint(SpringLayout.WEST, messageBar, 10,
				SpringLayout.WEST, chatWindow);
		// and to the left of sendButton
		chatWindowLayout.putConstraint(SpringLayout.EAST, messageBar, -10,
				SpringLayout.WEST, sendButton);

		// sendButton below chatHistory
		chatWindowLayout.putConstraint(SpringLayout.NORTH, sendButton, 10,
				SpringLayout.SOUTH, chatHistory);
		// and to the right edge of chatWindow
		chatWindowLayout.putConstraint(SpringLayout.EAST, sendButton, -10,
				SpringLayout.EAST, chatWindow);

		// bind chatWindow to the bottom of sendButton
		chatWindowLayout.putConstraint(SpringLayout.SOUTH, chatWindow, 10,
				SpringLayout.SOUTH, sendButton);
		// bind chatWindow to the right of chatHistory
		chatWindowLayout.putConstraint(SpringLayout.EAST, chatWindow, 10,
				SpringLayout.EAST, chatHistory);

		chatWindow.setLayout(chatWindowLayout);
		contentPane.add(chatWindow);

		// break

		JPanel settingsWindow = new JPanel();
		settingsWindow.setBorder(new LineBorder(Color.BLACK));

		SpringLayout settingsWindowLayout = new SpringLayout();

		settingsWindow.setLayout(settingsWindowLayout);
		contentPane.add(settingsWindow);

		// break

		JPanel bottomCorner = new JPanel();
		bottomCorner.setBorder(new LineBorder(Color.BLACK));

		JCheckBox readyButton = new JCheckBox("Ready Up");
		readyButton.setToolTipText("Are you ready to start the game?");
		readyButton.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent event)
			{
				Client.getInstance().updater.setReadiness(readyButton.isSelected());
			}

		});
		bottomCorner.add(readyButton);
		JButton startButton = new JButton("Start");
		startButton.setToolTipText("Start the Game!");
		startButton.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent event)
			{
				// TODO - start game method in socket connection
			}

		});
		bottomCorner.add(startButton);
		JButton cancelButton = new JButton("Cancel");
		cancelButton.setToolTipText("Leave and disolve this lobby.");
		cancelButton.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				Client.getInstance().updater.leaveRoom();
			}

		});
		bottomCorner.add(cancelButton);

		SpringLayout bottomCornerLayout = new SpringLayout();

		// cancelButton in bottom right
		bottomCornerLayout.putConstraint(SpringLayout.SOUTH, cancelButton, -10,
				SpringLayout.SOUTH, bottomCorner);
		bottomCornerLayout.putConstraint(SpringLayout.EAST, cancelButton, -10,
				SpringLayout.EAST, bottomCorner);

		// startButton to left of cancelButton
		bottomCornerLayout.putConstraint(SpringLayout.SOUTH, startButton, -10,
				SpringLayout.SOUTH, bottomCorner);
		bottomCornerLayout.putConstraint(SpringLayout.EAST, startButton, -10,
				SpringLayout.WEST, cancelButton);

		// readyButton to left of startButton
		bottomCornerLayout.putConstraint(SpringLayout.SOUTH, readyButton, -10,
				SpringLayout.SOUTH, bottomCorner);
		bottomCornerLayout.putConstraint(SpringLayout.EAST, readyButton, -10,
				SpringLayout.WEST, startButton);

		bottomCorner.setLayout(bottomCornerLayout);
		contentPane.add(bottomCorner);

		// break

		SpringLayout layout = new SpringLayout();

		// roomInfoBar in upper left corner of contentPane
		layout.putConstraint(SpringLayout.NORTH, roomInfoBar, 10,
				SpringLayout.NORTH, contentPane);
		layout.putConstraint(SpringLayout.WEST, roomInfoBar, 10,
				SpringLayout.WEST, contentPane);

		// playerWindow below roomInfoBar
		layout.putConstraint(SpringLayout.NORTH, playerWindow, 10,
				SpringLayout.SOUTH, roomInfoBar);
		layout.putConstraint(SpringLayout.WEST, playerWindow, 0,
				SpringLayout.WEST, roomInfoBar);

		// chatWindow below playerWindow
		layout.putConstraint(SpringLayout.NORTH, chatWindow, 10,
				SpringLayout.SOUTH, playerWindow);
		layout.putConstraint(SpringLayout.WEST, chatWindow, 0,
				SpringLayout.WEST, roomInfoBar);

		// settingsWindow right of roomInfoBar and PlayerWindow
		layout.putConstraint(SpringLayout.NORTH, settingsWindow, 0,
				SpringLayout.NORTH, roomInfoBar);
		layout.putConstraint(SpringLayout.WEST, settingsWindow, 10,
				SpringLayout.EAST, playerWindow);
		layout.putConstraint(SpringLayout.EAST, settingsWindow, -10,
				SpringLayout.EAST, contentPane);

		// bottomCorner below settingsWindow
		layout.putConstraint(SpringLayout.NORTH, bottomCorner, 10,
				SpringLayout.SOUTH, settingsWindow);
		layout.putConstraint(SpringLayout.EAST, bottomCorner, 0,
				SpringLayout.EAST, settingsWindow);
		// bottomCorner right of chatWindow
		layout.putConstraint(SpringLayout.WEST, bottomCorner, 10,
				SpringLayout.EAST, chatWindow);
		// bottomCorner at bottom of contentPane
		layout.putConstraint(SpringLayout.SOUTH, bottomCorner, -10,
				SpringLayout.SOUTH, contentPane);
		// bottomCornenr at right of contentPane

		// bind contentPane to bottom of chatWindow
		layout.putConstraint(SpringLayout.SOUTH, contentPane, 10,
				SpringLayout.SOUTH, chatWindow);

		contentPane.setLayout(layout);

		repaint();
	}

	/** Set up the in-game screen, in a generic copy of Age of
	 * Empires.
	 * 
	 * @param clientActor */
	public void inGameScreen(ClientGameLogic clientActor)
	{
		clear();
		Container contentPane = getContentPane();

		TopBar topBar = new TopBar();
		topBar.setBorder(new LineBorder(Color.RED));
		clientActor.topBar = topBar;
		topBar.updateResources(clientActor.wood, clientActor.food, clientActor.gold,
				clientActor.stone);
		contentPane.add(topBar);

		JPanel gameWindow = new GameWindow(clientActor);
		gameWindow.setBorder(new LineBorder(Color.BLUE));
		contentPane.add(gameWindow);

		BottomBar bottomBar = new BottomBar(clientActor);
		bottomBar.updateControls();
		bottomBar.setBorder(new LineBorder(Color.GREEN));
		contentPane.add(bottomBar);

		SpringLayout layout = new SpringLayout();

		// topBar on top of screen
		layout.putConstraint(SpringLayout.NORTH, topBar, 0,
				SpringLayout.NORTH, contentPane);
		layout.putConstraint(SpringLayout.EAST, topBar, 0,
				SpringLayout.EAST, contentPane);
		layout.putConstraint(SpringLayout.WEST, topBar, 0,
				SpringLayout.WEST, contentPane);

		// gameWindow below topBar
		layout.putConstraint(SpringLayout.NORTH, gameWindow, 0,
				SpringLayout.SOUTH, topBar);
		layout.putConstraint(SpringLayout.EAST, gameWindow, 0,
				SpringLayout.EAST, contentPane);
		layout.putConstraint(SpringLayout.WEST, gameWindow, 0,
				SpringLayout.WEST, contentPane);

		// bottomBar on bottom
		layout.putConstraint(SpringLayout.SOUTH, bottomBar, 0,
				SpringLayout.SOUTH, contentPane);
		layout.putConstraint(SpringLayout.EAST, bottomBar, 0,
				SpringLayout.EAST, contentPane);
		layout.putConstraint(SpringLayout.WEST, bottomBar, 0,
				SpringLayout.WEST, contentPane);

		// gameWindow bind to bottomBar
		layout.putConstraint(SpringLayout.SOUTH, gameWindow, 0,
				SpringLayout.NORTH, bottomBar);

		contentPane.setLayout(layout);
		repaint();
	}

	/** Clean the screen so something else can be displayed. */
	public void clear()
	{
		// remove everything from the content pane
		getContentPane().removeAll();
		// do a magic thing that invalidates old contents
		getContentPane().revalidate();
		// remove the layout
		getContentPane().setLayout(null);
		// paint over where the old contents were
		getContentPane().repaint();
	}

}
