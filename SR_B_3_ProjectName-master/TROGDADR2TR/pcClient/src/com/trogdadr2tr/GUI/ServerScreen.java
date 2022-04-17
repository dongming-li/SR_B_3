package com.trogdadr2tr.GUI;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

/** @author Connor Brown */
public class ServerScreen extends JFrame
{

	/**
	 * Number of servers displayed
	 */
	public int serverNumber = 25;

	/**
	 * Current experience points
	 */
	public int levelExp;

	/**
	 * Experience points maximum
	 */
	public int maxExp;

	/**
	 * Users win loss ratio
	 */
	public double winLossRatio = 2.0;

	/**
	 * Text for favorite class
	 */
	public String favoriteClass = "Tank";

	/**
	 * Text for least favorite class
	 */
	public String leastFavoriteClass = "Magic";

	/**
	 * Text for favorite building
	 */
	public String favoriteBuilding = "Spawner";

	/**
	 * Text for favorite unit
	 */
	public String favoriteUnit = "Calvary";

	/**
	 * Reference to this object
	 */
	public static ServerScreen parent;

	/** yeah java */
	private static final long serialVersionUID = 1L;

	public static void main(String[] args)
	{
		new ServerScreen();
	}

	/**
	 * Creates the server select screen
	 */
	public ServerScreen()
	{
		parent = this;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setExtendedState(Frame.MAXIMIZED_BOTH);
		setUndecorated(true);
		setVisible(true);

		levelExp = 100;
		maxExp = getWidth() / 12;

		addExitButton();
		addServerSelect();
		addPlayerIcon();
		addLevelBar();
		addStats();
		addChat();
	}

	/**
	 * Adds a chat screen the the screen
	 */
	public void addChat()
	{
		JLabel chat = new JLabel("Chat goes here", SwingConstants.CENTER);
		chat.setBorder(new LineBorder(Color.black));
		int y = getWidth() / 8 + getHeight() / 4;
		chat.setBounds(getWidth() * 2 / 3, y, getWidth() / 3, getHeight() - y);
		chat.setVisible(true);
		getContentPane().add(chat);
	}

	/**
	 * Displays stats about the user, including favorite class and favorite unit
	 */
	public void addStats()
	{
		Container c = new Container();
		int width = getWidth() / 3;
		int height = getHeight() / 4;
		c.setPreferredSize(new Dimension(width, height));
		c.setBounds(getWidth() * 2 / 3, getWidth() / 8, width, height);
		// String s = "Win/Loss Ration: " + winLossRatio + '\n' +
		// "Favorite Class: " + favoriteClass + '\n' +
		// "Least Favorite Class: " + leastFavoriteClass + '\n' +
		// "Favorite Building: " + favoriteBuilding + '\n' +
		// "Favorite Unit: " + favoriteUnit;
		JLabel winLoss = new JLabel("Win/Loss Ration:                    " + winLossRatio,
				SwingConstants.CENTER);
		winLoss.setBounds(0, 0, width, height / 5);
		winLoss.setBorder(new LineBorder(Color.black));
		winLoss.setVisible(true);
		c.add(winLoss);

		JLabel favClass =
				new JLabel("Favorite Class:                     " + favoriteClass,
						SwingConstants.CENTER);
		favClass.setBounds(0, height / 5, width, height / 5);
		favClass.setBorder(new LineBorder(Color.black));
		favClass.setVisible(true);
		c.add(favClass);

		JLabel leastFavClass =
				new JLabel("Least Favorite Class:               " + leastFavoriteClass,
						SwingConstants.CENTER);
		leastFavClass.setBounds(0, height * 2 / 5, width, height / 5);
		leastFavClass.setBorder(new LineBorder(Color.black));
		leastFavClass.setVisible(true);
		c.add(leastFavClass);

		JLabel favBuilding =
				new JLabel("Favorite Building:                  " + favoriteBuilding,
						SwingConstants.CENTER);
		favBuilding.setBounds(0, height * 3 / 5, width, height / 5);
		favBuilding.setBorder(new LineBorder(Color.black));
		favBuilding.setVisible(true);
		c.add(favBuilding);

		JLabel favUnit = new JLabel("Favorite Unit:                      " + favoriteUnit,
				SwingConstants.CENTER);
		favUnit.setBounds(0, height * 4 / 5, width, height / 5);
		favUnit.setBorder(new LineBorder(Color.black));
		favUnit.setVisible(true);
		c.add(favUnit);

		getContentPane().add(c);
	}

	/**
	 * Adds the progress bar for the player, which indicates how close they are to their next level
	 */
	public void addLevelBar()
	{
		int x = getWidth() * 2 / 3 + getWidth() / 8;
		Container c = new Container();
		c.setPreferredSize(new Dimension(getWidth() - x, getWidth() / 8));
		c.setBounds(x, 0, getWidth() - x, getWidth() / 8);

		JLabel text = new JLabel("Level", SwingConstants.CENTER);
		text.setBounds(0, 0, getWidth() - x, getWidth() / 15);
		text.setVisible(true);
		c.add(text);

		JPanel progressBar = new JPanel();
		progressBar.setBounds((getWidth() - x) / 2 - getWidth() / 24, getWidth() / 16,
				levelExp, getHeight() / 20);
		progressBar.setBackground(Color.BLACK);
		progressBar.setBorder(new LineBorder(Color.black));
		progressBar.setVisible(true);
		c.add(progressBar);

		JPanel fullLevelBar = new JPanel();
		fullLevelBar.setBounds((getWidth() - x) / 2 - getWidth() / 24, getWidth() / 16,
				maxExp, getHeight() / 20);
		fullLevelBar.setBackground(Color.WHITE);
		fullLevelBar.setBorder(new LineBorder(Color.black));
		fullLevelBar.setVisible(true);
		c.add(fullLevelBar);

		JLabel border = new JLabel();
		border.setBounds(0, 0, getWidth() - x, getWidth() / 8);
		border.setBorder(new LineBorder(Color.BLACK));
		c.add(border);

		getContentPane().add(c);
	}

