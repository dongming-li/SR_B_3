package com.trogdadr2tr.resources;

import java.io.Serializable;

/** RTS player's little minions.
 * 
 * @author Connor Brown, Colt Rogness */
public class Unit implements Serializable
{

	/** Unit MK I */
	private static final long serialVersionUID = 1L;

	/** A unique identifier for use in updating remote units. */
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

	/** Food cost to make this unit. */
	private double foodCost;

	/** Gold cost to make this unit. */
	private double goldCost;

	/** Wood cost to make this unit. */
	private double woodCost;

	/** Stone cost to make this unit. */
	private double stoneCost;

	/** Default constructor. */
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

	/** Construct a unit from the given unit type.
	 * 
	 * @param n unit type */
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

	/** Compare uniqueUnitID to another unit.
	 * 
	 * @param o unit to compare to. */
	@Override
	public boolean equals(Object o)
	{
		if (o instanceof Unit)
		{
			return uniqueUnitID == ((Unit) o).uniqueUnitID;
		}
		return false;
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

	/** Getter for goldCost.
	 * 
	 * @return goldCost */
	public double getGoldCost()
	{
		return goldCost;
	}

	/** Getter for foodCost.
	 * 
	 * @return foodCost */
	public double getFoodCost()
	{
		return foodCost;
	}

	/** Getter for woodCost.
	 * 
	 * @return woodCost */
	public double getWoodCost()
	{
		return woodCost;
	}

	/** Getter for stoneCost.
	 * 
	 * @return stoneCost */
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

	/** Returns the name and ID of this unit
	 * 
	 * @return The name and ID of this unit in the form 'Name:
	 *         UnitID' */
	public String toString()
	{
		return name + ": " + uniqueUnitID;
	}
}
