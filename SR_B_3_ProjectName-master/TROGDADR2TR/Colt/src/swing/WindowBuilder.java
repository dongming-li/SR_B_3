package swing;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

/**
 * 
 * @author Colt Rogness
 *
 */
public class WindowBuilder extends JFrame {

	/**
	 * Not sure why eclipse wants this, but they can have it.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Launch the application.
	 * 
	 * @param args
	 *            - command line arguments
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				WindowBuilder frame = new WindowBuilder();
				// frame.setVisible(true);
				frame.buildMainScreen();
			}
		});
	}

	/**
	 * Build a frame, set it to fullscreen, set it to be visible.
	 */
	public WindowBuilder() {

		// buttons = new ArrayList<JButton>();
		// make the program end when the window is closed
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// removes the borders and buttons on the top of the window
		setUndecorated(true);
		// make the window fullscreen
		setExtendedState(JFrame.MAXIMIZED_BOTH);
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

	/**
	 * Set up the starting screen. Will have Play, Map Editor, Credits, and Exit
	 * Buttons.
	 */
	private void buildMainScreen() {
		class mainScreenActionListener implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent e) {
				switch (e.getActionCommand()) {
				case "Play":
					System.out.println("Play button was pressed.");
					buildPlayScreen();
					break;
				case "Map Editor":
					System.out.println("Map Editor button was pressed.");
					buildMapEditorScreen();
					break;
				case "Options":
					System.out.println("Options button was pressed.");
					buildOptionsScreen();
					break;
				case "Credits":
					System.out.println("Credits button was pressed.");
					buildCreditsScreen();
					break;
				case "Exit":
					System.out.println("Exit button was pressed.");
					System.exit(0);
				}
			}

		}
		ArrayList<JButton> buttons = new ArrayList<JButton>();
		JButton button;

		// remove everything from the content pane
		getContentPane().removeAll();
		// do a magic thing that invalidates old contents
		getContentPane().revalidate();
		// paint over where the old contents were
		getContentPane().repaint();

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
		button.setToolTipText("Change game options, graphical and audio settings, and key bindings.");
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
		for (int n = 0; n < buttons.size(); n++) {
			buttons.get(n).setBounds((getWidth() - getWidth() / 6) / 2,
					(getHeight() - buttons.size() * getHeight() / 18) / 2 + getHeight() / 18 * n, getWidth() / 6, getHeight() / 18);
			buttons.get(n).addActionListener(new mainScreenActionListener());
			getContentPane().add(buttons.get(n));
		}

	}

	/**
	 * Set up the play sub-screen, with host game, join Game, and back.
	 */
	private void buildPlayScreen() {
		class playScreenActionListener implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent e) {
				switch (e.getActionCommand()) {
				case "Host Game":
					System.out.println("Host Game button was pressed.");
					break;
				case "Join Game":
					System.out.println("Join Game button was pressed.");
					break;
				case "Back":
					System.out.println("Back button was pressed.");
					buildMainScreen();
				}
			}

		}
		ArrayList<JButton> buttons = new ArrayList<JButton>();
		JButton button;

		// remove everything from the content pane
		getContentPane().removeAll();
		// do a magic thing that invalidates old contents
		getContentPane().revalidate();
		// paint over where the old contents were
		getContentPane().repaint();

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
		for (int n = 0; n < buttons.size(); n++) {
			buttons.get(n).setBounds((getWidth() - getWidth() / 6) / 2,
					(getHeight() - buttons.size() * getHeight() / 18) / 2 + getHeight() / 18 * n, getWidth() / 6, getHeight() / 18);
			buttons.get(n).addActionListener(new playScreenActionListener());
			getContentPane().add(buttons.get(n));
		}

	}

	/**
	 * Set up Map Editor sub-screen, with new map, load map, and back.
	 */
	private void buildMapEditorScreen() {
		class mapEditorScreenActionListener implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent e) {
				switch (e.getActionCommand()) {
				case "New Map":
					System.out.println("Create Map button was pressed.");
					break;
				case "Load Map":
					System.out.println("Load Map button was pressed.");
					break;
				case "Back":
					System.out.println("Back button was pressed.");
					buildMainScreen();
				}
			}

		}
		ArrayList<JButton> buttons = new ArrayList<JButton>();
		JButton button;

		// remove everything from the content pane
		getContentPane().removeAll();
		// do a magic thing that invalidates old contents
		getContentPane().revalidate();
		// paint over where the old contents were
		getContentPane().repaint();

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
		for (int n = 0; n < buttons.size(); n++) {
			buttons.get(n).setBounds((getWidth() - getWidth() / 6) / 2,
					(getHeight() - buttons.size() * getHeight() / 18) / 2 + getHeight() / 18 * n, getWidth() / 6, getHeight() / 18);
			buttons.get(n).addActionListener(new mapEditorScreenActionListener());
			getContentPane().add(buttons.get(n));
		}
	}

	/**
	 * Set up Options sub-screen, with graphics, audio, controls, other, and back.
	 */
	private void buildOptionsScreen() {
		class OptionScreenActionListener implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent e) {
				switch (e.getActionCommand()) {
				case "Graphics":
					System.out.println("Graphics was pressed.");
					break;
				case "Audio":
					System.out.println("Audio was pressed.");
					break;
				case "Controls":
					System.out.println("Controles was pressed.");
					break;
				case "Other":
					System.out.println("Other button was pressed.");
					break;
				case "Back":
					System.out.println("Back button was pressed.");
					buildMainScreen();
				}
			}

		}
		ArrayList<JButton> buttons = new ArrayList<JButton>();
		JButton button;

		// remove everything from the content pane
		getContentPane().removeAll();
		// do a magic thing that invalidates old contents
		getContentPane().revalidate();
		// paint over where the old contents were
		getContentPane().repaint();

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
		for (int n = 0; n < buttons.size(); n++) {
			buttons.get(n).setBounds((getWidth() - getWidth() / 6) / 2,
					(getHeight() - buttons.size() * getHeight() / 18) / 2 + getHeight() / 18 * n, getWidth() / 6, getHeight() / 18);
			buttons.get(n).addActionListener(new OptionScreenActionListener());
			getContentPane().add(buttons.get(n));
		}
	}

	private void buildCreditsScreen() {
		class mainScreenActionListener implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent e) {
				switch (e.getActionCommand()) {
				case "Back":
					System.out.println("Back button was pressed.");
					buildMainScreen();
				}
			}

		}
		int paneHeight = 500;
		int paneWidth = 500;
		JButton button;
		JPanel subWindow;

		// remove everything from the content pane
		getContentPane().removeAll();
		// do a magic thing that invalidates old contents
		getContentPane().revalidate();
		// paint over where the old contents were
		getContentPane().repaint();

		subWindow = new JPanel();
		subWindow.setBounds((getWidth() - paneWidth) / 2, (getHeight() - paneHeight) / 2, paneWidth, paneHeight);
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
		button.setBounds((subWindow.getWidth() - getWidth() / 6 / 3 - 20), (subWindow.getHeight() - getHeight() / 18 / 3 - 20),
				getWidth() / 6 / 3, getHeight() / 18 / 3);
		button.addActionListener(new mainScreenActionListener());
		subWindow.add(button);
	}
}
