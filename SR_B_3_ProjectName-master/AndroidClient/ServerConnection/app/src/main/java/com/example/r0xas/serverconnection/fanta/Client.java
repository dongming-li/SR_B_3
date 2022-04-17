package com.example.r0xas.serverconnection.fanta;
/**
 * Created by Danny on 10/10/2017.
 */
import android.os.AsyncTask;
import android.widget.TextView;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client extends AsyncTask<Void, Void, Void>
{
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private String hostName;
    private int portNumber;
    private TextView console;
    private MainActivity act;
    private String text;

    Client(Socket socket, String hostName, int portNumber, TextView console, MainActivity act)
    {
        this.socket = socket;
        this.hostName = hostName;
        this.portNumber = portNumber;
        this.console = console;
        this.act = act;
        text = "";
    }

    @Override
    protected Void doInBackground(Void... arg0)
    {
        if (socket != null && socket.isConnected())
        {
            try
            {
                out.writeObject("quit");
                socket.close();
                socket = null;
            }
            catch (IOException e)
            {
                return null;
            }
        }
        try
        {
            hostName = "proj-309-sr-b-3.cs.iastate.edu";
            portNumber = 4444;
            socket = new Socket(hostName, portNumber);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
        }
        catch (IOException e)
        {
            text = "IOException: " + e.getMessage();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void result)
    {
        console.append(text + "\n");
        console.append("connected to " + socket + "\n");
        act.setSocket(socket);
        act.setOutput(out);
        act.setInput(in);
        super.onPostExecute(result);
    }
}