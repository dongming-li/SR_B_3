package com.trogdadr2tr.socket;

import java.net.Socket;
import java.net.ServerSocket;
import java.io.IOException;
import java.util.ArrayList;

import com.trogdadr2tr.game.GameRoom;

/** Server thread to accept connections from client sockets.
 * 
 * @author Colt Rogness */
public class Server extends Thread
{

	/** Singleton instance. */
	private static Server instance;

	/** Game rooms open on the server. */
	private static ArrayList<GameRoom> gameRooms;

	/** Server host socket. Accepts client connections. */
	private ServerSocket serverSocket;

	/** Entry point of the server code.
	 * 
	 * @param args */
	public static void main(String[] args)
	{
		Server.getInstance().start();
	}

	/** Start the server-side socket, at default port number.
	 * 
	 * @throws IOException while binding server to port. */
	private Server() throws IOException
	{
		this(4444);
	}

	/** Start the server-side socket at the given port number.
	 * 
	 * @param portNumber to bind the server to.
	 * @throws IOException while binding server to portNumber. */
	private Server(int portNumber) throws IOException
	{
		gameRooms = new ArrayList<GameRoom>();
		try
		{
			serverSocket = new ServerSocket(portNumber);
			System.out.println("Started " + serverSocket);
		}
		catch (IOException e)
		{
			System.out.println(
					"Exception caught when trying to listen to port " + portNumber + ".");
			e.printStackTrace();
			throw e;
		}
	}

	/** Access to the singleton from the outside.
	 * 
	 * @return
	 * @throws IOException */
	public static Server getInstance()
	{
		if (instance == null)
		{
			try
			{
				instance = new Server();
			}
			catch (IOException e)
			{
				System.err.println("The program failed. I/O error: " + e.getMessage());
				e.printStackTrace();
				System.exit(MAX_PRIORITY);
			}
		}
		return instance;
	}

	/** Continuously accept clients, make them their own thread,
	 * and store it for future reference. */
	@Override
	public void run()
	{
		while (true)
		{
			Socket client;
			try
			{
				client = serverSocket.accept();
				ClientConnection clientThread = new ClientConnection(client);
				clientThread.start();
				System.out.println(clientThread + " connected");
			}
			catch (IOException e)
			{
				System.out.println("I/O error: " + e.getMessage());
			}
		}
		// TODO - make a shutdown method, so we can gently stop
		// the server
	}

	/** Add a game room to the server.
	 * 
	 * @param room to add
	 * @throws IllegalArgumentException if there is already a room
	 *             with that name */
	public synchronized void addGameRoom(GameRoom room) throws IllegalArgumentException
	{
		// TODO - gameRooms may need to be synchronized. Only the
		// future will tell.
		for (GameRoom gameRoom : gameRooms)
		{
			if (gameRoom.getRoomName() == room.getRoomName())
			{
				throw new IllegalArgumentException(
						"Game room with that name already exists.");
			}
		}
		gameRooms.add(room);
	}

	/** Retrieve a game room by it's name.
	 * 
	 * @param gameRoom
	 * @return */
	public GameRoom getRoom(GameRoom gameRoom)
	{
		for (GameRoom room : gameRooms)
		{
			if (gameRoom.getRoomName() == room.getRoomName())
			{
				return room;
			}
		}
		return null;
	}
}
