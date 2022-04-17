package com.trogdadr2tr.game;

/**
 * Created by Danny on 12/1/2017.
 */
import java.util.concurrent.CopyOnWriteArrayList;
import com.trogdadr2tr.resources.*;

public class BuildingAttack
{

    /** The variable for the attacking unit from a building */
    private Building attacker;

    /**
     * The buildings attacker unit
     * @param attacker The value for the attacker received from the caller
     */
    public BuildingAttack(Building attacker)
    {
        this.attacker = attacker;
    }

    /**
     * Makes the unit do an attack based on ranged values
     * @param hSPlayers The value for a Hack-n'-Slash player received from the caller
     * @param deltaMilliseconds The value for time received from the caller
     */
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

    /**
     * Checks to see if an enemy is in range to attack
     * @param defender The value for the player defending received from the caller
     * @return True if the distance is less than or equal to the units range, false if not
     */
    public Boolean targetInRange(Player defender)
    {
        return distance(attacker.getX(), attacker.getY(), defender.y,
                defender.y) < attacker.getRange();
    }

    /**
     * Distance between two game objects
     * @param ax The value for the first x received from the caller
    * @param ay The value for the first y received from the caller
    * @param bx The value for the second x received from the caller
    * @param by The value for the second y received from the caller
    * @return The distance between them
    */
    private double distance(double ax, double ay, double bx, double by)
    {
       return Math.sqrt(Math.pow(ax - bx, 2)
             + Math.pow(ay - by, 2));
    }
}
