package com.trogdadr2tr.resources;

import java.io.Serializable;

/**
 * Created by Danny on 11/4/2017.
 */

public class Terrain implements Serializable
{
    /** Terrain MK I */
    private static final long serialVersionUID = 1L;

    /** This is the x position of the terrain */
    private double xPos;

    /** This is the y position of the terrain */
    private double yPos;

    /** This is the width of the terrain */
    private double width;

    /** This is the height of the terrain */
    private double height;

    /** This is the name of the terrain */
    private String name;

    /**
     * This creates a new terrain object with the following parameters
     * @param x
     * 		The x position of the terrain
     * @param y
     * 		The y position of the terrain
     * @param w
     * 		The width of the terrain
     * @param h
     * 		The height of the terrain
     * @param name
     * 		The name of the terrain
     */
    public Terrain(double x, double y, double w, double h, String name)
    {
        xPos = x;
        yPos = y;
        width = w;
        height = h;
        this.name = name;
    }

    /**
     * Returns the name of the terrain
     * @return
     * 		What the terrain is called
     */
    public String getName()
    {
        return name;
    }

    /**
     * Returns the width of the terrain
     * @return
     * 		The width of the terrain
     */
    public double getWidth()
    {
        return width;
    }

    /**
     * Returns the height of the terrain
     * @return
     * 		The height of the terrain
     */
    public double getHeight()
    {
        return height;
    }

    /**
     * returns the x position of the terrain
     * @return
     * 		The x position of the terrain
     */
    public double getX()
    {
        return xPos;
    }

    /**
     * Returns the y position of the terrain
     * @return
     * 		The y position of the terrain
     */
    public double getY()
    {
        return yPos;
    }

    /**
     * Sends the string of the input from the array
     * @return string of the input from the array
     */
    public String toString()
    {
        return name + ", (" + xPos + ", " + yPos + "),  Width/Height:" + width + "/" + height + "\n";
    }
}
