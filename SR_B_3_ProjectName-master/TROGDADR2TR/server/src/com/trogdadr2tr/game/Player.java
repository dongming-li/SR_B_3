package com.trogdadr2tr.game;

import java.io.Serializable;

import com.trogdadr2tr.resources.MovementDirection;

/** Game data needed for in-game characters.
 * 
 * @author Colt Rogness */
public class Player implements Serializable
{

	/** Player MK 0 */
	private static final long serialVersionUID = 0;

	/** Direction the player is moving. */
	public MovementDirection direction;

	/** player's x coordinate. */
	public double x;

	/** Player's y coordinate. */
	public double y;

	/** Player's hp. */
	private int hp;

	/** Player ready to start game. */
	private Boolean ready;

	/** Type of player (rts, haS). */
	public final PlayerType playerType;

	/** Construct a player.
	 * 
	 * @param playerType type of player to create. */
	public Player(PlayerType playerType)
	{
		direction = MovementDirection.STATIONARY;
		ready = false;
		this.playerType = playerType;
	}

	/** Getter for ready.
	 * 
	 * @return */
	public Boolean isReady()
	{
		return ready;
	}

	/** Setter for <direction>.
	 * 
	 * @param move */
	public void setDirection(MovementDirection move)
	{
		direction = move;
	}

	/** Getter for <direction>.
	 * 
	 * @return */
	public MovementDirection getDiretion()
	{
		return direction;
	}

	/** Setter for <ready>.
	 * 
	 * @param ready */
	public void setReadiness(Boolean ready)
	{
		this.ready = ready;
	}

	/** Check if this player is the host of a room.
	 * 
	 * @return */
	public Boolean isHost()
	{
		return playerType == PlayerType.RTS;
	}

	/** Move the player in <direction>.
	 * 
	 * @param milisecondsPast time past since last move */
	public void doMove(long milisecondsPast)
	{
		// TODO - collision detection
		int speed = 10;
		x += direction.x / speed * milisecondsPast;
		y += direction.y / speed * milisecondsPast;
	}

	/** Decrement the player's hp according to <damage>.
	 * 
	 * @param damage */
	public void takeDamage(int damage)
	{
		hp -= damage;
		if (hp <= 0)
		{
			die();
		}
	}

	/** Kill the player. */
	private void die()
	{
		// fuckin dead m8
	}
}
