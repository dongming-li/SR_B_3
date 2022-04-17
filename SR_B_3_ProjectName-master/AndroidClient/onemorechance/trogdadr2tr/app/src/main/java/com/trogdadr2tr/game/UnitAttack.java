package com.trogdadr2tr.game;

/**
 * Created by Danny on 12/1/2017.
 */
import com.trogdadr2tr.resources.Point;
import com.trogdadr2tr.resources.Unit;

public class UnitAttack
{

    /** The attacker of the units */
    public Unit attacker;

    /** The defender of the units */
    public Unit defender;

    /**
     * The unit attackers and defenders
     * @param attacker The value for an attacker received from the caller
     * @param defender The value for a defender received from the caller
     */
    public UnitAttack(Unit attacker, Unit defender)
    {
        this.attacker = attacker;
        this.defender = defender;
    }

    /**
     * Checks to see if the target is in range and if it is to do an attack
     * @param deltaMilliseconds The value for time in between attacks and moving received from the caller
     * @return If you do an attack or not
     */
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

    /**
     * Checks to see if a unit is within range of you
     * @return If there is a target within a certain distance of a unit then it's true, otherwise, false
     */
    public Boolean targetInRange()
    {
        return distance(attacker.getX(), attacker.getY(), defender.getX(),
                defender.getY()) < attacker.getRange();
    }

    /**
     * The distance in-between two objects on the map
     * @param ax The first value for x received from the caller
     * @param ay The first value for y received from the caller
     * @param bx The second value for x received from the caller
     * @param by The second value for y received from the caller
     * @return The distance between two game objects
     */
    private double distance(double ax, double ay, double bx, double by)
    {
        return Math.sqrt(Math.pow(ax - bx, 2)
                + Math.pow(ay - by, 2));
    }
}