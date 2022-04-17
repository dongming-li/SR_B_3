package com.trogdadr2tr.socket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import com.trogdadr2tr.database.DatabaseConnector;
import com.trogdadr2tr.game.*;
import com.trogdadr2tr.resources.*;

/** Handles all communication with the client.
 * 
 * @author Colt Rogness */
public class ClientConnection extends Thread implements ClientListener, GameListener
{

	/** Socket connection to the client. */
	private Socket socket;

	/** Listener / handler for client interaction. */
	private ClientReciever reciever;

	/** Handle pushing updates to the client. */
	public ClientUpdater updater;

	/**
	 * Game room this client is in. null if not in a room.
	 */
	private GameRoom gameRoom;

	/**
	 * Client's in-game player. null if not in a room.
	 */
	private Player player;

	/**
	 * Client's connection state.
	 */
	private ConnectionState connectionState;

	/** @param socket connection to the client */
	public ClientConnection(Socket socket)
	{
		this.socket = socket;
		this.connectionState = ConnectionState.DISCONNECTED;
		this.gameRoom = null;
		this.player = null;
	}

	/** Terminate the connection and close all child threads. */
	public void close()
	{
		if (updater != null)
		{
			updater.close();
			updater = null;
		}
		if (reciever != null)
		{
			reciever.close();
			reciever = null;
		}
	}

	/** Connect Object input and output streams, pass them to
	 * their respective handlers, and start the client listener.
	 * Push changes on the server to the client. */
	@Override
	public void run()
	{
		try
		{
			ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
			ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
			reciever = new ClientReciever(this, in);
			updater = new ClientUpdater(out);
			reciever.start();
			updater.start();
			reciever.addListener(this);
			// updater.updateConnectionState(ConnectionState.NEWCOMER);
			// updater.requestLogin();
			// TODO - handle login properly
			sleep(1000);
			setConnectionState(ConnectionState.LOGGED_IN);
		}
		catch (IOException e)
		{
			System.err.println("ClientConnection I/O error: " + e.getMessage());
			close();
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Add a listener to this client. Will recieve updates from the remote client.
	 * @param toAdd
	 */
	public void addListener(ClientListener toAdd)
	{
		reciever.addListener(toAdd);
	}

	public void setConnectionState(ConnectionState state)
	{
		connectionState = state;
		updater.updateConnectionState(connectionState);
	}

	@Override
	public void login(String username, String password)
	{
		if (DatabaseConnector.getInstance().submitLogin(username, password))
		{
			setConnectionState(ConnectionState.LOGGED_IN);
		}
	}

	@Override
	public void joinRoom(GameRoom room)
	{
		try
		{
			GameRoom toJoin = Server.getInstance().getRoom(room);
			player = new Player(PlayerType.HS);
			toJoin.joinRoom(player);
			gameRoom = toJoin;
		}
		catch (IllegalStateException e)
		{
			// TODO if room full, tell client of error
		}
	}

	@Override
	public void hostRoom(String name)
	{
		try
		{
			player = new Player(PlayerType.RTS);
			GameRoom toHost = new GameRoom(player, name);
			Server.getInstance().addGameRoom(toHost);
			gameRoom = toHost;
			gameRoom.start();
			gameRoom.addListener(this);
			setConnectionState(ConnectionState.HOST);
			updater.updateGameRoom(gameRoom);
		}
		catch (IllegalArgumentException e)
		{
			// TODO - tell client to rename server updater
		}
	}

	@Override
	public void updateGameRoom(GameRoom room)
	{
		if (player.playerType == PlayerType.RTS)
		{
			gameRoom.update(room);
		}
	}

	@Override
	public void kickPlayer(Player player)
	{
		if (player.isHost())
		{
			gameRoom.leaveRoom(player);
		}
	}

	@Override
	public void addPlayer(Player player)
	{
		if (player.isHost())
		{
			try
			{
				gameRoom.joinRoom(player);
			}
			catch (IllegalStateException e)
			{
			}
		}
	}

	@Override
	public void setReadiness(Boolean ready)
	{
		player.setReadiness(ready);
	}

	@Override
	public void sendMessage(String messageText)
	{
		gameRoom.sendMessage(new Message(player, messageText));
	}

	@Override
	public void leaveRoom()
	{
		gameRoom.leaveRoom(player);
		setConnectionState(ConnectionState.LOGGED_IN);
	}

	@Override
	public void characterSelect()
	{
	}

	@Override
	public void placeBuilding(String buildingType, Point location)
	{
		if (player.playerType == PlayerType.RTS)
		{
			gameRoom.placeBuilding(player, buildingType, location);
		}
	}

	public void spawnUnit(Building spawningBuilding, String unitType)
	{
		if (player.playerType == PlayerType.RTS)
		{
			gameRoom.spawnUnit(spawningBuilding, unitType);
		}
	}

	@Override
	public void deleteBuilding(Building building)
	{
		if (player.playerType == PlayerType.RTS)
		{
			gameRoom.deleteBuilding(player, building);
		}
	}

	@Override
	public void moveUnit(Unit unit, Point destination)
	{
		if (player.playerType == PlayerType.RTS)
		{
			gameRoom.moveUnit(player, unit, destination);
		}
	}

	@Override
	public void moveUnits(ArrayList<Unit> units, Point destination)
	{
		if (player.playerType == PlayerType.RTS)
		{
			gameRoom.moveUnits(player, units, destination);
		}

	}

	@Override
	public void movePlayer(MovementDirection move, Point currentLocation)
	{
		if (player.playerType == PlayerType.HS)
		{
			gameRoom.movePlayer(player, move, currentLocation);
		}

	}

	@Override
	public void updateResources(int gold, int wood, int food, int stone)
	{
		updater.updateResources(gold, wood, food, stone);
	}

	@Override
	public void addBuilding(Building building)
	{
		updater.addBuilding(building);
	}

	@Override
	public void addUnit(Unit unit)
	{
		updater.addUnit(unit);
	}

	@Override
	public void removeBuilding(Building building)
	{
		updater.removeBuilding(building);
	}

	@Override
	public void removeUnit(Unit unit)
	{
		updater.removeUnit(unit);
	}

	@Override
	public void updateBuilding(Building building)
	{
		updater.updateBuilding(building);
	}

	@Override
	public void updateUnit(Unit unit)
	{
		updater.updateUnit(unit);
	}

	@Override
	public void sendMessage(Message message)
	{
		updater.newMessage(message);
	}

	@Override
	public void updatePlayer(Player player)
	{
		updater.updatePlayer(player);
	}

	@Override
	public void startGame()
	{
		if (player.playerType == PlayerType.HS)
		{
			this.setConnectionState(ConnectionState.HaS_IN_GAME);
		}
		else if (player.playerType == PlayerType.RTS)
		{
			this.setConnectionState(ConnectionState.RTS_IN_GAME);
		}

	}

	@Override
	public void sendMap(GameMap map)
	{
		updater.sendMap(map);

	}

}
