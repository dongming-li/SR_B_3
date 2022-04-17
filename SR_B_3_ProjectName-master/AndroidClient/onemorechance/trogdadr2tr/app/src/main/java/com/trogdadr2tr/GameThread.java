package com.trogdadr2tr;

/**
 * Created by Danny on 11/1/2017.
 */
import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class GameThread extends Thread
{
    private boolean running;
    private GameSurface gameSurface;
    private SurfaceHolder surfaceHolder;

    /**
     * Constructor for the GameThread
     * @param gameSurface The GameSurface received from the caller
     * @param surfaceHolder The SurfaceHolder received from the caller
     */
    public GameThread(GameSurface gameSurface, SurfaceHolder surfaceHolder)
    {
        this.gameSurface= gameSurface;
        this.surfaceHolder= surfaceHolder;
    }

    /**
     * Implementation of Thread. Redraws the screen for the game every once in awhile
     */
    @Override
    public void run()
    {
        long startTime = System.nanoTime();

        while(running)
        {
            Canvas canvas= null;
            try
            {
                // Get Canvas from Holder and lock it.
                canvas = this.surfaceHolder.lockCanvas();

                // Synchronized
                synchronized (canvas)
                {
                    this.gameSurface.update();
                    this.gameSurface.draw(canvas);
                }
            }
            catch(Exception e)
            {
                // Do nothing.
            }
            finally
            {
                if(canvas!= null)
                {
                    // Unlock Canvas.
                    this.surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
            long now = System.nanoTime() ;
            // Interval to redraw game
            // (Change nanoseconds to milliseconds)
            long waitTime = (now - startTime)/1000000;
            if(waitTime < 10)
            {
                waitTime= 10; // Millisecond.
            }
            System.out.print(" Wait Time="+ waitTime);

            try
            {
                // Sleep.
                this.sleep(waitTime);
            } catch(InterruptedException e)
            {

            }
            startTime = System.nanoTime();
            System.out.print(".");
        }
    }

    /**
     * Checks to see if it is running or not
     * @param running Is it running?
     */
    public void setRunning(boolean running)
    {
        this.running = running;
    }
}