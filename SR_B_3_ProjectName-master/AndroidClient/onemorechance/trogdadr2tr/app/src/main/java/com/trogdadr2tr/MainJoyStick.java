package com.trogdadr2tr;

/**
 * Created by Danny on 11/1/2017.
 */

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.trogdadr2tr.resources.*;
import com.trogdadr2tr.socket.Client;

import java.io.IOException;
import java.io.InterruptedIOException;

public class MainJoyStick extends Activity
{

    RelativeLayout layout_joystick;
    TextView textView5;


    Adventurer player;
    JoyStickClass js;
    //SocketConnector yus;
    //com.trogdadr2tr.resources.Point heck = new com.trogdadr2tr.resources.Point(player.getX(), player.getY()) ;
    Point heck = new Point(0,0);
    /**
     * Creates the following XML file, and initializes all of the values
     * @param savedInstanceState
     */
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        textView5 = (TextView)findViewById(R.id.textView5);
        Button Attack;

        //yus = new SocketConnector(null, null, 0, null, null);

        layout_joystick = (RelativeLayout)findViewById(R.id.layout_joystick);

        js = new JoyStickClass(getApplicationContext(), layout_joystick, R.drawable.image_button);
        js.setStickSize(150, 150);
        js.setLayoutSize(500, 500);
        js.setLayoutAlpha(150);
        js.setStickAlpha(100);
        js.setOffset(90);
        js.setMinimumDistance(50);

        layout_joystick.setOnTouchListener(new OnTouchListener()
        {
            public boolean onTouch(View arg0, final MotionEvent arg1)
            {
                js.drawStick(arg1);
                try
                {
                    Client.init();
                }
                catch (IOException e)
                {
                    // gotta catch'm all!
                }
                catch(InterruptedException e)
                {

                }
//                Runnable r = new Runnable()
//                {
//                    @Override
//                    public void run() {
                try
                {
                    if (arg1.getAction() == MotionEvent.ACTION_DOWN
                            || arg1.getAction() == MotionEvent.ACTION_MOVE)
                    {
                        int direction = js.get8Direction();
                        if (direction == JoyStickClass.STICK_UP)
                        {
                            Client.getInstance().updater.movePlayer(MovementDirection.UP, heck);
                            textView5.setText("Direction : Up");
                        }
                        else if (direction == JoyStickClass.STICK_UPRIGHT)
                        {
                            Client.getInstance().updater.movePlayer(MovementDirection.UP_RIGHT, heck);
                            textView5.setText("Direction : Up Right");
                        }
                        else if (direction == JoyStickClass.STICK_RIGHT)
                        {
                            Client.getInstance().updater.movePlayer(MovementDirection.RIGHT, heck);
                            textView5.setText("Direction : Right");
                        }
                        else if (direction == JoyStickClass.STICK_DOWNRIGHT)
                        {
                            Client.getInstance().updater.movePlayer(MovementDirection.DOWN_RIGHT, heck);
                            textView5.setText("Direction : Down Right");
                        }
                        else if (direction == JoyStickClass.STICK_DOWN)
                        {
                            Client.getInstance().updater.movePlayer(MovementDirection.DOWN, heck);
                            textView5.setText("Direction : Down");
                        }
                        else if (direction == JoyStickClass.STICK_DOWNLEFT)
                        {
                            Client.getInstance().updater.movePlayer(MovementDirection.DOWN_LEFT, heck);
                            textView5.setText("Direction : Down Left");
                        }
                        else if (direction == JoyStickClass.STICK_LEFT)
                        {
                            Client.getInstance().updater.movePlayer(MovementDirection.LEFT, heck);
                            textView5.setText("Direction : Left");
                        }
                        else if (direction == JoyStickClass.STICK_UPLEFT)
                        {
                            Client.getInstance().updater.movePlayer(MovementDirection.UP_LEFT, heck);
                            textView5.setText("Direction : Up Left");
                        }
                        else if (direction == JoyStickClass.STICK_NONE)
                        {
                            textView5.setText("Direction : Center");
                            //  yus.sendNoodles("Direction : Center");
                        }
                    }
                    else if (arg1.getAction() == MotionEvent.ACTION_UP)
                    {

                    }
                }
                catch(IllegalStateException e)
                {

                }
//                    }
//                };
//                new Thread(r).start();
                return true;
            }
        });
    }
}
