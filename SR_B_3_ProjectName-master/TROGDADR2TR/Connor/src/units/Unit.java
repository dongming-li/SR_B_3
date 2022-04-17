package units;

import game.RTSPlayer;

public class Unit
{

	/** The x position of this unit */
	private int xPos;

	/** The y position of this unit */
	private int yPos;

	/** The width of this unit */
	private int width;

	/** The height of this unit */
	private int height;

	/** The current health of this unit */
	private int health;

	/** The maximum health this unit can have */
	private int maxHealth;

	/** How fast the unit can move around the map */
	private int speed;

	/** The amount of health this unit takes away from the
	 * defending object when this unit attacks */
	private int damage;

	/** How far away this unit can deal damage */
	private int range;

	/** What this unit is called */
	private String name;

	/** How much space the unit takes up in regards to total unit
	 * capacity of RTSPlayer */
	private int cost;

	/** References the player that controls these units */
	private RTSPlayer player;

	/** Creates a new unit based on the String passed in
	 * 
	 * @param x The x position this object will be created at
	 * @param y The y position this object will be created at
	 * @param n The name of this object. This determines what type
	 *            of unit is created */
	public Unit(int x, int y, String n, RTSPlayer p)
	{
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
		xPos = x;
		yPos = y;
		name = n;
		player = p;
	}

	/** Does the special attack type of this unit, based on what
	 * type of unit it is */
	public void attack()
	{
		// does the attack based on the unit
	}

	/** Subtracts the parameter amount from the units health. If
	 * amount is more than how much health is remaining, calls
	 * die()
	 * 
	 * @param amount The amount of health this unit loses */
	public void takeDamage(int amount)
	{
		if (health - amount <= 0)
		{
			die();
		}
		else
		{
			health -= amount;
		}
	}

	/** Removes the object from the game */
	public void die()
	{
		player.playerScreen.unitCount -= this.cost;
		player.playerScreen.unitCountLabel
				.setText("Units: " + player.playerScreen.unitCount);
		try
		{
			System.out.println(getName() + " died");
			int index = player.map.units.indexOf(this);
			System.out.println(index);
			player.map.units.remove(this);
			player.unitsToMove.remove(index);
			player.unitsToMovePosition.remove(index);
		}
		catch (IndexOutOfBoundsException e)
		{
			System.out.println("This is dumb");
		}
		player.redraw();
	}

	/** Acts like moving the unit, but it sort of jumps to the
	 * indicated x and y coords
	 * 
	 * @param x The x coordinate the unit is moved to
	 * @param y The y coordinate the unit is moved to */
	public void setPosition(int x, int y)
	{
		xPos = x;
		yPos = y;
	}

	/** Returns the cost of the unit
	 * 
	 * @return The cost of the unit */
	public int getCost()
	{
		return cost;
	}

	/** Returns the maxHealth variable
	 * 
	 * @return The maximum amount of health this unit can have */
	public int getMaxHealth()
	{
		return maxHealth;
	}

	/** Returns the xPos variable
	 * 
	 * @return The x position of this unit */
	public int getX()
	{
		return xPos;
	}

	/** Returns the yPos variable
	 * 
	 * @return The y position of this unit */
	public int getY()
	{
		return yPos;
	}

	/** Returns the width variable
	 * 
	 * @return The width of this unit */
	public int getWidth()
	{
		return width;
	}

	/** Returns the height variable
	 * 
	 * @return The height of this unit */
	public int getHeight()
	{
		return height;
	}

	/** Returns the health variable
	 * 
	 * @return The current health of this unit */
	public int getHealth()
	{
		return health;
	}

	/** Returns the speed variable
	 * 
	 * @return How fast this unit can move around the map */
	public int getSpeed()
	{
		return speed;
	}

	/** Returns the damage variable
	 * 
	 * @return The amount of damage this unit can inflict on other
	 *         objects */
	public int getDamage()
	{
		return damage;
	}

	/** Returns the range variable
	 * 
	 * @return The maximum distance this unit can attack */
	public int getRange()
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