	/**
	 * Adds the players Icon to the screen
	 */
	public void addPlayerIcon()
	{
		JLabel icon = new JLabel("Picture goes here", SwingConstants.CENTER);
		icon.setBorder(new LineBorder(Color.black));
		icon.setBounds(getWidth() * 2 / 3, 0, getWidth() / 8, getWidth() / 8);
		icon.setVisible(true);
		getContentPane().add(icon);
	}

	/**
	 * Adds the top bar to the container which contains the information about the servers
	 */
	public void addServerSelect()
	{
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(getWidth() * 2 / 3, getHeight()));
		Container c = new Container();
		JScrollPane scroll = new JScrollPane(panel);
		scroll.setHorizontalScrollBarPolicy(
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scroll.getVerticalScrollBar().setUnitIncrement(24);

		int GNwidth = getWidth() / 4;
		int baseHeight = 50;
		int otherWidth = getWidth() * 5 / 36;

		c.setPreferredSize(new Dimension(getWidth() * 2 / 3, serverNumber * 50 + 50));

		JLabel gameName = new JLabel("Game Name", SwingConstants.CENTER);
		gameName.setBounds(0, 0, GNwidth, baseHeight);
		gameName.setVisible(true);
		gameName.setBorder(new LineBorder(Color.black));
		c.add(gameName);

		JLabel gameType = new JLabel("Game Type", SwingConstants.CENTER);
		gameType.setBounds(GNwidth, 0, otherWidth, baseHeight);
		gameType.setVisible(true);
		gameType.setBorder(new LineBorder(Color.black));
		c.add(gameType);

		JLabel playerNumber = new JLabel("Number of Players", SwingConstants.CENTER);
		playerNumber.setBounds(GNwidth + otherWidth, 0, otherWidth, baseHeight);
		playerNumber.setVisible(true);
		playerNumber.setBorder(new LineBorder(Color.black));
		c.add(playerNumber);

		JLabel isPrivate = new JLabel("Private", SwingConstants.CENTER);
		isPrivate.setBounds(GNwidth + otherWidth + otherWidth, 0, otherWidth, baseHeight);
		isPrivate.setVisible(true);
		isPrivate.setBorder(new LineBorder(Color.black));
		c.add(isPrivate);

		addServers(c);

		scroll.setViewportView(c);
		scroll.setBorder(new LineBorder(Color.black));
		add(scroll);
		scroll.setBounds(0, 0, getWidth() * 2 / 3, getHeight());
	}

	/**
	 * Adds the servers to the container
	 * @param c The container the servers are added to
	 */
	private void addServers(Container c)
	{
		int firstWidth = getWidth() / 4;
		int width = getWidth() * 5 / 36;
		int height = 50;

		for (int i = 0; i < serverNumber; i++)
		{
			JButton name = new JButton("NAME TEXT");
			name.setBounds(0, height * (i + 1), firstWidth, height);
			name.setActionCommand("Join");
			name.addActionListener(new ActionListener()
			{

				@Override
				public void actionPerformed(ActionEvent e)
				{
					if (e.getActionCommand().equals("Join"))
					{
					}
				}
			});
			c.add(name);

			JButton type = new JButton("Standard");
			type.setBounds(firstWidth, height * (i + 1), width, height);
			type.setActionCommand("Join");
			type.addActionListener(new ActionListener()
			{

				@Override
				public void actionPerformed(ActionEvent e)
				{
					if (e.getActionCommand().equals("Join"))
					{
					}
				}
			});
			c.add(type);

			JButton number = new JButton("1/5");
			number.setBounds(firstWidth + width, height * (i + 1), width, height);
			number.setActionCommand("Join");
			number.addActionListener(new ActionListener()
			{

				@Override
				public void actionPerformed(ActionEvent e)
				{
					if (e.getActionCommand().equals("Join"))
					{
					}
				}
			});
			c.add(number);

			JButton kek = new JButton("No");
			kek.setBounds(firstWidth + width + width, height * (i + 1), width, height);
			kek.setActionCommand("Join");
			kek.addActionListener(new ActionListener()
			{

				@Override
				public void actionPerformed(ActionEvent e)
				{
					if (e.getActionCommand().equals("Join"))
					{
					}
				}
			});
			c.add(kek);
		}
	}

	/**
	 * Adds a way to exit this screen
	 */
	public void addExitButton()
	{
		int width = getWidth() / 16;
		int height = getHeight() / 20;
		int x = getWidth() - width;
		int y = 0;

		JButton exit = new JButton("Exit");
		exit.setBounds(x, y, width, height);
		exit.setActionCommand("Exit");
		exit.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				if (e.getActionCommand().equals("Exit"))
				{
					parent.dispatchEvent(
							new WindowEvent(parent, WindowEvent.WINDOW_CLOSING));
				}
			}
		});
		getContentPane().add(exit);
	}

}
