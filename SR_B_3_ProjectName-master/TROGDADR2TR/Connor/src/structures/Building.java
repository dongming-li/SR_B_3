package structures;

import game.RTSPlayer;
import units.Unit;

/**
 * 
 * @author Connor Brown
 *
 */
public class Building 
{
	/**
	 * This is the x position of the building
	 */
	private int xPos;
	
	/**
	 * This is the y position of the building
	 */
	private int yPos;
	
	/**
	 * This is the width of the building
	 */
	private int width;
	
	/**
	 * This is the height of the building
	 */
	private int height;
	
	/**
	 * This is the hit points of the building
	 */
	private int health;
	
	/**
	 * This is the current health of the building
	 */
	private int currentHealth;
	
	/**
	 * This is the name of the building
	 */
	private String name;
	
	/**
	 * References the player that spawns in the buildings
	 */
	private RTSPlayer player;
	private long time;
	
	private int goldCost;
	private int woodCost;
	private int foodCost;
	private int stoneCost;
	
	/**
	 * This creates a new building object with the following parameters
	 * @param x
	 * 		The x position of the building
	 * @param y
	 * 		The y position of the building
	 * @param w
	 * 		The width of the building
	 * @param h
	 * 		The height of the building
	 * @param hp
	 * 		The hit points of the building
	 * @param name
	 * 		The name of the building
	 */
	public Building(int x, int y, String name, RTSPlayer p)
	{
		int baseWidth = 100;
		int baseHeight = 100;
		int baseHP = 100;
		switch(name) {
			case "Base":
				width = baseWidth*2;
				height = baseHeight*2;
				health = baseHP*10;
				goldCost = Integer.MAX_VALUE;
				foodCost = Integer.MAX_VALUE;
				woodCost = Integer.MAX_VALUE;
				stoneCost = Integer.MAX_VALUE;
				break;
			case "Food Generator":	
				width = baseWidth;
				height = baseHeight;
				health = (int) (baseHP*1.5);
				goldCost = 2;
				foodCost = 2;
				woodCost = 2;
				stoneCost = 2;
				break;
			case "Gold Generator":
				width = baseWidth;
				height = baseHeight;
				health = (int) (baseHP*1.5);
				goldCost = 2;
				foodCost = 2;
				woodCost = 2;
				stoneCost = 2;
				break;
			case "Wood Generator":
				width = baseWidth;
				height = baseHeight;
				health = (int) (baseHP*1.5);
				goldCost = 2;
				foodCost = 2;
				woodCost = 2;
				stoneCost = 2;
				break;
			case "Stone Generator":
				width = baseWidth;
				height = baseHeight;
				health = (int) (baseHP*1.5);
				goldCost = 2;
				foodCost = 2;
				woodCost = 2;
				stoneCost = 2;
				break;
			case "Garrison":
				width = baseWidth*2;
				height = baseHeight*2;
				health = baseHP*5;
				goldCost = 10;
				foodCost = 5;
				woodCost = 10;
				stoneCost = 10;
				break;
			case "Long Range Defense":
				width = (int) (baseWidth*1.5);
				height = (int) (baseHeight*1.5);
				health = (int) (baseHP*3.5);
				goldCost = 20;
				foodCost = 5;
				woodCost = 15;
				stoneCost = 10;
				break;
			case "Short Range Defense":
				width = baseWidth*2;
				height = baseHeight*2;
				health = baseHP*6;
				goldCost = 10;
				foodCost = 5;
				woodCost = 20;
				stoneCost = 20;
				break;
			case "Spawner":
				width = baseWidth*2;
				height = baseHeight*2;
				health = baseHP*6;
				goldCost = 20;
				foodCost = 10;
				woodCost = 15;
				stoneCost = 20;
				break;
			case "Wall Vertical":
				width = (int) (baseWidth*.5);
				height = baseHeight*3;
				health = baseHP*2;
				goldCost = 5;
				foodCost = 2;
				woodCost = 5;
				stoneCost = 25;
				break;
			case "Wall Horizontal":
				width = baseWidth*3;
				height = (int) (baseHeight*.5);
				health = baseHP*2;
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
		time = System.currentTimeMillis();
		player = p;
	}
	
	/**
	 * Checks for a single unit Object within the indicated range and returns that object if it is there, otherwise returns null
	 * @param range
	 * 		How far away an object can be to be detected
	 * @return
	 * 		A unit Object if there is one within range, else null
	 */
	public Object enemyInRange(int range)
	{
		for(int i = 0; i < player.map.units.size(); i++)
		{
			int x = player.map.units.get(i).getX();
			int y = player.map.units.get(i).getY();
			if(Math.abs(getX() - x) < range && Math.abs(getY() - y) < range)
			{
				return player.map.units.get(i);
			}
		}
		
		return null;
	}
	
	/**
	 * This is the attack function for the Long Range Defense
	 */
	public void LRDAttack()
	{
		if(System.currentTimeMillis() - time > 1000)
		{
			System.out.println("attacking");
			Unit enemy = (Unit) enemyInRange(400);
			if(enemy != null)
			{
				enemy.takeDamage(100);
			}
			time = System.currentTimeMillis();
		}
	}
	
	/**
	 * This is the attack function for the Short Range Defense
	 */
	public void SRDAttack()
	{
		if(System.currentTimeMillis() - time > 300)
		{
			System.out.println("attacking");
			Unit enemy = (Unit) enemyInRange(200);
			if(enemy != null)
			{
				enemy.takeDamage(40);
			}
			time = System.currentTimeMillis();
		}
	}
	
	/**
	 * Returns the cost of gold for this building
	 * @return
	 * 		The cost of gold
	 */
	public int getGoldCost()
	{
		return goldCost;
	}
	
	/**
	 * Returns the cost of wood for this building
	 * @return
	 * 		The cost of wood
	 */
	public int getWoodCost()
	{
		return woodCost;
	}
	
	/**
	 * Returns the cost of food for this building
	 * @return
	 * 		The cost of food
	 */
	public int getFoodCost()
	{
		return foodCost;
	}
	
	/**
	 * Returns the cost of stone for this building
	 * @return
	 * 		The cost of stone
	 */
	public int getStoneCost()
	{
		return stoneCost;
	}
	
	/**
	 * Returns the name of the building
	 * @return
	 * 		What the building is called
	 */
	public String getName()
	{
		return name;
	}
	
	/**
	 * Returns the health of the building
	 * @return
	 * 		The hit points of the building
	 */
	public int getHealth()
	{
		return health;
	}
	
	/**
	 * Returns the current health of the building
	 * @return
	 * 		The current health of the building
	 */
	public int getCurrentHealth()
	{
		return currentHealth;
	}
	
	/**
	 * Returns the width of the building
	 * @return
	 * 		The width of the building
	 */
	public int getWidth()
	{
		return width;
	}
	
	/**
	 * Returns the height of the building
	 * @return
	 * 		The height of the building
	 */
	public int getHeight()
	{
		return height;
	}
	
	/**
	 * returns the x position of the building
	 * @return
	 * 		The x position of the building
	 */
	public int getX()
	{
		return xPos;
	}
	
	/**
	 * Returns the y position of the building
	 * @return
	 * 		The y position of the building
	 */
	public int getY()
	{
		return yPos;
	}
	
}
