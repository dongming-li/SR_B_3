package com.trogdadr2tr;

/**
 * Created by Danny on 11/1/2017.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.trogdadr2tr.resources.GameMap;

import java.util.ArrayList;
import java.util.List;

public class GameSurface extends SurfaceView implements SurfaceHolder.Callback
{
    private GameThread gameThread;
    private final List<Adventurer> adventurerList = new ArrayList<Adventurer>();
    private final List<GameStuff> stuffy = new ArrayList<GameStuff>();

    /**
     * Sets the screen and makes it not Focused on meaning you can't click on it
     * @param context The context received from the caller
     */
    public GameSurface(Context context)
    {
        super(context);
        // Make Game Surface focusable so it can handle events..
        this.setFocusable(false);
        // Set callback
        this.getHolder().addCallback(this);
    }

    /**
     * Updates where the Adventurer is by calling the function in the Adventurer Class
     */
    public void update()
    {
        for(GameStuff yeet: stuffy)
        {
            yeet.update();
        }
        for(Adventurer adventurer: adventurerList)
        {
            adventurer.update();
        }
    }

    /**
     * When you click on the screen you are able to update and make your characters go in that direction
     * @param event The event/place where the screen was clicked
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        if (event.getAction() == MotionEvent.ACTION_DOWN)
        {
            int x = (int)event.getX();
            int y = (int)event.getY();

            for(Adventurer adventurer: adventurerList)
            {
                int movingVectorX =x - adventurer.getX() ;
                int movingVectorY =y - adventurer.getY() ;
                adventurer.setMovingVector(movingVectorX, movingVectorY);
            }
            return true;
        }
        return false;
    }

    /**
     * Draws the Canvas along with where the character is at
     * @param canvas The Canvas to be drawn on
     */
    @Override
    public void draw(Canvas canvas)
    {
        super.draw(canvas);

        for(GameStuff yeet: stuffy)
        {
            yeet.draw(canvas);
        }
        for(Adventurer adventurer: adventurerList)
        {
            adventurer.draw(canvas);
        }
    }

    /**
     * Implements method of SurfaceHolder.Callback
     * @param holder The holder received from the caller
     */
    @Override
    public void surfaceCreated(SurfaceHolder holder)
    {
        Bitmap terrainBitmap = BitmapFactory.decodeResource(this.getResources(),R.drawable.yeet);
        GameStuff reeeeeeeeee = new GameStuff(this,terrainBitmap,0,0, true);

        Bitmap adventurerBitmap1 = BitmapFactory.decodeResource(this.getResources(),R.drawable.wizard);
        Adventurer adventurer1 = new Adventurer(this,adventurerBitmap1,100,50);

        Bitmap adventurerBitmap2 = BitmapFactory.decodeResource(this.getResources(),R.drawable.knight);
        Adventurer adventurer2 = new Adventurer(this,adventurerBitmap2,300,150);

        Bitmap adventurerBitmap3 = BitmapFactory.decodeResource(this.getResources(),R.drawable.archer);
        Adventurer adventurer3 = new Adventurer(this,adventurerBitmap3,150,150);

        Bitmap buildingTest = BitmapFactory.decodeResource(this.getResources(),R.drawable.square);
        GameStuff firstSquare = new GameStuff(this,buildingTest,200,200,false);

        Bitmap enemyTest = BitmapFactory.decodeResource(this.getResources(),R.drawable.reeeeee);
        GameStuff firstCircle = new GameStuff(this,enemyTest,300,200,false);

        if(firstCircle.getY() == adventurer1.getY() && firstCircle.getX() == adventurer1.getX())
        {
            this.stuffy.remove(firstCircle);
        }

        this.adventurerList.add(adventurer1);
        this.adventurerList.add(adventurer2);
        this.adventurerList.add(adventurer3);
        this.stuffy.add(reeeeeeeeee);
        this.stuffy.add(firstSquare);
        this.stuffy.add(firstCircle);

        this.gameThread = new GameThread(this,holder);
        this.gameThread.setRunning(true);
        this.gameThread.start();
    }

    /**
     * Implements method of SurfaceHolder.Callback
     * @param holder The holder received from the caller
     * @param format The format received from the caller
     * @param width The value of width received from the caller
     * @param height The value of height received from the caller
     */
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
    {

    }

    /**
     * Implements method of SurfaceHolder.Callback
     * @param holder The value of holder received from the caller
     */
    @Override
    public void surfaceDestroyed(SurfaceHolder holder)
    {
        boolean retry = true;
        while(retry)
        {
            try
            {
                this.gameThread.setRunning(false);

                // Parent thread must wait until the end of GameThread.
                this.gameThread.join();
            }
            catch(InterruptedException e)
            {
                e.printStackTrace();
            }
            retry= true;
        }
    }
}