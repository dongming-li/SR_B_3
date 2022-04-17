package com.trogdadr2tr.socket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import com.trogdadr2tr.game.GameRoom;
import com.trogdadr2tr.game.Player;
import com.trogdadr2tr.resources.*;

/** Handle connection to a single client.
 * 
 * @author Colt Rogness */
public class ClientReciever extends Thread
{

	/** List of objects listening to this receiver. */
	private ArrayList<ClientListener> listeners;

	/** Input stream from the client socket. */
	private ObjectInputStream in;

	/** Parent thread, to access server variables. */
	private ClientConnection client;

	/** Control thread exit point. */
	private boolean listenToClient;

	/** Thread to handle communication with a client.
	 * 
	 * @param client */
	public ClientReciever(ClientConnection client, ObjectInputStream in)
	{
		this.client = client;
		this.in = in;
		this.listeners = new ArrayList<ClientListener>();
		this.listenToClient = true;
	}

	/** Close this listener. */
	public void close()
	{
		listenToClient = false;
	}

	/** Listen for user input and handle what they send. */
	@Override
	public void run()
	{
		while (listenToClient)
		{
			try
			{
				String input = (String) in.readObject();
				System.out.println("Input reciever from " + client + ": " + input);
				handler(input);
			}
			catch (IOException e)
			{
				System.err.println("ClientListener I/O error: " + e.getMessage());
				client.close();
			}
			catch (ClassNotFoundException e)
			{
				// no need to care, let the user deal with the
				// problem.
				System.err.println("ClientListener Class error: " + e.getMessage());
			}
		}
	}

