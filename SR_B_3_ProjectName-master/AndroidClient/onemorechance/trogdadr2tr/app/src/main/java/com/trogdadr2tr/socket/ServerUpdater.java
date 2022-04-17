package com.trogdadr2tr.socket;

import com.trogdadr2tr.game.GameRoom;
import com.trogdadr2tr.game.Player;
import com.trogdadr2tr.resources.*;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * Created by Argetlam on 2017-12-05.
 */

public class ServerUpdater extends Thread {

    private ObjectOutputStream out;

    private ThreadQueue queue;

    private Client client;

    private Boolean connectionOpen;

    public ServerUpdater(Client client, ObjectOutputStream out)
    {
        this.client = client;
        this.out = out;
        this.queue = new ThreadQueue();
        connectionOpen = true;
    }

    public void run()
    {
        while (connectionOpen)
        {
            try
            {
                sleep(100);
                Thread thread = queue.pop();
                if (thread != null)
                {
                    thread.start();
                    thread.join();
                }
            }
            catch (InterruptedException e)
            {
                // do nothing because I don't feel like it
            }
        }
    }

    public void close()
    {
        connectionOpen = false;
        try
        {
            out.writeObject("QUIT");
        }
        catch (IOException e)
        {
            // oh no, it's closed when I'm trying to close it. Oh well.
        }
    }

    public void movePlayer(final MovementDirection move, final Point location) throws IllegalStateException
    {
        if (client.connectionState != ConnectionState.HaS_IN_GAME)
        {
            throw new IllegalStateException("Not in a game.");
        }
        queue.add(new Thread()
        {

            public void run()
            {
                try
                {
                    out.writeObject("MOVE PLAYER");
                    out.writeObject(move);
                    out.writeObject(location);
                }
                catch (IOException e)
                {
                    ServerUpdater.this.close();
                }
            }
        });
    }

    public void moveUnit(final Unit unit, final Point destination)
            throws IllegalStateException
    {
        if (client.connectionState != ConnectionState.RTS_IN_GAME)
        {
            throw new IllegalStateException("Not in a game.");
        }
        queue.add(new Thread()
        {

            public void run()
            {
                try
                {
                    out.writeObject("MOVE UNIT");
                    out.writeObject(unit);
                    out.writeObject(destination);
                }
                catch (IOException e)
                {
                    ServerUpdater.this.close();
                }
            }
        });

    }

    public void moveUnits(final ArrayList<Unit> units, final Point destination)
            throws IllegalStateException
    {
        if (client.connectionState != ConnectionState.RTS_IN_GAME)
        {
            throw new IllegalStateException("Not in a game.");
        }
        queue.add(new Thread()
        {

            public void run()
            {
                try
                {
                    out.writeObject("MOVE UNITS");
                    out.writeObject(units);
                    out.writeObject(destination);
                }
                catch (IOException e)
                {
                    ServerUpdater.this.close();
                }
            }
        });

    }

    public void placeBuilding(final String name, final Point placeLocation)
            throws IllegalStateException
    {
        if (client.connectionState != ConnectionState.RTS_IN_GAME)
        {
            System.err.println("ConnectionState is " + client.connectionState);
            throw new IllegalStateException("Not in a game.");
        }
        queue.add(new Thread()
        {

            public void run()
            {
                try
                {
                    out.writeObject("PLACE BUILDING");
                    out.writeObject(name);
                    out.writeObject(placeLocation);
                }
                catch (IOException e)
                {
                    ServerUpdater.this.close();
                }
            }
        });
    }

    public void spawnUnit(final Building spawningBuilding, final String unitName)
            throws IllegalStateException
    {
        if (client.connectionState != ConnectionState.RTS_IN_GAME)
        {
            throw new IllegalStateException("Not in a game.");
        }
        queue.add(new Thread()
        {

            public void run()
            {
                try
                {
                    out.writeObject("SPAWN UNIT");
                    out.writeObject(spawningBuilding);
                    out.writeObject(unitName);
                }
                catch (IOException e)
                {
                    ServerUpdater.this.close();
                }
            }
        });
    }

    public void deleteBuilding(final Building building)
            throws IllegalStateException
    {
        if (client.connectionState != ConnectionState.RTS_IN_GAME)
        {
            throw new IllegalStateException("Not in a game.");
        }
        queue.add(new Thread()
        {

            public void run()
            {
                try
                {
                    out.writeObject("DELETE BUILDING");
                    out.writeObject(building);
                }
                catch (IOException e)
                {
                    ServerUpdater.this.close();
                }
            }
        });
    }

