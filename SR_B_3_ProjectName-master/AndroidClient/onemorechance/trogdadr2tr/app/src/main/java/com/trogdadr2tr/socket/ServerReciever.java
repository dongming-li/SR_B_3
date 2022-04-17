package com.trogdadr2tr.socket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import com.trogdadr2tr.game.*;
import com.trogdadr2tr.resources.*;

/**
 * Created by Argetlam on 2017-12-05.
 */

public class ServerReciever extends Thread {

    private ArrayList<ServerListener> listeners;

    private ObjectInputStream in;

    private Client client;

    private boolean listenToServer;

    public ServerReciever(Client client, ObjectInputStream in)
    {
        this.client = client;
        this.in = in;
        listeners = new ArrayList<ServerListener>();
        listenToServer = true;
    }

    public void close()
    {
        listenToServer = false;
    }

    public void run()
    {
        while (listenToServer)
        {
            try
            {
                String command = (String) in.readObject();
                handleInput(command);
            }
            catch (ClassNotFoundException e)
            {
                System.err.println("ServerReciever: Class not found error: " + e.getMessage());
            }
            catch (IOException e)
            {
                System.err.println("ServerReciever: I/O error: " + e.getMessage());
            }
        }
    }

    private void handleInput(String command)
    {
        try
        {
            switch (command)
            {
                case "DOWNLOAD MAP":
                    try
                    {
                        Object o = in.readObject();
                        if (o instanceof GameMap)
                        {
                            final GameMap map = (GameMap) o;
                            new Thread()
                            {

                                public void run()
                                {
                                    for (ServerListener n : listeners)
                                    {
                                        n.downloadMap(map);
                                    }
                                }
                            }.start();
                        }
                    }
                    catch (ClassNotFoundException e)
                    {
                    }
                    break;
                case "UPDATE CONNECTION STATE":
                    try
                    {
                        Object o = in.readObject();
                        if (o instanceof ConnectionState)
                        {
                            final ConnectionState connectionState = (ConnectionState) o;
                            new Thread()
                            {

                                public void run()
                                {
                                    client.connectionState = connectionState;
                                    for (ServerListener n : listeners)
                                    {
                                        n.updateConnectionState(connectionState);
                                    }
                                }
                            }.start();
                        }
                    }
                    catch (ClassNotFoundException e)
                    {

                    }
                    break;
                case "REMOVE BUILDING":
                    try
                    {
                        Object o = in.readObject();
                        if (o instanceof Building)
                        {
                            final Building building = (Building) o;
                            new Thread()
                            {
                                public void run()
                                {
                                    for (ServerListener n : listeners)
                                    {
                                        n.removeBuilding(building);
                                    }
                                }
                            }.start();
                        }
                    }
                    catch (ClassNotFoundException e)
                    {

                    }
                    break;
                case "REMOVE UNIT":
                    try
                    {
                        Object o = in.readObject();
                        if (o instanceof Unit)
                        {
                            final Unit unit = (Unit) o;
                            new Thread()
                            {
                                public void run()
                                {
                                    for (ServerListener n : listeners)
                                    {
                                        n.removeUnit(unit);
                                    }
                                }
                            }.start();
                        }
                    }
                    catch (ClassNotFoundException e)
                    {

                    }
                    break;
                case "ADD BUILDING":
                    try
                    {
                        Object o = in.readObject();
                        if (o instanceof Building)
                        {
                            final Building building = (Building) o;
                            new Thread()
                            {

                                public void run()
                                {
                                    for (ServerListener n : listeners)
                                    {
                                        n.addBuilding(building);
                                    }
                                }
                            }.start();
                        }
                    }
                    catch (ClassNotFoundException e)
                    {

                    }
                    break;
                case "ADD UNIT":
                    try
                    {
                        Object o = in.readObject();
                        if (o instanceof Unit)
                        {
                            final Unit unit = (Unit) o;
                            new Thread()
                            {

                                public void run()
                                {
                                    for (ServerListener n : listeners)
                                    {
                                        n.addUnit(unit);
                                    }
                                }
                            }.start();
                        }
                    }
                    catch (ClassNotFoundException e)
                    {

                    }
                    break;
                case "UPDATE BUILDING":
                    try
                    {
                        Object o = in.readObject();
                        if (o instanceof Building)
                        {
                            final Building building = (Building) o;
                            new Thread()
                            {

                                public void run()
                                {
                                    for (ServerListener n : listeners)
                                    {
                                        n.updateBuilding(building);
                                    }
                                }
                            }.start();
                        }
                    }
                    catch (ClassNotFoundException e)
                    {

                    }
                    break;
                case "UPDATE UNIT":
                    try
                    {
                        Object o = in.readObject();
                        if (o instanceof Unit)
                        {
                            final Unit unit = (Unit) o;
                            new Thread()
                            {

                                public void run()
                                {
                                    for (ServerListener n : listeners)
                                    {
                                        n.updateUnit(unit);
                                    }
                                }
                            }.start();
                        }
                    }
                    catch (ClassNotFoundException e)
                    {

                    }
                    break;
                case "NEW MESSAGE":
                    try
                    {
                        Object o = in.readObject();
                        if (o instanceof Message)
                        {
                            final Message message = (Message) o;
                            new Thread()
                            {

                                public void run()
                                {
                                    for (ServerListener n : listeners)
                                    {
                                        n.newMessage(message);
                                    }
                                }
                            }.start();
                        }
                    }
                    catch (ClassNotFoundException e)
                    {

                    }
                    break;
                case "UPDATE GAMEROOM":
                    try
                    {
                        Object o = in.readObject();
                        if (o instanceof GameRoom)
                        {
                            final GameRoom gameRoom = (GameRoom) o;
                            new Thread()
                            {

                                public void run()
                                {
                                    for (ServerListener n : listeners)
                                    {
                                        n.downloadGameRoom(gameRoom);
                                    }
                                }
                            }.start();
                        }
                    }
                    catch (ClassNotFoundException e)
                    {

                    }
                    break;
                case "DOWNLOAD GAMEROOMS":
                    try
                    {
                        Object o = in.readObject();
                        if (o instanceof ArrayList<?>)
                        {
                            ArrayList<?> l = (ArrayList<?>) o;
                            final ArrayList<GameRoom> gameRooms = new ArrayList<GameRoom>();
                            for (Object n : l)
                            {
                                if (n instanceof GameRoom)
                                {
                                    gameRooms.add((GameRoom) n);
                                }
                            }
                            new Thread()
                            {
                                public void run()
                                {
                                    for (ServerListener n : listeners)
                                    {
                                        n.downloadGameRooms(gameRooms);
                                    }
                                }
                            }.start();
                        }
                    }
                    catch (ClassNotFoundException e)
                    {

                    }
                    break;
                case "INVALID LOGIN":
                    try
                    {
                        Object o = in.readObject();
                        if (o instanceof String)
                        {
                            final String errorMessage = (String) o;
                            new Thread()
                            {

                                public void run()
                                {
                                    for (ServerListener n : listeners)
                                    {
                                        n.serverErrorMessage(errorMessage);
                                    }
                                }
                            }.start();
                        }
                    }
                    catch (ClassNotFoundException e)
                    {

                    }
                    break;
                case "REQUEST LOGIN":
                    new Thread()
                    {

                        public void run()
                        {
                            for (ServerListener n : listeners)
                            {
                                n.requestLogin();
                            }
                        }
                    }.start();
                    break;
                default:
                    break;
                case "UPDATE RESOURCES":
                    try
                    {
                        Object o = in.readObject();
                        if (o instanceof Integer)
                        {
                            final int gold = (int) o;
                            o = in.readObject();
                            if (o instanceof Integer)
                            {
                                final int wood = (int) o;
                                o = in.readObject();
                                if (o instanceof Integer)
                                {
                                    final int food = (int) o;
                                    o = in.readObject();
                                    if (o instanceof Integer)
                                    {
                                        final int stone = (int) o;
                                        new Thread()
                                        {

                                            public void run()
                                            {
                                                for (ServerListener n : listeners)
                                                {
                                                    n.updateResources(gold, wood, food,
                                                            stone);
                                                }
                                            }
                                        }.start();
                                    }
                                }
                            }
                        }
                    }
                    catch (ClassNotFoundException e)
                    {
                        close();
                    }
                    break;
            }
        }
        catch (IOException e)
        {
            close();
        }
    }

    public void addListener(ServerListener toAdd)
    {
        listeners.add(toAdd);
    }
}