	/** Handle different interactions from the client.
	 * 
	 * @param command used to determine what action to take.
	 * @throws IOException
	 * @throws ClassNotFoundException */
	private void handler(String command) throws ClassNotFoundException, IOException
	{
		Object o;
		switch (command)
		{
			/* For each case, attempt to read in the objects
			 * associated with it and send them to all the
			 * listeners. If there are any errors, abort the
			 * operation. */
			case "MOVE PLAYER":
				o = in.readObject();
				if (o instanceof MovementDirection)
				{
					MovementDirection move = (MovementDirection) o;
					o = in.readObject();
					if (o instanceof Point)
					{
						Point currentLocation = (Point) o;
						new Thread()
						{

							@Override
							public void run()
							{
								for (ClientListener n : listeners)
								{
									n.movePlayer(move, currentLocation);
								}
							}
						}.start();
					}
				}
				break;
			case "MOVE UNIT":
				o = in.readObject();
				if (o instanceof Unit)
				{
					Unit unit = (Unit) o;
					o = in.readObject();
					if (o instanceof Point)
					{
						Point destination = (Point) o;
						new Thread()
						{

							public void run()
							{
								for (ClientListener n : listeners)
								{
									n.moveUnit(unit, destination);
								}
							}
						}.start();
					}
				}
				break;
			case "MOVE UNITS":
				o = in.readObject();
				if (o instanceof ArrayList<?>)
				{
					ArrayList<?> list = (ArrayList<?>) o;
					ArrayList<Unit> units = new ArrayList<Unit>();
					for (Object n : list)
					{
						if (n instanceof Unit)
						{
							units.add((Unit) n);
						}
					}
					o = in.readObject();
					if (o instanceof Point)
					{
						Point destination = (Point) o;
						new Thread()
						{

							public void run()
							{
								for (ClientListener n : listeners)
								{
									n.moveUnits(units, destination);
								}
							}
						}.start();
					}
				}
				break;
			case "PLACE BUILDING":
				o = in.readObject();
				if (o instanceof String)
				{
					String buildingType = (String) o;
					o = in.readObject();
					if (o instanceof Point)
					{
						Point location = (Point) o;
						new Thread()
						{

							public void run()
							{
								for (ClientListener n : listeners)
								{
									n.placeBuilding(buildingType, location);
								}
							}
						}.start();
					}
				}
				break;
			case "SPAWN UNIT":
				o = in.readObject();
				if (o instanceof Building)
				{
					Building spawningBuilding = (Building) o;
					o = in.readObject();
					if (o instanceof String)
					{
						String unitType = (String) o;
						new Thread()
						{

							public void run()
							{
								for (ClientListener n : listeners)
								{
									n.spawnUnit(spawningBuilding, unitType);
								}
							}
						}.start();
					}
				}
				break;
			case "DELETE BUILDING":
				o = in.readObject();
				if (o instanceof Building)
				{
					Building building = (Building) o;
					new Thread()
					{

						public void run()
						{
							for (ClientListener n : listeners)
							{
								n.deleteBuilding(building);
							}
						}
					}.start();
				}
				break;
			case "LEAVE ROOM":
				new Thread()
				{

					public void run()
					{
						for (ClientListener n : listeners)
						{
							n.leaveRoom();
						}
					}
				}.start();
				break;
			case "CHARACTER SELECT":
				// o = in.readObject();
				// TODO - what do I do here?
				new Thread()
				{

					public void run()
					{
						for (ClientListener n : listeners)
						{
							n.characterSelect();
						}
					}
				}.start();
				break;
			case "SEND MESSAGE":
				o = in.readObject();
				if (o instanceof String)
				{
					String messageText = (String) o;
					new Thread()
					{

						public void run()
						{
							for (ClientListener n : listeners)
							{
								n.sendMessage(messageText);
							}
						}
					}.start();
				}
				break;
			case "SET READINESS":
				o = in.readObject();
				if (o instanceof Boolean)
				{
					Boolean ready = (Boolean) o;
					new Thread()
					{

						public void run()
						{
							for (ClientListener n : listeners)
							{
								n.setReadiness(ready);
							}
						}
					}.start();
				}
				break;
			case "ADD PLAYER":
				o = in.readObject();
				if (o instanceof Player)
				{
					Player player = (Player) o;
					new Thread()
					{

						public void run()
						{
							for (ClientListener n : listeners)
							{
								n.addPlayer(player);
							}
						}
					}.start();
				}
				break;
			case "KICK PLAYER":
				o = in.readObject();
				if (o instanceof Player)
				{
					Player player = (Player) o;
					new Thread()
					{

						public void run()
						{
							for (ClientListener n : listeners)
							{
								n.kickPlayer(player);
							}
						}
					}.start();
				}
				break;
			case "UPDATE GAME ROOM":
				o = in.readObject();
				if (o instanceof GameRoom)
				{
					GameRoom room = (GameRoom) o;
					new Thread()
					{

						public void run()
						{
							for (ClientListener n : listeners)
							{
								n.updateGameRoom(room);
							}
						}
					}.start();
				}
				break;
			case "HOST ROOM":
				o = in.readObject();
				if (o instanceof String)
				{
					String roomName = (String) o;
					new Thread()
					{

						public void run()
						{
							for (ClientListener n : listeners)
							{
								n.hostRoom(roomName);
							}
						}
					}.start();
				}
				break;
			case "JOIN ROOM":
				o = in.readObject();
				if (o instanceof GameRoom)
				{
					GameRoom room = (GameRoom) o;
					new Thread()
					{

						public void run()
						{
							for (ClientListener n : listeners)
							{
								n.joinRoom(room);
							}
						}
					}.start();
				}
				break;
			case "LOGIN":
				o = in.readObject();
				if (o instanceof String)
				{
					String username = (String) o;
					o = in.readObject();
					if (o instanceof String)
					{
						String password = (String) o;
						new Thread()
						{

							public void run()
							{
								for (ClientListener n : listeners)
								{
									n.login(username, password);
								}
							}
						}.start();
					}
				}
				break;
			default:
				System.err.println(command + " is not a handled message?!");
				break;
		}
		// }
		// catch (IOException e)
		// {
		// System.err.println("CleintListener handler: I/O error:
		// " + e.getMessage());
		// client.close();
		// }
		// catch (ClassNotFoundException e)
		// {
		// // Ignore. Someone sent the wrong class, and we don't
		// // need to care.
		// }
	}

	/** Add another listener to this receiver.
	 * 
	 * @param toAdd the listener to add. */
	public void addListener(ClientListener toAdd)
	{
		listeners.add(toAdd);
	}
}
