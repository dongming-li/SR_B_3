package com.trogdadr2tr.resources;

import java.io.Serializable;
import java.util.ArrayList;

/** Store all map data and perform any functions related to the
 * map.
 * 
 * @author Colt Rogness */
public class GameMap implements Serializable
{

	/** GameMap MK 1 */
	private static final long serialVersionUID = 1L;

	/** Width of the map. */
	private int width;

	/** Height of the map. */
	private int height;

	/** Player starting x coordinate. */
	public double startingX;

	/** Player starting y coordinate. */
	public double startingY;

	/** List of all the terrain on the map. */
	public ArrayList<Terrain> terrain;

	/** List of all the buildings on the map. */
	public ArrayList<Building> buildings;

	/** List of all units on the map. */
	public ArrayList<Unit> units;

	/** Build a default map. */
	public GameMap()
	{
		width = 1000;
		height = 1000;
		startingX = 500;
		startingY = 500;
		terrain = new ArrayList<Terrain>();
		buildings = new ArrayList<Building>();
		units = new ArrayList<Unit>();
	}

	/** Add a building to the map.
	 * 
	 * @param b building to add */
	public void addBuilding(Building b)
	{
		buildings.add(b);
	}

	/** Add a unit to the map.
	 * 
	 * @param u unit to add */
	public void addUnit(Unit u)
	{
		units.add(u);
	}

	/** Getter for map width.
	 * 
	 * @return width */
	public int getWidth()
	{
		return width;
	}

	/** Getter for map height.
	 * 
	 * @return height */
	public int getHeight()
	{
		return height;
	}

	/** Check that a given building does not overlap with any
	 * buildings already on the map.
	 * 
	 * @param building building to check
	 * @return whether building is placable or not */
	public boolean checkBuildingOverlap(Building building)
	{
		int maxX = building.getX() + building.getWidth() / 2;
		int minX = building.getX() - building.getWidth() / 2;
		int maxY = building.getY() + building.getHeight() / 2;
		int minY = building.getY() - building.getWidth() / 2;
		for (int i = 0; i < buildings.size(); i++)
		{
			Building temp = buildings.get(i);
			int tempMaxX = temp.getX() + temp.getWidth() / 2;
			int tempMinX = temp.getX() - temp.getWidth() / 2;
			int tempMaxY = temp.getY() + temp.getHeight() / 2;
			int tempMinY = temp.getY() - temp.getHeight() / 2;
			if ((tempMaxX < maxX && tempMaxX > minX)
					|| (tempMinX < maxX && tempMaxX > minX))
			{
				if ((tempMaxY < maxY && tempMaxY > minY)
						|| (tempMinY < maxY && tempMinY > minY))
				{
					return false;
				}
			}
		}
		for (int i = 0; i < units.size(); i++)
		{
			Unit temp = units.get(i);
			double tempMaxX = temp.getX() + temp.getWidth() / 2;
			double tempMinX = temp.getX() - temp.getWidth() / 2;
			double tempMaxY = temp.getY() + temp.getHeight() / 2;
			double tempMinY = temp.getY() - temp.getHeight() / 2;
			if ((tempMaxX < maxX && tempMaxX > minX)
					|| (tempMinX < maxX && tempMaxX > minX))
			{
				if ((tempMaxY < maxY && tempMaxY > minY)
						|| (tempMinY < maxY && tempMinY > minY))
				{
					return false;
				}
			}
		}
		return true;
	}

	/** Check that a given unit does not overlap with any units
	 * already on the map.
	 * 
	 * @param unit unit to check
	 * @return whether unit is placeable or not */
	public boolean checkUnitOverlap(Unit unit)
	{
		double maxX = unit.getX() + unit.getWidth() / 2;
		double minX = unit.getX() - unit.getWidth() / 2;
		double maxY = unit.getY() + unit.getHeight() / 2;
		double minY = unit.getY() - unit.getWidth() / 2;
		for (int i = 0; i < buildings.size(); i++)
		{
			Building temp = buildings.get(i);
			int tempMaxX = temp.getX() + temp.getWidth() / 2;
			int tempMinX = temp.getX() - temp.getWidth() / 2;
			int tempMaxY = temp.getY() + temp.getHeight() / 2;
			int tempMinY = temp.getY() - temp.getHeight() / 2;
			if ((tempMaxX < maxX && tempMaxX > minX)
					|| (tempMinX < maxX && tempMaxX > minX))
			{
				if ((tempMaxY < maxY && tempMaxY > minY)
						|| (tempMinY < maxY && tempMinY > minY))
				{
					return false;
				}
			}
		}
		for (int i = 0; i < units.size(); i++)
		{
			Unit temp = units.get(i);
			double tempMaxX = temp.getX() + temp.getWidth() / 2;
			double tempMinX = temp.getX() - temp.getWidth() / 2;
			double tempMaxY = temp.getY() + temp.getHeight() / 2;
			double tempMinY = temp.getY() - temp.getHeight() / 2;
			if ((tempMaxX < maxX && tempMaxX > minX)
					|| (tempMinX < maxX && tempMaxX > minX))
			{
				if ((tempMaxY < maxY && tempMaxY > minY)
						|| (tempMinY < maxY && tempMinY > minY))
				{
					return false;
				}
			}
		}
		return true;
	}
}
