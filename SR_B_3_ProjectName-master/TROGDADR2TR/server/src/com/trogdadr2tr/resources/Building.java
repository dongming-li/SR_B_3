package com.trogdadr2tr.resources;

import java.io.Serializable;

/** @author Connor Brown, Colt Rogness */
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

	/** The amount of damage this building can inflict. If
	 * building cannot attack, damage is 0. */
	private int damage;

	/** Building's attack range. */
	private int range;

	/** This is the name of the building */
	private String name;

	/** Gold cost to make this building. */
	private int goldCost;

	/** Wood cost to make this building. */
	private int woodCost;

	/** Food cost to make this building. */
	private int foodCost;

	/** Stone cost to make this building. */
	private int stoneCost;

	/** Gold generation rate. */
	private int goldRate;

	/** Wood generation rate. */
	private int woodRate;

	/** Food generation rate. */
	private int foodRate;

	/** Stone generation rate. */
	private int stoneRate;

	/** The basic building setup that puts placeholder values for
	 * each building */
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

	/** Construct a building from the given building name.
	 * 
	 * @param name type of building to make */
	public Building(String name)
	{
		this(0, 0, name);
	}

	/** Creates a building of the given building type in the
	 * specified location
	 * 
	 * @param location The location this building is put at on the
	 *            map
	 * @param buildingType The type of building created */
	public Building(Point location, String buildingType)
	{
		this((int) location.getX(), (int) location.getY(), buildingType);
	}

	/** Compare buildings by the uniqueBuildingID.
	 * 
	 * @param o object to compare to */
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

	/** Deduct damage from health.
	 * 
	 * @param amount damage output of the attacking entity. */
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

	/** Setter for x.
	 * 
	 * @param x */
	public void setX(int x)
	{
		this.xPos = x;
	}

	/** Setter for y.
	 * 
	 * @param y */
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

	/** Getter for goldRate.
	 * 
	 * @return goldRate */
	public int goldRate()
	{
		return goldRate;
	}

	/** Getter for woodRate.
	 * 
	 * @return woodRate */
	public int woodRate()
	{
		return woodRate;
	}

	/** Getter for foodRate.
	 * 
	 * @return foodRate */
	public int foodRate()
	{
		return foodRate;
	}

	/** Getter for stoneRate.
	 * 
	 * @return stoneRate */
	public int stoneRate()
	{
		return stoneRate;
	}

	/** Spawns a new unit at the specified point.
	 * 
	 * @param unit The unit to spawn in
	 * @param p The location the unit will be placed
	 * @return True if building can spawn in a unit, false
	 *         otherwise */

	public Boolean spawn(Unit unit, Point p)
	{
		if (name.equals("Spawner"))
		{
			// double x = xPos + width / 2;
			// double y = yPos + height + 10;

			unit.setPosition(p.getX(), p.getY());
			return true;
		}
		return false;
	}

	/** Getter for damage.
	 * 
	 * @return damage */
	public int getDamage()
	{
		return damage;
	}

	/** Getter for range.
	 * 
	 * @return range */
	public int getRange()
	{
		return range;
	}

	public String toString()
	{
		return name + ": " + uniqueBuildingID;
	}
}
