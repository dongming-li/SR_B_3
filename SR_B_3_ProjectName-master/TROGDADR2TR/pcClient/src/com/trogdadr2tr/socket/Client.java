package com.trogdadr2tr.socket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/** Handle communication with the server.
 * 
 * @author Colt Rogness */
public class Client
{

	/**
	 * The instance of this client
	 */
	private static Client instance;

	/** Socket connected to the server. */
	private Socket serverSocket;

	/**
	 * The object that receives commands from the server
	 */
	public ServerReciever receiver;

	/**
	 * The object that connects to the server to update the server
	 */
	public ServerUpdater updater;

	/**
	 * The current state this Client is in
	 */
	public ConnectionState connectionState;

	/** Connect the socket at the default location.
	 * 
	 * @throws IOException - there was a problem connecting to the
	 *             server. */
	private Client() throws IOException
	{
		this(4444, "proj-309-sr-b-3.cs.iastate.edu");
	}

	/** Connect the socket at the given address and port number.
	 * 
	 * @param portNumber
	 * @param hostName
	 * @throws IOException */
	private Client(int portNumber, String hostName) throws IOException
	{
		try
		{
			serverSocket = new Socket(hostName, portNumber);
			updater = new ServerUpdater(this, new ObjectOutputStream(serverSocket.getOutputStream()));
			receiver = new ServerReciever(this, new ObjectInputStream(serverSocket.getInputStream()));
			updater.start();
			receiver.start();
			connectionState = ConnectionState.NEWCOMER;
		}
		catch (IOException e)
		{
			close();
			throw e;
		}
	}

	/** On closing the client, close the the socket.
	 * 
	 * @throws IOException */
	public void close() throws IOException
	{
		updater.close();
		receiver.close();
		serverSocket.close();
	}

	/**
	 * Returns the Client instance
	 * @return The Client instance
	 */
	public static Client getInstance()
	{
		return instance;
	}

	/**
	 * Starts the Client and gives it an instance
	 * @throws IOException
	 */
	public static void init() throws IOException
	{
		if (instance == null)
		{
			instance = new Client();
		}
	}

	/**
	 * Adds a ServerListener to the receiver
	 * @param toAdd The ServerListener added
	 */
	public void addListener(ServerListener toAdd)
	{
		receiver.addListener(toAdd);
	}

}
