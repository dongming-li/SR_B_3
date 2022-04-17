package com.trogdadr2tr;

/**
 * Created by Danny on 11/1/2017.
 */

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;

import static android.content.ContentValues.TAG;

public class Adventurer extends GameObject
{
    private static final int ROW_TOP_TO_BOTTOM = 0;
    private static final int ROW_RIGHT_TO_LEFT = 1;
    private static final int ROW_LEFT_TO_RIGHT = 2;
    private static final int ROW_BOTTOM_TO_TOP = 3;

    // Row index of Image are being used.
    private int row = ROW_LEFT_TO_RIGHT;

    /** Column position */
    private int column;

    /** Corners of the map */
    private Bitmap[] leftToRight;
    private Bitmap[] rightToLeft;
    private Bitmap[] topToBottom;
    private Bitmap[] bottomToTop;

    // Velocity of game character (pixel/millisecond)
    public static final float VELOCITY = 0.1f;

    private int moveinX = 0;
    private int moveinY = 0;

    private long lastDrawNanoTime =-1;

    /** Brings in game surface */
    private GameSurface gameSurface;

    /**
     * Creates the Adventurer to walk on the screen
     * @param gameSurface The GameSurface received from the caller
     * @param image The image received from the caller
     * @param x The value for X received from the caller
     * @param y The value for Y received from the caller
     */
    public Adventurer(GameSurface gameSurface, Bitmap image, int x, int y)
    {
        super(image, 4, 3, x, y, false);

        this.gameSurface= gameSurface;

        this.topToBottom = new Bitmap[colCount]; // 3
        this.rightToLeft = new Bitmap[colCount]; // 3
        this.leftToRight = new Bitmap[colCount]; // 3
        this.bottomToTop = new Bitmap[colCount]; // 3


        for(int col = 0; col< this.colCount; col++)
        {
            this.topToBottom[col] = this.createSubImageAt(ROW_TOP_TO_BOTTOM, col);
            this.rightToLeft[col]  = this.createSubImageAt(ROW_RIGHT_TO_LEFT, col);
            this.leftToRight[col] = this.createSubImageAt(ROW_LEFT_TO_RIGHT, col);
            this.bottomToTop[col]  = this.createSubImageAt(ROW_BOTTOM_TO_TOP, col);
        }
    }

    /**
     * Returns based on the row
     * @return Which of the rows they are on
     */
    public Bitmap[] getMoveBitmaps()
    {
        switch (row)
        {
            case ROW_BOTTOM_TO_TOP:
                return  this.bottomToTop;
            case ROW_LEFT_TO_RIGHT:
                return this.leftToRight;
            case ROW_RIGHT_TO_LEFT:
                return this.rightToLeft;
            case ROW_TOP_TO_BOTTOM:
                return this.topToBottom;
            default:
                return null;
        }
    }

    /**
     * gets the current Bitmap
     * @return Bitmap
     */
    public Bitmap getCurrentMoveBitmap()
    {
        Bitmap[] bitmaps = this.getMoveBitmaps();
        return bitmaps[this.column];
    }

    /**
     * Updates your position
     */
    public void update()
    {
        this.column++;
        if(column >= this.colCount)
        {
            this.column =0;
        }
        // Current time in nanoseconds
        long now = System.nanoTime();

        // Never once did draw.
        if(lastDrawNanoTime==-1)
        {
            lastDrawNanoTime= now;
        }
        // Change nanoseconds to milliseconds (1 nanosecond = 1000000 milliseconds).
        int deltaTime = (int) ((now - lastDrawNanoTime)/ 1000000 );

        // Distance moves
        float distance = VELOCITY * deltaTime;

        double movingVectorLength = Math.sqrt(moveinX * moveinX + moveinY * moveinY);

        // Calculate the new position of the game character.
        this.x = x +  (int)(distance* moveinX / movingVectorLength);
        this.y = y +  (int)(distance* moveinY / movingVectorLength);

        // When the game's character touches the edge of the screen, then change direction
        if(this.x < 0 )
        {
            this.x = 0;
            this.moveinX = - this.moveinX;
        }
        else if(this.x > this.gameSurface.getWidth() -width)
        {
            this.x= this.gameSurface.getWidth()-width;
            this.moveinX = - this.moveinX;
        }

        if(this.y < 0 )
        {
            this.y = 0;
            this.moveinY = - this.moveinY;
        }
        else if(this.y > this.gameSurface.getHeight()- height)
        {
            this.y= this.gameSurface.getHeight()- height;
            this.moveinY = - this.moveinY;
        }

        // row
        if( moveinX > 0 )
        {
            if(moveinY > 0 && Math.abs(moveinX) < Math.abs(moveinY))
            {
                this.row = ROW_TOP_TO_BOTTOM;
            }
            else if(moveinY < 0 && Math.abs(moveinX) < Math.abs(moveinY))
            {
                this.row = ROW_BOTTOM_TO_TOP;
            }
            else
            {
                this.row = ROW_LEFT_TO_RIGHT;
            }
        }
        else
            {
            if(moveinY > 0 && Math.abs(moveinX) < Math.abs(moveinY))
            {
                this.row = ROW_TOP_TO_BOTTOM;
            }
            else if(moveinY < 0 && Math.abs(moveinX) < Math.abs(moveinY))
            {
                this.row = ROW_BOTTOM_TO_TOP;
            }
            else
            {
                this.row = ROW_RIGHT_TO_LEFT;
            }
        }
    }

    /**
     * Draws the bitmap to the canvas
     * @param canvas The drawing space
     */
    public void draw(Canvas canvas)
    {
        Bitmap bitmap = this.getCurrentMoveBitmap();
        canvas.drawBitmap(bitmap,x, y, null);
        // Last draw time.
        this.lastDrawNanoTime= System.nanoTime();
    }

    //When Joystick is implemented
    public void setMovingVector(int movingVectorX, int movingVectorY)
    {
        this.moveinX = movingVectorX;
        this.moveinY = movingVectorY;
    }

}