    public void leaveRoom() throws IllegalStateException
    {
        if (client.connectionState != ConnectionState.HOST
                && client.connectionState != ConnectionState.GUEST
                && client.connectionState != ConnectionState.RTS_IN_GAME
                && client.connectionState != ConnectionState.HaS_IN_GAME)
        {
            throw new IllegalStateException("Not in a room.");
        }
        queue.add(new Thread()
        {

            public void run()
            {
                try
                {
                    out.writeObject("LEAVE ROOM");
                }
                catch (IOException e)
                {
                    ServerUpdater.this.close();
                }
            }
        });
    }

    public void characterSelect() throws IllegalStateException
    {
        if (client.connectionState != ConnectionState.HOST
                && client.connectionState != ConnectionState.GUEST)
        {
            throw new IllegalStateException("");
        }
        queue.add(new Thread()
        {

            public void run()
            {
                try
                {
                    out.writeObject("CHARACTER SELECT");
                    // TODO - what do I do here?
                }
                catch (IOException e)
                {
                    ServerUpdater.this.close();
                }
            }
        });
    }

    public void sendMessage(final String message) throws IllegalStateException
    {
        if (client.connectionState != ConnectionState.GUEST
                && client.connectionState != ConnectionState.HOST
                && client.connectionState != ConnectionState.HaS_IN_GAME
                && client.connectionState != ConnectionState.RTS_IN_GAME)
        {
            throw new IllegalStateException("Not in a room.");
        }
        queue.add(new Thread()
        {

            public void run()
            {
                try
                {
                    out.writeObject("SEND MESSAGE");
                    out.writeObject(message);
                }
                catch (IOException e)
                {
                    ServerUpdater.this.close();
                }
            }
        });
    }

    public void setReadiness(final Boolean ready) throws IllegalStateException
    {
        if (client.connectionState != ConnectionState.GUEST
                && client.connectionState != ConnectionState.HOST)
        {
            throw new IllegalStateException("Not in a room, or already in a game.");
        }
        queue.add(new Thread()
        {

            public void run()
            {
                try
                {
                    out.writeObject("SET READINESS");
                    out.writeObject(ready);
                }
                catch (IOException e)
                {
                    ServerUpdater.this.close();
                }
            }
        });
    }

    public void addPlayer(final Player player) throws IllegalStateException
    {
        if (client.connectionState != ConnectionState.HOST)
        {
            throw new IllegalStateException("Not the host of a room.");
        }
        queue.add(new Thread()
        {

            public void run()
            {
                try
                {
                    out.writeObject("ADD PLAYER");
                    out.writeObject(player);
                }
                catch (IOException e)
                {
                    ServerUpdater.this.close();
                }
            }
        });
    }

    public void kickPlayer(final Player player) throws IllegalStateException
    {
        if (client.connectionState != ConnectionState.HOST)
        {
            throw new IllegalStateException("Not the host of a room.");
        }
        queue.add(new Thread()
        {

            public void run()
            {
                try
                {
                    out.writeObject("KICK PLAYER");
                    out.writeObject(player);
                }
                catch (IOException e)
                {
                    ServerUpdater.this.close();
                }
            }
        });
    }

    public void updateGameRoom(final GameRoom gameRoom)
            throws IllegalStateException
    {
        if (client.connectionState != ConnectionState.HOST)
        {
            throw new IllegalStateException("Not the host of a room.");
        }
        queue.add(new Thread()
        {

            public void run()
            {
                try
                {
                    out.writeObject("UPDATE GAME ROOM");
                    out.writeObject(gameRoom);
                }
                catch (IOException e)
                {
                    ServerUpdater.this.close();
                }
            }
        });
    }

    public void hostRoom(final String roomName) throws IllegalStateException
    {
        if (client.connectionState != ConnectionState.LOGGED_IN)
        {
            throw new IllegalStateException("Not logged in or already in a room.");
        }
        queue.add(new Thread()
        {

            public void run()
            {
                try
                {
                    out.writeObject("HOST ROOM");
                    out.writeObject(roomName);
                }
                catch (IOException e)
                {
                    ServerUpdater.this.close();
                }
            }
        });
    }

    public void joinRoom(final GameRoom room) throws IllegalStateException
    {
        queue.add(new Thread()
        {

            public void run()
            {
                try
                {
                    out.writeObject("JOIN ROOM");
                    out.writeObject(room);
                }
                catch (IOException e)
                {
                    ServerUpdater.this.close();
                }
            }
        });
    }

    public void login(final String username, final String password)
            throws IllegalStateException
    {
        if (client.connectionState != ConnectionState.NEWCOMER)
        {
            throw new IllegalStateException("Already logged in or disconnected.");
        }
        queue.add(new Thread()
        {

            public void run()
            {
                try
                {
                    out.writeObject("LOGIN");
                    out.writeObject(username);
                    out.writeObject(password);
                }
                catch (IOException e)
                {
                    ServerUpdater.this.close();
                }
            }
        });
    }

}
