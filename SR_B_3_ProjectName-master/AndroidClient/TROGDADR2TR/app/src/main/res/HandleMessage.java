package com.example.r0xas.serverconnection;

import android.os.AsyncTask;
import android.widget.TextView;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * Created by Argetlam on 2017-10-11.
 */

public class HandleMessage extends AsyncTask<Void, Void, String> {

    //    private Receiver listener;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private String response;
    private TextView console;
//    private ServerMessenger act;

    public HandleMessage(ObjectOutputStream out, ObjectInputStream in, TextView console) {
        this.out = out;
        this.in = in;
        this.console = console;
        response = new String();
    }

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

    private String handleCommand(String command) {
        try {
            ArrayList<com.example.r0xas.serverconnection.Building> buildings = new ArrayList<com.example.r0xas.serverconnection.Building>();
            Object o = in.readObject();
            if (o instanceof ArrayList<?>) {
                ArrayList<?> l = (ArrayList<?>) o;
                if (l.size() > 0) {
                    for (Object n : l) {
                        if (n instanceof com.example.r0xas.serverconnection.Building) {
                            buildings.add((com.example.r0xas.serverconnection.Building) n);
                        }
                    }
                }
            }
            response = "buildings: " + buildings;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            response = "ClassNotFoundException: " + e.getMessage();
        } catch (IOException e) {
            e.printStackTrace();
            response = "IOException: " + e.getMessage();
        }
        return response;
    }

    @Override
    protected void onPostExecute(String result) {
        console.append(response + "\n");
        super.onPostExecute(result);
        // act.doTheThing(message);
        // listener.Receiver();
    }
}
