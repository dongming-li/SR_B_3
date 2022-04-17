package com.trogdadr2tr.game;

import java.util.concurrent.CopyOnWriteArrayList;

import com.trogdadr2tr.resources.*;

/** Handles buildings with the ability to attack.
 * 
 * @author Colt Rogness */
public class BuildingAttack
{

	/** Self explanitory. */
	private Building attacker;

	/** Set the attacker
	 * 
	 * @param attacker */
	public BuildingAttack(Building attacker)
	{
		this.attacker = attacker;
	}

	/** Preform an attack against one of the players if they are
	 * in range.
	 * 
	 * @param hSPlayers list of players to attack.
	 * @param deltaMilliseconds - time passed since last
	 *            attack. */
	public void doAttack(CopyOnWriteArrayList<Player> hSPlayers, long deltaMilliseconds)
	{
		for (Player player : hSPlayers)
		{
			if (targetInRange(player))
			{
				player.takeDamage(attacker.getDamage());
			}
		}
	}

	/** Determine if target is in range.
	 * 
	 * @param defender target
	 * @return */
	public Boolean targetInRange(Player defender)
	{
		return distance(attacker.getX(), attacker.getY(), defender.y,
				defender.y) < attacker.getRange();
	}

	/** Calculate distance between point (ax, ay) and (bx, by).
	 * 
	 * @param ax
	 * @param ay
	 * @param bx
	 * @param by
	 * @return distance between points */
	private double distance(double ax, double ay, double bx, double by)
	{
		return Math.sqrt(Math.pow(ax - bx, 2)
				+ Math.pow(ay - by, 2));
	}
}
