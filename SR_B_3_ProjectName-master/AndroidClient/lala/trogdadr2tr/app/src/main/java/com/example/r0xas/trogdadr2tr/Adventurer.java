package com.example.r0xas.trogdadr2tr;

/**
 * Created by Danny on 9/24/2017.
 */

//Adventurer
import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Adventurer extends GameObject
{
    private static final int ROW_TOP_TO_BOTTOM = 0;
    private static final int ROW_RIGHT_TO_LEFT = 1;
    private static final int ROW_LEFT_TO_RIGHT = 2;
    private static final int ROW_BOTTOM_TO_TOP = 3;

    // Row index of Image are being used.
    private int row = ROW_LEFT_TO_RIGHT;

    private int column;

    private Bitmap[] leftToRight;
    private Bitmap[] rightToLeft;
    private Bitmap[] topToBottom;
    private Bitmap[] bottomToTop;

    // Velocity of game character (pixel/millisecond)
    public static final float VELOCITY = 0.1f;

    private int moveinX = 10;
    private int moveinY = 5;

    private long lastDrawNanoTime =-1;

    private GameSurface gameSurface;

    public Adventurer(GameSurface gameSurface, Bitmap image, int x, int y)
    {
        super(image, 4, 3, x, y);

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

    public Bitmap getCurrentMoveBitmap()
    {
        Bitmap[] bitmaps = this.getMoveBitmaps();
        return bitmaps[this.column];
    }

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
