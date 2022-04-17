package com.trogdadr2tr;

/**
 * Created by Danny on 12/7/2017.
 */
import android.graphics.Bitmap;
import android.graphics.Canvas;

public class GameStuff extends GameObject
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

    private long lastDrawNanoTime =-1;

    /** Brings in game surface */
    private GameSurface gameSurface;

    /**
     * Creates the Building to walk on the screen
     * @param gameSurface The GameSurface received from the caller
     * @param image The image received from the caller
     * @param x The value for X received from the caller
     * @param y The value for Y received from the caller
     */
    public GameStuff(GameSurface gameSurface, Bitmap image, int x, int y, Boolean isBackground)
    {

        super(image, 4, 3, x, y, isBackground);

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

    }

    /**
     * Draws the bitmap to the canvas
     * @param canvas The drawing space
     */
    public void draw(Canvas canvas)
    {
        Bitmap bitmap = this.getCurrentMoveBitmap();
        canvas.drawBitmap(bitmap,x, y, null);
        // Last draw time
        this.lastDrawNanoTime= System.nanoTime();
    }

}
