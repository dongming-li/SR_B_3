package com.trogdadr2tr.resources;

import java.io.Serializable;

/**
 * Created by Danny on 10/13/2017.
 */
public class Building implements Serializable
{

    /** Building class MK II */
    private static final long serialVersionUID = 2L;

    /** unique */
    public final long uniqueBuildingID;

    /** This is the x position of the building */
    private int xPos;

    /** This is the y position of the building */
    private int yPos;

    /** This is the width of the building */
    private int width;

    /** This is the height of the building */
    private int height;

    /** This is the hit points of the building */
    private int health;

    /** This is the current health of the building */
    private int currentHealth;

    /** This is the damage */
    private int damage;

    /** This is the range of the building */
    private int range;

    /** This is the name of the building */
    private String name;

    /** This is the gold cost of the building */
    private int goldCost;

    /** This is the wood cost of the building */
    private int woodCost;

    /** This is the food cost of the building */
    private int foodCost;

    /** This is the stone cost of the building */
    private int stoneCost;

    /** This is the gold rate of the building */
    private int goldRate;

    /** This is the wood rate of the building */
    private int woodRate;

    /** This is the food rate of the building */
    private int foodRate;

    /** This is the stone rate of the building */
    private int stoneRate;

    /**
     * Constructor to make a building with no functionality
     */
    private Building()
    {
        uniqueBuildingID = System.currentTimeMillis();
        goldRate = 0;
        woodRate = 0;
        foodRate = 0;
        stoneRate = 0;

    }

    /** This creates a new building object with the following
     * parameters
     *
     * @param x The x position of the building
     * @param y The y position of the building
     * @param name The name of the building */
    public Building(int x, int y, String name)
    {
        this();
        int baseWidth = 100;
        int baseHeight = 100;
        int baseHP = 100;
        switch (name)
        {
            case "Base":
                width = baseWidth * 2;
                height = baseHeight * 2;
                health = baseHP * 10;
                goldCost = Integer.MAX_VALUE;
                foodCost = Integer.MAX_VALUE;
                woodCost = Integer.MAX_VALUE;
                stoneCost = Integer.MAX_VALUE;
                break;
            case "Food Generator":
                width = baseWidth;
                height = baseHeight;
                health = (int) (baseHP * 1.5);
                goldCost = 2;
                foodCost = 2;
                woodCost = 2;
                stoneCost = 2;
                foodRate = 1;
                break;
            case "Gold Generator":
                width = baseWidth;
                height = baseHeight;
                health = (int) (baseHP * 1.5);
                goldCost = 2;
                foodCost = 2;
                woodCost = 2;
                stoneCost = 2;
                goldRate = 1;
                break;
            case "Wood Generator":
                width = baseWidth;
                height = baseHeight;
                health = (int) (baseHP * 1.5);
                goldCost = 2;
                foodCost = 2;
                woodCost = 2;
                stoneCost = 2;
                woodRate = 1;
                break;
            case "Stone Generator":
                width = baseWidth;
                height = baseHeight;
                health = (int) (baseHP * 1.5);
                goldCost = 2;
                foodCost = 2;
                woodCost = 2;
                stoneCost = 2;
                stoneRate = 1;
                break;
            case "Garrison":
                width = baseWidth * 2;
                height = baseHeight * 2;
                health = baseHP * 5;
                goldCost = 10;
                foodCost = 5;
                woodCost = 10;
                stoneCost = 10;
                break;
            case "Long Range Defense":
                width = (int) (baseWidth * 1.5);
                height = (int) (baseHeight * 1.5);
                health = (int) (baseHP * 3.5);
                goldCost = 20;
                foodCost = 5;
                woodCost = 15;
                stoneCost = 10;
                range = 50;
                damage = 10;
                break;
            case "Short Range Defense":
                width = baseWidth * 2;
                height = baseHeight * 2;
                health = baseHP * 6;
                goldCost = 10;
                foodCost = 5;
                woodCost = 20;
                stoneCost = 20;
                range = 25;
                damage = 10;
                break;
            case "Spawner":
                width = baseWidth * 2;
                height = baseHeight * 2;
                health = baseHP * 6;
                goldCost = 20;
                foodCost = 10;
                woodCost = 15;
                stoneCost = 20;
                break;
            case "Wall Vertical":
                width = (int) (baseWidth * .5);
                height = baseHeight * 3;
                health = baseHP * 2;
                goldCost = 5;
                foodCost = 2;
                woodCost = 5;
                stoneCost = 25;
                break;
            case "Wall Horizontal":
                width = baseWidth * 3;
                height = (int) (baseHeight * .5);
                health = baseHP * 2;
                goldCost = 5;
                foodCost = 2;
                woodCost = 5;
                stoneCost = 25;
                break;
        }
        xPos = x;
        yPos = y;
        currentHealth = health;
        this.name = name;
    }

