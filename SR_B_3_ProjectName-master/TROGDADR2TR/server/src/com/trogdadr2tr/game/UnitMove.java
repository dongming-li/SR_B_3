package com.trogdadr2tr.game;

import com.trogdadr2tr.resources.Point;
import com.trogdadr2tr.resources.Unit;

/** Handle a unit moving.
 * 
 * @author Colt Rogness */
public class UnitMove
{

	/** Moving unit. */
	public Unit unit;

	/** Unit's destination. */
	public Point destination;

	/** Construct default unit move.
	 * 
	 * @param unit to move
	 * @param destination of the unit */
	public UnitMove(Unit unit, Point destination)
	{
		this.unit = unit;
		this.destination = destination;
	}

	/** Perform the move.
	 * 
	 * @param deltaMilliseconds time since the last move
	 * @return */
	public Boolean doMove(long deltaMilliseconds)
	{
		double x = unit.getX();
		double y = unit.getY();
		System.out.println("unit: (" + x + ", " + y + ")\ndest: (" + destination.getX()
				+ ", " + destination.getY() + ").\n");
		double distance = unit.getSpeed() * deltaMilliseconds / 10;
		if (x == destination.getX() && y == destination.getY())
		{
			return true;
		}
		if (Math.abs(x - destination.getX()) < distance)
		{
			x = destination.getX();
		}
		else if (x > destination.getX())
		{
			x -= distance;
		}
		else if (x < destination.getX())
		{
			x += distance;
		}
		if (Math.abs(y - destination.getY()) < distance)
		{
			y = destination.getY();
		}
		else if (y > destination.getY())
		{
			y -= distance;
		}
		else if (y < destination.getY())
		{
			y += distance;
		}
		System.out.println(x + "   " + y);
		unit.setPosition(x, y);
		// return whether the unit is done moving
		return ((int) unit.getX() == destination.getX()
				&& (int) unit.getY() == destination.getY());
	}

}
