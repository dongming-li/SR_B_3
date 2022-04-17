package com.trogdadr2tr.GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import com.trogdadr2tr.game.GameRoom;
import com.trogdadr2tr.game.RTSPlayer;
import com.trogdadr2tr.socket.Client;

public class LobbyScreen extends JFrame
{

	/** yeah java */
	private static final long serialVersionUID = 1L;

	/**
	 * The GameRoom for reference
	 */
	public GameRoom g;

	/**
	 * The RTSPlayer that is created in this class
	 */
	public RTSPlayer player;

	/**
	 * The name of this room
	 */
	private JLabel roomName;

	/**
	 * Creates a new LobbyScreen within the given WindowBuilder
	 * @param screen The WindowBuilder the lobby is created in
	 */
	public LobbyScreen(WindowBuilder screen)
	{
		screen.clear();
		screen.setLayout(null);
		buildLobbyScreen(screen);
	}

	/**
	 * Creates the actual LobbyScreen
	 * @param screen The WindowBuilder the lobby is created in
	 */
	public void buildLobbyScreen(WindowBuilder screen)
	{
		try
		{
			Client.init();
			player = new RTSPlayer(this);
			Client.getInstance().addListener(player);
		}
		catch (IOException e)
		{
			System.err.println("There was a bamboozle: " + e.getMessage());
		}

		roomName = new JLabel();
		roomName.setText("Connecting to server...");
		roomName.setBounds(0, 0, screen.getWidth() / 8, screen.getHeight() / 16);
		roomName.setVisible(true);

		JButton ready = new JButton("Ready Up");
		int width = screen.getWidth() / 8;
		int height = screen.getHeight() / 8;
		ready.setBounds(screen.getWidth() / 2 - width / 2,
				screen.getHeight() / 2 - height / 2, width, height);
		ready.setActionCommand("Ready");
		ready.setToolTipText("Sets the players status to 'Ready'");
		ready.setVisible(true);

		ready.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				try
				{
					Client.getInstance().updater.setReadiness(true);
				}
				catch (IllegalStateException e)
				{
					e.printStackTrace();
				}
			}
		});
		screen.getContentPane().add(ready);
		screen.getContentPane().add(roomName);
		screen.setVisible(false);
		screen.setVisible(true);
	}
	
	/**
	 * Sets the GameRoom reference to the given gameRoom
	 * @param gameRoom The GameRoom for reference
	 */
	public void setGameRoom(GameRoom gameRoom)
	{
		g = gameRoom;
		roomName.setText(g.getRoomName());
	}

}
