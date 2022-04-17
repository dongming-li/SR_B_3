package com.trogdadr2tr;

import android.os.AsyncTask;
import android.widget.TextView;

import com.trogdadr2tr.game.Building;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/** Handling the messaging back and forth with the client and server
 * Created by Argetlam on 2017-10-11.
 */
public class HandleMessage extends AsyncTask<Void, Void, String> {

    //    private Receiver listener;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private String response;
    private TextView console;
//    private ServerMessenger act;

    /**
     * Constructor for HandleMessage
     * @param out  OutputStream received from the caller
     * @param in InputStream received from the caller
     * @param console The text received from the caller
     */
    public HandleMessage(ObjectOutputStream out, ObjectInputStream in, TextView console) {
        this.out = out;
        this.in = in;
        this.console = console;
        response = new String();
    }

    /**
     *
     * @param voids
     * @return The response sent from the server.
     */
    @Override
    protected String doInBackground(Void... voids) {
        try {
            String command = (String) in.readObject();
            return handleCommand(command);
        } catch (IOException e) {
            e.printStackTrace();
            response = "You disconnected from the server already you baka!";
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            response = "ClassNotFoundException: " + e.toString();
        }
        return response;
    }

    /**
     *Adds and receives ArrayList information sent by the server and processes the
     * information and adds a new item into the database.
     * @param command String command to send to the server
     * @return The organized and new string that will display to the screen.
     */
    private String handleCommand(String command) {
        try {
            ArrayList<Building> buildings = new ArrayList<Building>();
            buildings.add(new Building(1,1,1,1,1,"Client"));
            out.writeObject("BUILDINGS");
            out.writeObject(buildings);
            Object o = in.readObject();
            if (o instanceof ArrayList<?>) {
                ArrayList<?> l = (ArrayList<?>) o;
                if (l.size() > 0) {
                    for (Object n : l) {
                        if (n instanceof Building) {
                            buildings.add((Building) n);
                        }
                    }
                }
            }
            response = "buildings: " + buildings;
//            buildings.add(new Building(1,1,1,1,1,"Client"));
//            out.writeObject("BUILDINGS");
//            out.writeObject(buildings);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            response = "ClassNotFoundException: " + e.getMessage();
        } catch (IOException e) {
            e.printStackTrace();
            response = "IOException: " + e.getMessage();
        }
        return response;
    }

    /**
     *Excutes after all the code is run constantly.
     * @param result Outputs the information to console, which will display everything in result.
     */
    @Override
    protected void onPostExecute(String result) {
        console.append(response + "\n");
        super.onPostExecute(result);
        // act.doTheThing(message);
        // listener.Receiver();
    }
}
