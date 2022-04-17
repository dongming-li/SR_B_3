package com.trogdadr2tr.game;

import com.trogdadr2tr.resources.*;

/** Interface for a listener to gameRoom.
 * 
 * @author Colt Rogness */
public interface GameListener
{

	/** Called when gameRoom updates resources.
	 * 
	 * @param gold new gold amount
	 * @param wood new wood amount
	 * @param food new food amount
	 * @param stone new stone amount */
	public void updateResources(int gold, int wood, int food, int stone);

	/** Called when gameRoom adds a new building.
	 * 
	 * @param building */
	public void addBuilding(Building building);

	/** Called when gameRoom adds a new unit.
	 * 
	 * @param unit */
	public void addUnit(Unit unit);

	/** Called when gameRoom removes a building.
	 * 
	 * @param building */
	public void removeBuilding(Building building);

	/** Called whne gameRoom removes a unit.
	 * 
	 * @param unit */
	public void removeUnit(Unit unit);

	/** Called when gameRoom updates a building.
	 * 
	 * @param building */
	public void updateBuilding(Building building);

	/** Called when gameRoom updates a unit.
	 * 
	 * @param unit */
	public void updateUnit(Unit unit);

	/** Called when gameRoom sends a message recieved from a
	 * player.
	 * 
	 * @param message */
	public void sendMessage(Message message);

	/** Called when gameRoom updates a player.
	 * 
	 * @param player */
	public void updatePlayer(Player player);

	/** Called when gameRoom starts the game. */
	public void startGame();

	/** Called when gameRoom starts the game and sends out the
	 * initial map to the players.
	 * 
	 * @param map */
	public void sendMap(GameMap map);
}
