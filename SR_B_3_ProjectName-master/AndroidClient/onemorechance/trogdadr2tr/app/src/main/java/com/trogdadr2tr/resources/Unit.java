package com.trogdadr2tr.resources;

/**
 * Created by Danny on 11/7/2017.
 */
import java.io.Serializable;

public class Unit implements Serializable
{

    /** Unit MK I */
    private static final long serialVersionUID = 1L;

    public final long uniqueUnitID;

    /** The x position of this unit */
    private double xPos;

    /** The y position of this unit */
    private double yPos;

    /** The width of this unit */
    private double width;

    /** The height of this unit */
    private double height;

    /** The current health of this unit */
    private double health;

    /** The maximum health this unit can have */
    private double maxHealth;

    /** How fast the unit can move around the map */
    private double speed;

    /** The amount of health this unit takes away from the
     * defending object when this unit attacks */
    private double damage;

    /** How far away this unit can deal damage */
    private double range;

    /** What this unit is called */
    private String name;

    /** How much space the unit takes up in regards to total unit
     * capacity of RTSPlayer */
    private double cost;

    /**The amount of food it will cost to get a unit */
    private double foodCost;

    /** The amount of gold it will cost to get a unit */
    private double goldCost;

    /** The amount of wood it will cost to get a unit */
    private double woodCost;

    /** The amount of stone it will cost to get a unit */
    private double stoneCost;

    /** The Unit based on time */
    private Unit()
    {
        uniqueUnitID = System.currentTimeMillis();
    }

    /** Creates a new unit based on the String passed in
     *
     * @param x The x position this object will be created at
     * @param y The y position this object will be created at
     * @param n The name of this object. This determines what type
     *            of unit is created */
    public Unit(double x, double y, String n)
    {
        this();
        switch (n)
        {
            case "Calvary": // speed based unit
                health = 200;
                speed = 7;
                damage = 5;
                range = 2;
                width = 20;
                height = 40;
                cost = 4;
                foodCost = 5;
                goldCost = 5;
                woodCost = 5;
                stoneCost = 5;
                break;
            case "Magic": // AoE attacks
                health = 60;
                speed = 4;
                damage = 20;
                range = 30;
                width = 20;
                height = 20;
                cost = 2;
                foodCost = 5;
                goldCost = 5;
                woodCost = 5;
                stoneCost = 5;
                break;
            case "Soldier": // Melee dps
                health = 175;
                speed = 4;
                damage = 15;
                range = 1;
                width = 20;
                height = 20;
                cost = 2;
                foodCost = 5;
                goldCost = 5;
                woodCost = 5;
                stoneCost = 5;
                break;
            case "Minion": // cheap, weak unit
                health = 1;
                speed = 5;
                damage = 3;
                range = 1;
                width = 20;
                height = 20;
                cost = 1;
                foodCost = 5;
                goldCost = 5;
                woodCost = 5;
                stoneCost = 5;
                break;
            case "Ranged": // Ranged dps
                health = 100;
                speed = 5;
                damage = 15;
                range = 30;
                width = 20;
                height = 20;
                cost = 3;
                foodCost = 5;
                goldCost = 5;
                woodCost = 5;
                stoneCost = 5;
                break;
            case "Tank": // lots of health
                health = 400;
                speed = 3;
                damage = 10;
                range = 1;
                width = 30;
                height = 30;
                cost = 5;
                foodCost = 5;
                goldCost = 5;
                woodCost = 5;
                stoneCost = 5;
                break;
        }
        maxHealth = health;
        xPos = x;
        yPos = y;
        name = n;
    }

    /**
     * Chooses the unit based on the string that is passed into it
     * @param n name of the unit
     */
    public Unit(String n)
    {
        this();
        switch (n)
        {
            case "Calvary": // speed based unit
                health = 200;
                speed = 7;
                damage = 5;
                range = 2;
                width = 20;
                height = 40;
                cost = 4;
                break;
            case "Magic": // AoE attacks
                health = 60;
                speed = 4;
                damage = 20;
                range = 30;
                width = 20;
                height = 20;
                cost = 2;
                break;
            case "Soldier": // Melee dps
                health = 175;
                speed = 4;
                damage = 15;
                range = 1;
                width = 20;
                height = 20;
                cost = 2;
                break;
            case "Minion": // cheap, weak unit
                health = 1;
                speed = 5;
                damage = 3;
                range = 1;
                width = 20;
                height = 20;
                cost = 1;
                break;
            case "Ranged": // Ranged dps
                health = 100;
                speed = 5;
                damage = 15;
                range = 30;
                width = 20;
                height = 20;
                cost = 3;
                break;
            case "Tank": // lots of health
                health = 400;
                speed = 3;
                damage = 10;
                range = 1;
                width = 30;
                height = 30;
                cost = 5;
                break;
        }
        maxHealth = health;
        name = n;
    }

    /**
     * Checks to see if the objects are equal
     * @param o The object
     * @return True if they are equal, false otherwise
     */
    public boolean equals(Object o)
    {
        return (o instanceof Unit) && uniqueUnitID == ((Unit) o).uniqueUnitID;
    }

    /** Subtracts the parameter amount from the units health.
     * SHOULD NOT BE USED IF AMOUNT IS ENOUGH TO KILL THIS UNIT
     *
     * @param amount The amount of health this unit loses */
    public void takeDamage(double amount)
    {
        health -= amount;
    }

    /** Acts like moving the unit, but it sort of jumps to the
     * indicated x and y coords
     *
     * @param x The x coordinate the unit is moved to
     * @param y The y coordinate the unit is moved to */
    public void setPosition(double x, double y)
    {
        xPos = x;
        yPos = y;
    }

    /** Returns the cost of the unit
     *
     * @return The cost of the unit */
    public double getCost()
    {
        return cost;
    }

    /**
     * Returns the cost for gold
     * @return the cost for gold
     */
    public double getGoldCost()
    {
        return goldCost;
    }

    /**
     * Returns the cost for food
     * @return the cost for food
     */
    public double getFoodCost()
    {
        return foodCost;
    }

    /**
     * Returns the cost for wood
     * @return the cost for wood
     */
    public double getWoodCost()
    {
        return woodCost;
    }

    /**
     * Returns the cost for stone
     * @return the cost for stone
     */
    public double getStoneCost()
    {
        return stoneCost;
    }

    /** Returns the maxHealth variable
     *
     * @return The maximum amount of health this unit can have */
    public double getMaxHealth()
    {
        return maxHealth;
    }

    /** Returns the xPos variable
     *
     * @return The x position of this unit */
    public double getX()
    {
        return xPos;
    }

    /** Returns the yPos variable
     *
     * @return The y position of this unit */
    public double getY()
    {
        return yPos;
    }

    /** Returns the width variable
     *
     * @return The width of this unit */
    public double getWidth()
    {
        return width;
    }

    /** Returns the height variable
     *
     * @return The height of this unit */
    public double getHeight()
    {
        return height;
    }

    /** Returns the health variable
     *
     * @return The current health of this unit */
    public double getHealth()
    {
        return health;
    }

    /** Returns the speed variable
     *
     * @return How fast this unit can move around the map */
    public double getSpeed()
    {
        return speed;
    }

    /** Returns the damage variable
     *
     * @return The amount of damage this unit can inflict on other
     *         objects */
    public double getDamage()
    {
        return damage;
    }

    /** Returns the range variable
     *
     * @return The maximum distance this unit can attack */
    public double getRange()
    {
        return range;
    }

    /** Returns the name variable
     *
     * @return The name of this unit. This determines the other
     *         stats of this unit */
    public String getName()
    {
        return name;
    }

}
