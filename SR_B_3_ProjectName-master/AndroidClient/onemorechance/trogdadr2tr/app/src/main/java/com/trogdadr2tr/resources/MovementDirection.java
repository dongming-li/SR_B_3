package com.trogdadr2tr.resources;

/**
 * Created by Danny on 12/1/2017.
 */
/** Directional movement, x and y values for movement in that
 * direction.
 *
 */
public enum MovementDirection
{
    // TODO - make sure these are the right numbers.
    UP(1, 2), DOWN(3, 2), LEFT(1, 1), RIGHT(0, 1), UP_LEFT(3, 4), UP_RIGHT(1, 4), DOWN_LEFT(5, 4), DOWN_RIGHT(7, 4),
    STATIONARY(0, 0);

    /** The x value that you are moving to*/
    public final double x;

    /** The y value that you are moving to */
    public final double y;

    /**
     * Moves the character
     * @param numerator The numerator received from the caller
     * @param denominator The denominator received from the caller
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
