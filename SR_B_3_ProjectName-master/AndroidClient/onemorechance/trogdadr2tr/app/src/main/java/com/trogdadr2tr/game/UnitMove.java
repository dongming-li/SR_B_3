package com.trogdadr2tr.game;

/**
 * Created by Danny on 12/1/2017.
 */
import com.trogdadr2tr.resources.Point;
import com.trogdadr2tr.resources.Unit;

public class UnitMove 
{

    /** The unit that is moving */
    public Unit unit;

    /** The destination that the unit is going to */
    public Point destination;

    /**
     * The unit the is moving and where it is moving to
     * @param unit The value of the unit received from the caller
     * @param destination The distance value received from the caller
     */
    public UnitMove(Unit unit, Point destination)
    {
        this.unit = unit;
        this.destination = destination;
    }

    /**
     * Checks to see if you are moving and if you collided with any game objects
     * @param deltaMilliseconds The value for time needed received from the caller
     * @return If you did move
     */
    public Boolean doMove(long deltaMilliseconds)
    {
        // TODO - collision detection
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
        } else if (x < destination.getX())
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

        unit.setPosition(x, y);

        // return whether the unit is done moving
        return ((int) unit.getX() == destination.getX()
                && (int) unit.getY() == destination.getY());
    }
}
