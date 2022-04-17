package com.example.r0xas.trogdadr2tr;

/**
 * Created by Danny on 9/24/2017.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameSurface extends SurfaceView implements SurfaceHolder.Callback
{
    private GameThread gameThread;
    private Adventurer testadventurer2; //Will be characters

    public GameSurface(Context context)
    {
        super(context);
        // Make Game Surface focusable so it can handle events. .
        this.setFocusable(true);
        // Set callback
        this.getHolder().addCallback(this);
    }

    public void update()
    {
        this.testadventurer2.update();
    }

    @Override
    public void draw(Canvas canvas)
    {
        super.draw(canvas);

        this.testadventurer2.draw(canvas);
    }

    // Implements method of SurfaceHolder.Callback
    @Override
    public void surfaceCreated(SurfaceHolder holder)
    {
        Bitmap testadventurerBitmap1 = BitmapFactory.decodeResource(this.getResources(),R.drawable.testadventurer2);
        this.testadventurer2 = new Adventurer(this,testadventurerBitmap1,100,50);

        this.gameThread = new GameThread(this,holder);
        this.gameThread.setRunning(true);
        this.gameThread.start();
    }

    // Implements method of SurfaceHolder.Callback
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
    {

    }

    // Implements method of SurfaceHolder.Callback
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
            }catch(InterruptedException e)
            {
                e.printStackTrace();
            }
            retry= true;
        }
    }

}