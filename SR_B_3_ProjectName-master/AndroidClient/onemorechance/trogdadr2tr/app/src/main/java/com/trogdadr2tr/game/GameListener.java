package com.trogdadr2tr.game;

/**
 * Created by Danny on 12/1/2017.
 */
import com.trogdadr2tr.resources.*;

/**
 * Interface for the game listener
 */
public interface GameListener
{
    /**
     * Updates resources you have RTS Player info
     * @param gold The value for gold received from the caller
     * @param wood The value for wood received from the caller
     * @param food The value for food received from the caller
     * @param stone The value for stone received from the caller
     */
    public void updateResources(int gold, int wood, int food, int stone);

    /**
     * Adds a building on the map
     * @param building The value for building received from the caller
     */
    public void addBuilding(Building building);

    /**
     * Adds a new unit to the map
     * @param unit The value for unit received from the caller
     */
    public void addUnit(Unit unit);

    /**
     * Removing a building from the map
     * @param building The value for building received from the caller
     */
    public void removeBuilding(Building building);

    /**
     * Removing a building from the map
     * @param unit The value for unit received from the call
     */
    public void removeUnit(Unit unit);

    /**
     * updating a building position on the map
     * @param building The updated value for building received from the call
     */
    public void updateBuilding(Building building);

    /**
     * updating a position of a unit on the map
     * @param unit The updated value for unit received from the call
     */
    public void updateUnit(Unit unit);

    /**
     * Sends a message
     * @param message The value for the message received from the caller
     */
    public void sendMessage(Message message);

    /**
     * Updates the player on the map
     * @param player The updated value for player received from the caller
     */
    public void updatePlayer(Player player);

    /**
     * Starts the game
     */
    public void startGame();

    /**
     * sends the map
    * @param map The value for map received from the caller
    */
    public void sendMap(GameMap map);
}
