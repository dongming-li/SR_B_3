package com.example.r0xas.serverconnection.game;
/**
 * Created by Danny on 10/13/2017.
 */

public class Building
{
    /**
     * This is the x position of the building
     */
    private double xPos;

    /**
     * This is the y position of the building
     */
    private double yPos;

    /**
     * This is the width of the building
     */
    private double width;

    /**
     * This is the height of the building
     */
    private double height;

    /**
     * This is the hit points of the building
     */
    private double health;

    /**
     * This is the current health of the building
     */
    private double currentHealth;

    /**
     * This is the name of the building
     */
    private String name;

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
    public Building(double x, double y, double w, double h, double hp, String name)
    {
        xPos = x;
        yPos = y;
        width = w;
        height = h;
        health = hp;
        currentHealth = health;
        this.name = name;
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
    public double getHealth()
    {
        return health;
    }

    /**
     * Returns the current health of the building
     * @return
     * 		The current health of the building
     */
    public double getCurrentHealth()
    {
        return currentHealth;
    }

    /**
     * Returns the width of the building
     * @return
     * 		The width of the building
     */
    public double getWidth()
    {
        return width;
    }

    /**
     * Returns the height of the building
     * @return
     * 		The height of the building
     */
    public double getHeight()
    {
        return height;
    }

    /**
     * returns the x position of the building
     * @return
     * 		The x position of the building
     */
    public double getX()
    {
        return xPos;
    }

    /**
     * Returns the y position of the building
     * @return
     * 		The y position of the building
     */
    public double getY()
    {
        return yPos;
    }

    public String toString()
    {
        return name + ", (" + xPos + ", " + yPos + "),  HP:" + currentHealth + "/" + health;
    }
}
