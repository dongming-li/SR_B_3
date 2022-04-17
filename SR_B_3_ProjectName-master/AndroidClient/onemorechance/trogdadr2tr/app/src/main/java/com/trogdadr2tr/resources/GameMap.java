package com.trogdadr2tr.resources;

/**
 * Created by Danny on 12/1/2017.
 */
import java.io.Serializable;
import java.util.ArrayList;

/** Store all map data and perform any functions related to the
 * map.
 *
 */
public class GameMap implements Serializable
{

    /** GameMap MK 1 */
    private static final long serialVersionUID = 1L;

    /** The width of the map */
    private int width;

    /** The height of the map */
    private int height;

    /** Starting x position */
    public double startingX;

    /** Starting y position */
    public double startingY;

    /** An array list of terrain */
    public ArrayList<Terrain> terrain;

    /** An array list of buildings */
    public ArrayList<Building> buildings;

    /** An array list of units */
    public ArrayList<Unit> units;

    /**
     * Basic constructor for the game map
     */
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

    /**
     * Adds a building to the game map
     * @param b the building to be placed that was received from the caller
     */
    public void addBuilding(Building b)
    {
        buildings.add(b);
    }

    /**
     * Adds a unit to the game map
     * @param u the unit to be placed that was received from the caller
     */
    public void addUnit(Unit u)
    {
        units.add(u);
    }

    /**
     * Returns the width of the object
     * @return the width of the object
     */
    public int getWidth()
    {
        return width;
    }

    /**
     * Returns the height of the object
     * @return the height of the object
     */
    public int getHeight()
    {
        return height;
    }

    /**
     * Checks to see if there is any buildings that overlap
     * @param building The buildings that was received from the caller
     * @return if the buildings overlap
     */
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

    /**
     * Checks to see if there is any units that overlap
     * @param unit The unit that was received from the caller
     * @return if the units overlap
     */
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

