package com.trogdadr2tr.socket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by Colt Rogness on 2017-12-04.
 */

public class Client extends Thread
{
    private static Client instance;

    private ServerReciever reciever;

    public  ServerUpdater updater;

    public ConnectionState connectionState;

    private Boolean connected;

    private Client()
    {
        // do not allow
    }

    private Client(String serverAddress, int serverPortNum) throws IOException
    {
        try
        {
            Socket socket = new Socket(serverAddress, serverPortNum);
            updater = new ServerUpdater(this, new ObjectOutputStream(socket.getOutputStream()));
            reciever = new ServerReciever(this, new ObjectInputStream(socket.getInputStream()));
            updater.start();
            reciever.start();
            connectionState = ConnectionState.NEWCOMER;
            connected = true;
        }
        catch (IOException e)
        {
            close();
        }
    }

    public static Client getInstance()
    {
        return instance;
    }

    public static void init() throws IOException, InterruptedException
    {
        if (instance == null) {
            Thread thread = new Thread() {
                @Override
                public void run() {
                    try {
                        instance = new Client("proj-309-sr-b-3.cs.iastate.edu", 4444);
                        instance.start();
                    }
                    catch (IOException e)
                    {
                        instance = null;
                    }
                }
            };
            thread.start();
            while (thread.isAlive()) {
            }
            thread.join();
        }
    }

    public void run()
    {
        while(connected) {
            try {
                sleep(10000);
            } catch (InterruptedException e) {
                // whatever dude
            }
        }
    }

    private void close()
    {
        connected = false;
    }

    public void addListener(ServerListener toAdd)
    {
        reciever.addListener(toAdd);
    }

}
