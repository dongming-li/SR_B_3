package com.trogdadr2tr.socket;

import java.util.ArrayList;
import java.util.EventListener;

import com.trogdadr2tr.game.GameRoom;
// import com.trogdadr2tr.game.*;
import com.trogdadr2tr.resources.*;

/** Interface to listen to the ServerReciever.
 *
 * @author Colt Rogness */
public interface ServerListener extends EventListener
{

    /** Called when the server requests a user login. */
    public void requestLogin();

    /** Called when the server changes the state of the
     * connection. Can be triggered by joining or leaving a game,
     * starting or leaving a game, logging in, etc. */
    public void updateConnectionState(ConnectionState state);

    /** Called when the server receives an invalid login, and
     * requests another.
     *
     * @param errorMessage error message sent by the server. */
    public void serverErrorMessage(String errorMessage);

    /** Called when the server sends an ArrayList of the GameRooms
     * available to be joined.
     *
     * @param gameRooms current open rooms on the server */
    public void downloadGameRooms(ArrayList<GameRoom> gameRooms);

    /** Called when server sends an updated game room to the
     * player.
     *
     * @param gameRoom updated game room */
    public void downloadGameRoom(GameRoom gameRoom);

    /** Called when a new message is sent to the player.
     *
     * @param message the new message. */
    public void newMessage(Message message);

    /** Called when the server sends the starting game map.
     *
     * @param map the map of the game */
    public void downloadMap(GameMap map);

    /** Called when the server sends unit updates.
     *
     * @param unit the updated unit
     */
    public void updateUnit(Unit unit);

    /** Called when the server sends building updates.
     *
     * @param building the updated building
     */
    public void updateBuilding(Building building);

    /** Called when the server sends out a new unit.
     *
     * @param unit the new unit */
    public void addUnit(Unit unit);

    /** Called when the server sends out a new building.
     *
     * @param building the new building */
    public void addBuilding(Building building);

    /** Called when the server removes and old unit.
     *
     * @param unit the unit to be removed */
    public void removeUnit(Unit unit);

    /** Called when the server removes and old building.
     *
     * @param building the building to be removed */
    public void removeBuilding(Building building);

    /** Called when the server updates the RTS player's resources.
     *
     * @param gold total gold
     * @param wood total wood
     * @param food total food
     * @param stone total stone */
    public void updateResources(int gold, int wood, int food, int stone);
}
