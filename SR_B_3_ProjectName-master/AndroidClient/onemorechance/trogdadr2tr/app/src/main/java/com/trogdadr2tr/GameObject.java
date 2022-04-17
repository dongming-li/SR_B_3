package com.trogdadr2tr;

/**
 * Created by Danny on 9/24/2017.
 */
import android.graphics.Bitmap;

public abstract class GameObject
{
    protected Bitmap image;
    protected final int rowCount;
    protected final int colCount;
    protected final int WIDTH;
    protected final int HEIGHT;
    protected final int width;
    protected final int height;
    protected int x;
    protected int y;

    /**
     * Creates the image that will be placed on the Bitmap
     * @param image The image received from the caller
     * @param rowCount The row count received from the caller
     * @param colCount The column count received from the caller
     * @param x The value for X received from the caller
     * @param y The value for Y received from the caller
     */
    public GameObject(Bitmap image, int rowCount, int colCount, int x, int y, Boolean isBackground)
    {
        this.image = image;
        this.rowCount= rowCount;
        this.colCount= colCount;

        this.x= x;
        this.y= y;

        this.WIDTH = image.getWidth();
        this.HEIGHT = image.getHeight();

        this.width = this.WIDTH/ colCount;
        this.height= this.HEIGHT/ rowCount;
    }

    /**
     * Creates a subimage at a certain location on the map
     * @param row The row position received from the caller
     * @param col The column position received from the caller
     * @return
     */
    protected Bitmap createSubImageAt(int row, int col)
    {
        // createBitmap(bitmap, x, y, width, height).
        Bitmap subImage = Bitmap.createBitmap(image, col* width, row* height ,width,height);
        return subImage;
    }

    /**
     * Returns X
     * @return x
     */
    public int getX()
    {
        return this.x;
    }

    /**
     * Returns Y
     * @return y
     */
    public int getY()
    {
        return this.y;
    }

    /**
     * Returns the height of the object
     * @return height
     */
    public int getHeight()
    {
        return height;
    }

    /**
     * Returns the width of the object
     * @return width
     */
    public int getWidth()
    {
        return width;
    }

}