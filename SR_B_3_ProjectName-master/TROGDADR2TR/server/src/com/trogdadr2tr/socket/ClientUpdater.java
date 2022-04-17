package com.trogdadr2tr.socket;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import com.trogdadr2tr.game.*;
import com.trogdadr2tr.resources.*;

/** Interface to push updates to client.
 * 
 * @author Colt Rogness */
public class ClientUpdater extends Thread
{

	/** Output stream to the client socket. */
	private ObjectOutputStream out;

	private Boolean connectionOpen;

	private ThreadQueue queue;

	/** Construct with output stream to client socket.
	 * 
	 * @param out stream to client. */
	public ClientUpdater(ObjectOutputStream out)
	{
		this.out = out;
		queue = new ThreadQueue();
		connectionOpen = true;
	}

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

	/** Close connection to the client. */
	public void close()
	{
		try
		{
			out.writeObject("QUIT");
			connectionOpen = false;
		}
		catch (IOException e)
		{
			// well no helping that.
		}
	}

	/** Push a connectionsState update to the client.
	 * 
	 * @param connectionState new connection state */
	public void updateConnectionState(ConnectionState connectionState)
	{
		queue.add(new Thread()
		{

			public void run()
			{
				try
				{
					out.writeObject("UPDATE CONNECTION STATE");
					out.writeObject(connectionState);
				}
				catch (IOException e)
				{
					ClientUpdater.this.close();
				}

			}
		});

	}

	/** Push the game map to the client.
	 * 
	 * @param map the game map */
	public void sendMap(GameMap map)
	{
		queue.add(new Thread()
		{

			public void run()
			{
				try
				{
					out.writeObject("DOWNLOAD MAP");
					out.writeObject(map);
				}
				catch (IOException e)
				{
					ClientUpdater.this.close();
				}
			}
		});
	}

	/** Push a building to be removed to the client.
	 * 
	 * @param building to be removed */
	public void removeBuilding(Building building)
	{
		queue.add(new Thread()
		{

			public void run()
			{
				try
				{
					out.writeObject("REMOVE BUILDING");
					out.writeObject(building);
				}
				catch (IOException e)
				{
					ClientUpdater.this.close();
				}
			}
		});
	}

	/** Push a unit to be removed to the client.
	 * 
	 * @param unit to be removed */
	public void removeUnit(Unit unit)
	{
		queue.add(new Thread()
		{

			public void run()
			{
				try
				{
					out.writeObject("REMOVE UNIT");
					out.writeObject(unit);
				}
				catch (IOException e)
				{
					ClientUpdater.this.close();
				}
			}
		});
	}

	/** Push a building to be added to the client.
	 * 
	 * @param building to be added */
	public void addBuilding(Building building)
	{
		queue.add(new Thread()
		{

			public void run()
			{
				try
				{
					out.writeObject("ADD BUILDING");
					out.writeObject(building);
				}
				catch (IOException e)
				{
					ClientUpdater.this.close();
				}
			}
		});
	}

	/** Push a unit to be added to the client.
	 * 
	 * @param unit to be added */
	public void addUnit(Unit unit)
	{
		queue.add(new Thread()
		{

			public void run()
			{
				try
				{
					out.writeObject("ADD UNIT");
					out.writeObject(unit);
				}
				catch (IOException e)
				{
					ClientUpdater.this.close();
				}
			}
		});
	}

	public void updateBuilding(Building building)
	{
		queue.add(new Thread()
		{

			public void run()
			{
				try
				{
					out.writeObject("UPDATE BUILDING");
					out.writeObject(building);
				}
				catch (IOException e)
				{
					ClientUpdater.this.close();
				}
			}
		});
	}

	/** Push a unit to be updated to the client.
	 * 
	 * @param unit to be updated */
	public void updateUnit(Unit unit)
	{
		queue.add(new Thread()
		{

			public void run()
			{
				try
				{
					out.writeObject("UPDATE UNIT");
					out.writeObject(unit);
				}
				catch (IOException e)
				{
					ClientUpdater.this.close();
				}
			}
		});
	}

	/** Push a message to the client.
	 * 
	 * @param message */
	public void newMessage(Message message)
	{
		queue.add(new Thread()
		{

			public void run()
			{
				try
				{
					out.writeObject("NEW MESSAGE");
					out.writeObject(message);
				}
				catch (IOException e)
				{
					ClientUpdater.this.close();
				}
			}
		});
	}

	/** Push an update to a game room to the client.
	 * 
	 * @param gameRoom */
	public void updateGameRoom(GameRoom gameRoom)
	{
		queue.add(new Thread()
		{

			public void run()
			{
				try
				{
					out.writeObject("UPDATE GAMEROOM");
					out.writeObject(gameRoom);
				}
				catch (IOException e)
				{
					ClientUpdater.this.close();
				}
			}
		});
	}

	/** Push a list of game rooms on the server to the player.
	 * 
	 * @param gameRooms list of game rooms */
	public void sendGameRooms(ArrayList<GameRoom> gameRooms)
	{
		queue.add(new Thread()
		{

			public void run()
			{
				try
				{
					out.writeObject("DOWNLOAD GAMEROOMS");
					out.writeObject(gameRooms);
				}
				catch (IOException e)
				{
					ClientUpdater.this.close();
				}
			}
		});
	}

	/** Push an error message to the client after a failed login.
	 * 
	 * @param errorMessage */
	public void invalidLogin(String errorMessage)
	{
		queue.add(new Thread()
		{

			public void run()
			{
				try
				{
					out.writeObject("INVALID LOGIN");
					out.writeObject(errorMessage);
				}
				catch (IOException e)
				{
					ClientUpdater.this.close();
				}
			}
		});
	}

	/** Request a login from the client. */
	public void requestLogin()
	{
		queue.add(new Thread()
		{

			public void run()
			{
				try
				{
					out.writeObject("REQUEST LOGIN");
				}
				catch (IOException e)
				{
					ClientUpdater.this.close();
				}
			}
		});
	}

	/** Push gold, wood, food, and stone to be updated to the
	 * client
	 * 
	 * @param gold
	 * @param wood
	 * @param food
	 * @param stone */
	public void updateResources(int gold, int wood, int food, int stone)

	{
		queue.add(new Thread()
		{

			public void run()
			{
				try
				{
					out.writeObject("UPDATE RESOURCES");
					out.writeObject(gold);
					out.writeObject(wood);
					out.writeObject(food);
					out.writeObject(stone);
				}
				catch (IOException e)
				{
					ClientUpdater.this.close();
				}
			}
		});
	}

	/** Push a player to be updated to the client.
	 * 
	 * @param player to be update */
	public void updatePlayer(Player player)
	{
		queue.add(new Thread()
		{

			public void run()
			{
				try
				{
					out.writeObject("UPDATE PLAYER");
					out.writeObject(player);
				}
				catch (IOException e)
				{
					ClientUpdater.this.close();
				}
			}
		});
	}

}
