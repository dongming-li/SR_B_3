package com.trogdadr2tr.game;

import java.awt.EventQueue;
import java.util.ArrayList;

import com.trogdadr2tr.resources.*;
import com.trogdadr2tr.socket.ConnectionState;
import com.trogdadr2tr.socket.ServerListener;

import coltGUI.GameWindow;
import coltGUI.TopBar;
import coltGUI.WindowBuilder;

public class ClientGameLogic implements ServerListener
{

	/**
	 * The frame that builds all the windows
	 */
	private WindowBuilder frame;
	
	/**
	 * Little black top bar
	 */
	public TopBar topBar;
	
	/**
	 * The GameWindow for reference
	 */
	public GameWindow gameWindow;
	
	/**
	 * The selected Object
	 */
	public Object selected;

	/**
	 * Current amount of gold resource
	 */
	public int gold;

	/**
	 * Current amount of wood resource
	 */
	public int wood;

	/**
	 * Current amount of food resource
	 */
	public int food;

	/**
	 * Current amount of stone resource
	 */
	public int stone;

	/**
	 * The GameMap which contains all the buildings and units in the map
	 */
	public GameMap map;

	/**
	 * The x and y coordinates of the center of the screen
	 */
	public Point centerScreen;

	/**
	 * Creates a new object of this class with the given frame as reference
	 * @param frame The WindowBuilder to reference
	 */
	public ClientGameLogic(WindowBuilder frame)
	{
		this.frame = frame;
		centerScreen = new Point(0, 0);
		map = null;
	}

	@Override
	public void requestLogin()
	{
		frame.loginScreen();
	}

	@Override
	public void updateConnectionState(ConnectionState state)
	{
		// System.out.println(state);
		EventQueue.invokeLater(new Runnable()
		{

			@Override
			public void run()
			{
				switch (state)
				{
					case NEWCOMER:
						// don't do anything
						break;
					case DISCONNECTED:
						// TODO - stop everything
						frame.startMenuScreen();
						break;
					case GUEST:
						// only for hack and slash players
						System.err.println("This state should be impossible.");
						break;
					case HOST:
						// ready up screen
						break;
					case HaS_IN_GAME:
						// only for hack and slash players
						System.err.println("This state should be impossible.");
						break;
					case LOGGED_IN:
						// TODO - host or join game

						frame.playMenuScreen();
						break;
					case RTS_IN_GAME:
						// TODO - display game
						frame.inGameScreen(ClientGameLogic.this);
						break;
					default:
						break;
				}
			}
		});
	}

	@Override
	public void serverErrorMessage(String errorMessage)
	{
		// TODO - display temporary popup message
		System.err.println(errorMessage);
	}

	@Override
	public void downloadGameRooms(ArrayList<GameRoom> gameRooms)
	{
		frame.serverSelectScreen(gameRooms);
	}

	@Override
	public void downloadGameRoom(GameRoom gameRoom)
	{
		EventQueue.invokeLater(new Runnable()
		{

			@Override
			public void run()
			{
				frame.gameLobbyScreen(gameRoom);
			}
		});
	}

	@Override
	public void newMessage(Message message)
	{
		// TODO - display temp chat popup, add message to list.
		// look at JOptionPane
		System.out.println(message);
	}

	@Override
	public void downloadMap(GameMap map)
	{
		this.map = map;
	}

	@Override
	public void updateUnit(Unit unit)
	{
//		unit.setPosition(position.getX(), position.getY());
		map.units.set(map.units.indexOf(unit), unit);
	}

	@Override
	public void updateBuilding(Building building)
	{
		map.buildings.set(map.buildings.indexOf(building), building);
	}

	@Override
	public void addUnit(Unit unit)
	{
		map.units.add(unit);
		frame.repaint();
	}

	@Override
	public void addBuilding(Building building)
	{
		map.buildings.add(building);
		frame.repaint();
	}

	@Override
	public void removeUnit(Unit unit)
	{
		map.units.remove(unit);
	}

	@Override
	public void removeBuilding(Building building)
	{
		map.buildings.remove(building);
	}

	@Override
	public void updateResources(int gold, int wood, int food, int stone)
	{
		this.gold = gold;
		this.wood = wood;
		this.food = food;
		this.stone = stone;
		topBar.updateResources(wood, food, gold, stone);
	}

}
