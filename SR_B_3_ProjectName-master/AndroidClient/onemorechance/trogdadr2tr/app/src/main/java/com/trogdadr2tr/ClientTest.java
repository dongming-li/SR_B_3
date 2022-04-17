package com.trogdadr2tr;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by Argetlam on 2017-12-05.
 */

public class ClientTest extends Thread
{
    public Socket socket;

    public ClientTest()
    {
        // do things
    }

    public void run()
    {
        // kek
    }

    public void close()
    {
        // heh
    }

    private void setup()
    {
        try
        {
            socket = new Socket("proj-309-sr-b-3.cs.iastate.edu", 4444);
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
        }
        catch (IOException e)
        {
            // meh
        }
    }

}
