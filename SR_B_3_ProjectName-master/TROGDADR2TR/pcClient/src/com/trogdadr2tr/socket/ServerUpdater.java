package com.trogdadr2tr.socket;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import com.trogdadr2tr.game.GameRoom;
import com.trogdadr2tr.game.Player;
import com.trogdadr2tr.resources.*;

/** Handle pushing changes to the server.
 * 
 * @author Colt Rogness */
public class ServerUpdater extends Thread
{

	/** Stream out to the server. */
	private ObjectOutputStream out;

	/**
	 * The queue which processes command orders
	 */
	private ThreadQueue queue;

	/**
	 * Reference to the Client
	 */
	private Client client;

	/**
	 * True if connected to the server, false otherwise
	 */
	private Boolean connectionOpen;

	/** Set the output stream to use.
	 * 
	 * @param client The Client this ServerUpdater uses
	 * @param out The OutputStream used by this ServerUpdater
	 * */
	public ServerUpdater(Client client, ObjectOutputStream out)
	{
		this.client = client;
		this.out = out;
		this.queue = new ThreadQueue();
		connectionOpen = true;
	}

	/**
	 * Runs the
	 */
	public void run()
	{
		while (connectionOpen)
		{
			try
			{
				sleep(100);
				Thread thread = queue.pop();
				if (thread != null)
				{
					thread.start();
					thread.join(1000);
				}
			}
			catch (InterruptedException e)
			{
			}
		}
	}

	/**
	 * Closes the connection with the server
	 */
	public void close()
	{
		connectionOpen = false;
		try
		{
			out.writeObject("QUIT");
		}
		catch (IOException e)
		{
		}
	}

	/**
	 * Sends a command to the server to move a player in the direction based on the given Point
	 * @param move The direction to move the player
	 * @param location The point the movement is based off of
	 * @throws IllegalStateException
	 */
	public void movePlayer(MovementDirection move, Point location)
			throws IllegalStateException
	{
		if (client.connectionState != ConnectionState.HaS_IN_GAME)
		{
			throw new IllegalStateException("Not in a game.");
		}
		queue.add(new Thread()
		{
			public void run()
			{
				try
				{
					out.writeObject("MOVE PLAYER");
					out.writeObject(move);
					out.writeObject(location);
				}
				catch (IOException e)
				{
					ServerUpdater.this.close();
				}
			}
		});
	}

	/**
	 * Moves the indicated unit to a destination point
	 * @param unit The unit to move
	 * @param destination The point the unit is moving towards
	 * @throws IllegalStateException
	 */
	public void moveUnit(Unit unit, Point destination)
			throws IllegalStateException
	{
		if (client.connectionState != ConnectionState.RTS_IN_GAME)
		{
			throw new IllegalStateException("Not in a game.");
		}
		queue.add(new Thread()
		{

			public void run()
			{
				try
				{
					out.writeObject("MOVE UNIT");
					out.writeObject(unit);
					out.writeObject(destination);
				}
				catch (IOException e)
				{
					ServerUpdater.this.close();
				}
			}
		});

	}

	/**
	 * Moves a group of units to the indicated Point
	 * @param units The ArrayList of units to move
	 * @param destination The point the units are moving towards
	 * @throws IllegalStateException
	 */
	public void moveUnits(ArrayList<Unit> units, Point destination)
			throws IllegalStateException
	{
		if (client.connectionState != ConnectionState.RTS_IN_GAME)
		{
			throw new IllegalStateException("Not in a game.");
		}
		queue.add(new Thread()
		{

			public void run()
			{
				try
				{
					out.writeObject("MOVE UNITS");
					out.writeObject(units);
					out.writeObject(destination);
				}
				catch (IOException e)
				{
					ServerUpdater.this.close();
				}
			}
		});

	}

