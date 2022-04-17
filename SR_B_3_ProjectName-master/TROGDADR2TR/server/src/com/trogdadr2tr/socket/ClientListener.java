package com.trogdadr2tr.socket;

import java.util.ArrayList;
import java.util.EventListener;

import com.trogdadr2tr.game.*;
import com.trogdadr2tr.resources.*;

/** Interface to listen to the clientReciever.
 * 
 * @author Colt Rogness */
public interface ClientListener extends EventListener
{

	/** Called when the client sends a username and password to
	 * login.
	 * 
	 * @param username the user's username
	 * @param password the user's password (Strings are secure) */
	public void login(String username, String password);

	/** Called when the client sends a request to join a room.
	 * 
	 * @param room the room to join */
	public void joinRoom(GameRoom room);

	/** Called when the client sends a request to host a room. */
	public void hostRoom(String name);

	/** Called when the client sends changes to the game room they
	 * are hosting.
	 * 
	 * @param room the updated room */
	public void updateGameRoom(GameRoom room);

	/** Called when the client sends a request to kick a player
	 * from a room they are hosting.
	 * 
	 * @param player the player the client wants to kick. */
	public void kickPlayer(Player player);

	/** Called when the client sends a request to invite a player
	 * to a room they are hosting.
	 * 
	 * @param player the player they want to invite */
	public void addPlayer(Player player);

	/** Called when the client sends a request to change
	 * readiness.
	 * 
	 * @param ready whether they are ready or not */
	public void setReadiness(Boolean ready);

	/** Called when the client sends a message
	 * 
	 * @param messageText the text they want to be in their
	 *            message. */
	public void sendMessage(String messageText);

	/** Called when the client sends a request to leave a room. */
	public void leaveRoom();

	// TODO - what does this entail?
	public void characterSelect();

	/** Called when the client sends a request to place a building
	 * at a given location.
	 * 
	 * @param buildingType identifier for the type of building to
	 *            be placed
	 * @param location where they want the builiding placed. */
	public void placeBuilding(String buildingType, Point location);

	/** Called when the client sends a request to delete a
	 * building from the map.
	 * 
	 * @param b building to be deleted */
	public void deleteBuilding(Building building);

	/** Called when the client sends a request to move a unit.
	 * 
	 * @param u the unit the want to move
	 * @param destination the location they want this unit to move
	 *            to */
	public void moveUnit(Unit unit, Point destination);

	/** Called when the client sends a request to move a group of
	 * units.
	 * 
	 * @param units the ArrayList of units to be moved
	 * @param dextination the location they want these units to
	 *            move to */
	public void moveUnits(ArrayList<Unit> units, Point destination);

	/** Called when the client sends a request to move their
	 * player.
	 * 
	 * @param move the direction they want to move in
	 * @param currentLocation the location the player was in as of
	 *            sending this request */
	public void movePlayer(MovementDirection move, Point currentLocation);
	// TODO - H&S attacks.
	// I'm thinking store what way they are facing, just attack in
	// an arc there?

	/**
	 * Called when the client sends a request to spawn a unit from a building.
	 * @param spawningBuilding
	 * @param unitType
	 */
	public void spawnUnit(Building spawningBuilding, String unitType);
}
