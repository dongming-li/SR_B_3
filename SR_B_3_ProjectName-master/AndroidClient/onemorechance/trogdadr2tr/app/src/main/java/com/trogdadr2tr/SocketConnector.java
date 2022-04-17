package com.trogdadr2tr;
/**
 * Created by Danny on 10/10/2017.
 */
import android.os.AsyncTask;
import android.widget.TextView;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SocketConnector extends AsyncTask<Void, Void, Void>
{
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private String hostName;
    private int portNumber;
    private TextView console;
    private ServerMessenger act;
    private String text;

    /**
     * The Connector to the server
     * @param socket The socket received from the caller
     * @param hostName The hostname received from the caller
     * @param portNumber The portnumber received from the caller
     * @param console The console received from the caller
     * @param act The act received from the caller
     */
    SocketConnector(Socket socket, String hostName, int portNumber, TextView console, ServerMessenger act)
    {
        this.socket = socket;
        this.hostName = hostName;
        this.portNumber = portNumber;
        this.console = console;
        this.act = act;
        text = "";
    }

    /**
     * Connect to the Server
     * @param arg0 The value received from the caller
     * @return
     */
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

    public void sendNoodles(String yus) throws IOException
    {
        out.writeChars(yus);
    }

    /**
     * What is done after the connection is made
     * @param result The value received from the caller
     */
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