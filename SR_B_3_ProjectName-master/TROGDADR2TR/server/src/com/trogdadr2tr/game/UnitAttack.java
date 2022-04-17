package com.trogdadr2tr.game;

import com.trogdadr2tr.resources.Point;
import com.trogdadr2tr.resources.Unit;

/** Handle a unit attacking another unit.
 * 
 * @author Colt Rogness */
public class UnitAttack
{

	/** Attacking Unit. */
	public Unit attacker;

	/** Defending unit. */
	public Unit defender;

	/** Construct default UnitAttck.
	 * 
	 * @param attacker unit
	 * @param defender unit */
	public UnitAttack(Unit attacker, Unit defender)
	{
		this.attacker = attacker;
		this.defender = defender;
	}

	/** Perform an attack.
	 * 
	 * @param deltaMilliseconds time passed since last attack
	 * @return */
	public Boolean doAttack(long deltaMilliseconds)
	{
		if (targetInRange())
		{
			defender.takeDamage(attacker.getDamage());
			if (defender.getHealth() <= 0)
			{
				return true;
			}
		}
		else
		{
			new UnitMove(attacker,
					new Point((int) defender.getX(), (int) defender.getY()))
							.doMove(deltaMilliseconds);
		}
		return false;
	}

	/** Check if the defender is in range of the attacker.
	 * 
	 * @return */
	public Boolean targetInRange()
	{
		return distance(attacker.getX(), attacker.getY(), defender.getX(),
				defender.getY()) < attacker.getRange();
	}

	/** Calculate the distance between points (ax, ay) and (bx,
	 * by).
	 * 
	 * @param ax
	 * @param ay
	 * @param bx
	 * @param by
	 * @return */
	private double distance(double ax, double ay, double bx, double by)
	{
		return Math.sqrt(Math.pow(ax - bx, 2)
				+ Math.pow(ay - by, 2));
	}

}