	/**
	 * Sends a command to the server to place a building at the designated point
	 * @param name The type of building being placed
	 * @param placeLocation The location the building will be placed
	 * @throws IllegalStateException
	 */
	public void placeBuilding(String name, Point placeLocation)
			throws IllegalStateException
	{
		if (client.connectionState != ConnectionState.RTS_IN_GAME)
		{
			System.err.println("ConnectionState is " + client.connectionState);
			throw new IllegalStateException("Not in a game.");
		}
		queue.add(new Thread()
		{

			public void run()
			{
				try
				{
					out.writeObject("PLACE BUILDING");
					out.writeObject(name);
					out.writeObject(placeLocation);
				}
				catch (IOException e)
				{
					ServerUpdater.this.close();
				}
			}
		});
	}

	/**
	 * Spawns a unit around the given building
	 * @param spawningBuilding The building the unit is being spawned around
	 * @param unitName The type of the unit that is being spawned
	 * @throws IllegalStateException
	 */
	public void spawnUnit(Building spawningBuilding, String unitName)
			throws IllegalStateException
	{
		if (client.connectionState != ConnectionState.RTS_IN_GAME)
		{
			throw new IllegalStateException("Not in a game.");
		}
		queue.add(new Thread()
		{

			public void run()
			{
				try
				{
					out.writeObject("SPAWN UNIT");
					out.writeObject(spawningBuilding);
					out.writeObject(unitName);
				}
				catch (IOException e)
				{
					ServerUpdater.this.close();
				}
			}
		});
	}

	/**
	 * Sends a command to the server to delete the indicated building
	 * @param building The building to be removed
	 * @throws IllegalStateException
	 */
	public void deleteBuilding(Building building)
			throws IllegalStateException
	{
		if (client.connectionState != ConnectionState.RTS_IN_GAME)
		{
			throw new IllegalStateException("Not in a game.");
		}
		queue.add(new Thread()
		{

			public void run()
			{
				try
				{
					out.writeObject("DELETE BUILDING");
					out.writeObject(building);
				}
				catch (IOException e)
				{
					ServerUpdater.this.close();
				}
			}
		});
	}

	/**
	 * Makes the Client leave the room they are in
	 * @throws IllegalStateException
	 */
	public void leaveRoom() throws IllegalStateException
	{
		if (client.connectionState != ConnectionState.HOST
				&& client.connectionState != ConnectionState.GUEST
				&& client.connectionState != ConnectionState.RTS_IN_GAME
				&& client.connectionState != ConnectionState.HaS_IN_GAME)
		{
			throw new IllegalStateException("Not in a room.");
		}
		queue.add(new Thread()
		{

			public void run()
			{
				try
				{
					out.writeObject("LEAVE ROOM");
				}
				catch (IOException e)
				{
					ServerUpdater.this.close();
				}
			}
		});
	}

	/**
	 * Sends command to the server to give the player the character they chose
	 * @throws IllegalStateException
	 */
	public void characterSelect() throws IllegalStateException
	{
		if (client.connectionState != ConnectionState.HOST
				&& client.connectionState != ConnectionState.GUEST)
		{
			throw new IllegalStateException("");
		}
		queue.add(new Thread()
		{

			public void run()
			{
				try
				{
					out.writeObject("CHARACTER SELECT");
					// TODO - what do I do here?
				}
				catch (IOException e)
				{
					ServerUpdater.this.close();
				}
			}
		});
	}

	/**
	 * Sends the given message to the server
	 * @param message The String sent to the server
	 * @throws IllegalStateException
	 */
	public void sendMessage(String message) throws IllegalStateException
	{
		if (client.connectionState != ConnectionState.GUEST
				&& client.connectionState != ConnectionState.HOST
				&& client.connectionState != ConnectionState.HaS_IN_GAME
				&& client.connectionState != ConnectionState.RTS_IN_GAME)
		{
			throw new IllegalStateException("Not in a room.");
		}
		queue.add(new Thread()
		{

			public void run()
			{
				try
				{
					out.writeObject("SEND MESSAGE");
					out.writeObject(message);
				}
				catch (IOException e)
				{
					ServerUpdater.this.close();
				}
			}
		});
	}