    /**
     * When no parameters are passed in, this method will put the building at 0,0
     * @param name The name of the building received from the caller
     */
    public Building(String name)
    {
        this(0, 0, name);
    }

    /**
     * Building that has a location on the map
     * @param location The point value received from the caller
     * @param buildingType The type of building string received from the caller
     */
    public Building(Point location, String buildingType)
    {
        this((int) location.getX(), (int) location.getY(), buildingType);
    }

    /**
     * Checks to see if the building is equal to another building
     * @param o The object received from the caller
     * @return If the objects are equal the value returned is true, otherwise, false
     */
    public boolean equals(Object o)
    {
        return (o instanceof Building)
                && uniqueBuildingID == ((Building) o).uniqueBuildingID;
    }

    /** Returns the cost of gold for this building
     *
     * @return The cost of gold */
    public int getGoldCost()
    {
        return goldCost;
    }

    /** Returns the cost of wood for this building
     *
     * @return The cost of wood */
    public int getWoodCost()
    {
        return woodCost;
    }

    /** Returns the cost of food for this building
     *
     * @return The cost of food */
    public int getFoodCost()
    {
        return foodCost;
    }

    /** Returns the cost of stone for this building
     *
     * @return The cost of stone */
    public int getStoneCost()
    {
        return stoneCost;
    }

    public void takeDamage(int amount)
    {
        currentHealth -= amount;
    }

    /** Returns the name of the building
     *
     * @return What the building is called */
    public String getName()
    {
        return name;
    }

    /** Returns the health of the building
     *
     * @return The hit points of the building */
    public int getHealth()
    {
        return health;
    }

    /** Returns the current health of the building
     *
     * @return The current health of the building */
    public int getCurrentHealth()
    {
        return currentHealth;
    }

    /** Returns the width of the building
     *
     * @return The width of the building */
    public int getWidth()
    {
        return width;
    }

    /** Returns the height of the building
     *
     * @return The height of the building */
    public int getHeight()
    {
        return height;
    }

    /**
     * Sets the x position of the building
     * @param x the x position of the building received from the caller
     */
    public void setX(int x)
    {
        this.xPos = x;
    }

    /**
     * Sets the y position of the building
     * @param y the y position of the building received from the caller
     */
    public void setY(int y)
    {
        this.yPos = y;
    }

    /** returns the x position of the building
     *
     * @return The x position of the building */
    public int getX()
    {
        return xPos;
    }

    /** Returns the y position of the building
     *
     * @return The y position of the building */
    public int getY()
    {
        return yPos;
    }

    /**
     * Returns the gold rate of production
     * @return the gold rate of production
     */
    public int goldRate()
    {
        return goldRate;
    }

    /**
     * Returns the wood rate of production
     * @return the wood rate of production
     */
    public int woodRate()
    {
        return woodRate;
    }

    /**
     * Returns the food rate of production
     * @return the food rate of production
     */
    public int foodRate()
    {
        return foodRate;
    }

    /**
     * Returns the stone rate of production
     * @return the stone rate of production
     */
    public int stoneRate()
    {
        return stoneRate;
    }

    /**
     * Checks to see if units spawn based on the buildings at a specific spot
     * @param unit The unit received from the caller
     * @return True if it sets the correct position, otherwise false
     */
    public Boolean spawn(Unit unit)
    {
        if (name.equals("Spawner"))
        {
            double x = xPos + width / 2;
            double y = yPos + height + 10;

            unit.setPosition(x, y);
            return true;
        }
        return false;
    }

    /**
     * Returns the amount of damage from building
     * @return the amount of damage from building
     */
    public int getDamage()
    {
        return damage;
    }

    /**
     * Returns the range of the building
     * @return the range of the building
     */
    public int getRange()
    {
        return range;
    }

    /**
     * Debugging step to make sure it got the correct name and ID
     * @return the name and building as a string
     */
    public String toString() {
        return name + ": " + uniqueBuildingID;
    }
}
