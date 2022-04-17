package com.trogdadr2tr.resources;

/**
 * Created by Danny on 12/4/2017.
 */
public class Point
{

    /** The X- position of the map */
    private double x;

    /** The Y- position of the map */
    private double y;

    /** Without parameters it assumes you are at position  0,0 */
    public Point()
    {
        this(0, 0);
    }

    /**
     * Constructor that sends the values to the next constructor as doubles from the data given
     * @param x The x-value received from the caller
     * @param y The y-value received from the caller
     */
    public Point(int x, int y)
    {
        this((double) x, (double) y);
    }

    /**
     * Takes in the values for x and y as doubles and sets the x and y to those values
     * @param x The x-value received from the caller
     * @param y The y-value received from the caller
     */
    public Point(double x, double y)
    {
        this.x = x;
        this.y = y;
    }

    /**
     * Returns the x position
     * @return the x position
     */
    public double getX()
    {
        return x;
    }

    /**
     * Retuens the y position
     * @return the y position
     */
    public double getY()
    {
        return y;
    }

    /**
     * Sets the x value from the data given
     * @param x The x-value received from the caller
     */
    public void setX(double x)
    {
        this.x = x;
    }

    /**
     * Sets the y-value from the data given
     * @param y The y-value received from the caller
     */
    public void setY(double y)
    {
        this.y = y;
    }
}