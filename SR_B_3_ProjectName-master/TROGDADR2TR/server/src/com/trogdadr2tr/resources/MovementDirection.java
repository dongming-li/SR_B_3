package com.trogdadr2tr.resources;

/** Directional movement, x and y values for movement in that
 * direction.
 * 
 * @author Colt Rogness */
public enum MovementDirection
{
	// TODO - make sure these are the right numbers.
	UP(1, 2), DOWN(3, 2), LEFT(1, 1), RIGHT(0, 1), UP_LEFT(3, 4), UP_RIGHT(1, 4), DOWN_LEFT(5, 4), DOWN_RIGHT(7, 4),
	STATIONARY(0, 0);

	/**
	 * delta x for movement in the x direction.
	 */
	public final double x;

	/**
	 * delta y for movement in the y direction.
	 */
	public final double y;

	/**
	 * Default constructor for movement direction
	 * @param numerator don't worry about it
	 * @param denominator none of your concern
	 */
	private MovementDirection(int numerator, int denominator)
	{
		// if dividing by zero, then it is stationary.
		if (denominator == 0)
		{
			this.y = 0;
			this.x = 0;
		}
		else
		{
			this.y = Math.sin(Math.PI * numerator / denominator);
			this.x = Math.cos(Math.PI * numerator / denominator);
		}
		// System.out.println("(" + x + ", " + y + ")");
	}

}