	/**
	 * Sets the Client to be ready
	 * @param ready The level of readiness the Client is
	 * @throws IllegalStateException
	 */
	public void setReadiness(Boolean ready) throws IllegalStateException
	{
		if (client.connectionState != ConnectionState.GUEST
				&& client.connectionState != ConnectionState.HOST)
		{
			throw new IllegalStateException("Not in a room, or already in a game.");
		}
		queue.add(new Thread()
		{

			public void run()
			{
				try
				{
					out.writeObject("SET READINESS");
					out.writeObject(ready);
				}
				catch (IOException e)
				{
					ServerUpdater.this.close();
				}
			}
		});
	}

	/**
	 * Adds a player to the room
	 * @param player The player being added to the room
	 * @throws IllegalStateException
	 */
	public void addPlayer(Player player) throws IllegalStateException
	{
		if (client.connectionState != ConnectionState.HOST)
		{
			throw new IllegalStateException("Not the host of a room.");
		}
		queue.add(new Thread()
		{

			public void run()
			{
				try
				{
					out.writeObject("ADD PLAYER");
					out.writeObject(player);
				}
				catch (IOException e)
				{
					ServerUpdater.this.close();
				}
			}
		});
	}

	/**
	 * Kicks the given player from the room
	 * @param player The player that will be kicked out of the room
	 * @throws IllegalStateException
	 */
	public void kickPlayer(Player player) throws IllegalStateException
	{
		if (client.connectionState != ConnectionState.HOST)
		{
			throw new IllegalStateException("Not the host of a room.");
		}
		queue.add(new Thread()
		{

			public void run()
			{
				try
				{
					out.writeObject("KICK PLAYER");
					out.writeObject(player);
				}
				catch (IOException e)
				{
					ServerUpdater.this.close();
				}
			}
		});
	}

	/**
	 * Updates the current GameRoom to the given GameRoom
	 * @param gameRoom The new current GameRoom
	 * @throws IllegalStateException
	 */
	public void updateGameRoom(GameRoom gameRoom)
			throws IllegalStateException
	{
		if (client.connectionState != ConnectionState.HOST)
		{
			throw new IllegalStateException("Not the host of a room.");
		}
		queue.add(new Thread()
		{

			public void run()
			{
				try
				{
					out.writeObject("UPDATE GAME ROOM");
					out.writeObject(gameRoom);
				}
				catch (IOException e)
				{
					ServerUpdater.this.close();
				}
			}
		});
	}

	/**
	 * Sets the Client to host the given room
	 * @param roomName The name of the room the Client will host
	 * @throws IllegalStateException
	 */
	public void hostRoom(String roomName) throws IllegalStateException
	{
		if (client.connectionState != ConnectionState.LOGGED_IN)
		{
			throw new IllegalStateException("Not logged in or already in a room.");
		}
		queue.add(new Thread()
		{

			public void run()
			{
				try
				{
					out.writeObject("HOST ROOM");
					out.writeObject(roomName);
				}
				catch (IOException e)
				{
					ServerUpdater.this.close();
				}
			}
		});
	}

	/**
	 * Gets the Client to try to join the given room
	 * @param room The room name the Client is trying to join
	 * @throws IllegalStateException
	 */
	public void joinRoom(GameRoom room) throws IllegalStateException
	{
		queue.add(new Thread()
		{

			public void run()
			{
				try
				{
					out.writeObject("JOIN ROOM");
					out.writeObject(room);
				}
				catch (IOException e)
				{
					ServerUpdater.this.close();
				}
			}
		});
	}

	/**
	 * Sends login credentials to the server
	 * @param username The username the user is trying to get authorized
	 * @param password The password the user is trying to get authorized
	 * @throws IllegalStateException
	 */
	public void login(String username, String password)
			throws IllegalStateException
	{
		if (client.connectionState != ConnectionState.NEWCOMER)
		{
			throw new IllegalStateException("Already logged in or disconnected.");
		}
		queue.add(new Thread()
		{

			public void run()
			{
				try
				{
					out.writeObject("LOGIN");
					out.writeObject(username);
					out.writeObject(password);
				}
				catch (IOException e)
				{
					ServerUpdater.this.close();
				}
			}
		});
	}

}
