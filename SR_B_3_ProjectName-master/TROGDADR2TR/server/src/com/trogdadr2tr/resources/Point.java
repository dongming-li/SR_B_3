package com.trogdadr2tr.resources;

import java.io.Serializable;

/** Because android and awt don't play nice.
 * 
 * @author Colt Rogness */
public class Point implements Serializable
{

	/** Point MK I */
	private static final long serialVersionUID = 1L;

	/** The x coordinate of this point */
	private double x;

	/** The y coordinate of this point */
	private double y;

	/** Construct a point at (0, 0). */
	public Point()
	{
		this(0, 0);
	}

	/** Construct a point at (x, y).
	 * 
	 * @param x
	 * @param y */
	public Point(int x, int y)
	{
		this((double) x, (double) y);
	}

	/** Construct a point at (x, y).
	 * 
	 * @param x
	 * @param y */
	public Point(double x, double y)
	{
		this.x = x;
		this.y = y;
	}

	/** Getter for Point.x.
	 * 
	 * @return x */
	public double getX()
	{
		return x;
	}

	/** Getter for Point.y.
	 * 
	 * @return y */
	public double getY()
	{
		return y;
	}

	/** Setter for Point.x.
	 * 
	 * @param x */
	public void setX(double x)
	{
		this.x = x;
	}

	/** Setter for Point.y.
	 * 
	 * @param y */
	public void setY(double y)
	{
		this.y = y;
	}

}
