package com.trogdadr2tr;

/**
 * Created by Danny on 11/1/2017.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;

public class JoyStickClass
{

    public static final int STICK_NONE = 0;
    public static final int STICK_UP = 1;
    public static final int STICK_UPRIGHT = 2;
    public static final int STICK_RIGHT = 3;
    public static final int STICK_DOWNRIGHT = 4;
    public static final int STICK_DOWN = 5;
    public static final int STICK_DOWNLEFT = 6;
    public static final int STICK_LEFT = 7;
    public static final int STICK_UPLEFT = 8;

    private int STICK_ALPHA = 200;
    private int LAYOUT_ALPHA = 200;
    private int OFFSET = 0;

    private Context mContext;
    private ViewGroup mLayout;
    private LayoutParams params;
    private int stick_width, stick_height;

    private int position_x = 0, position_y = 0, min_distance = 0;
    private float distance = 0, angle = 0;

    private DrawCanvas draw;
    private Paint paint;
    private Bitmap stick;

    private boolean touch_state = false;

    /**
     * Creates the image of the Joystick to be used
     * @param context The context received from the caller
     * @param layout The layout received from the caller
     * @param stick_res_id The Stick received from the caller
     */
    public JoyStickClass (Context context, ViewGroup layout, int stick_res_id)
    {
        mContext = context;

        stick = BitmapFactory.decodeResource(mContext.getResources(), stick_res_id);

        stick_width = stick.getWidth();
        stick_height = stick.getHeight();

        draw = new DrawCanvas(mContext);
        paint = new Paint();
        mLayout = layout;
        params = mLayout.getLayoutParams();
    }

    /**
     * Draws the Red Circle that goes in the middle
     * @param arg1 The value received from the caller
     */
    public void drawStick(MotionEvent arg1)
    {
        position_x = (int) (arg1.getX() - (params.width / 2));
        position_y = (int) (arg1.getY() - (params.height / 2));
        distance = (float) Math.sqrt(Math.pow(position_x, 2) + Math.pow(position_y, 2));
        angle = (float) cal_angle(position_x, position_y);

        if(arg1.getAction() == MotionEvent.ACTION_DOWN)
        {
            if(distance <= (params.width / 2) - OFFSET)
            {
                draw.position(arg1.getX(), arg1.getY());
                draw();
                touch_state = true;
            }
        }
        else if(arg1.getAction() == MotionEvent.ACTION_MOVE && touch_state)
        {
            if(distance <= (params.width / 2) - OFFSET)
            {
                draw.position(arg1.getX(), arg1.getY());
                draw();
            } else if(distance > (params.width / 2) - OFFSET)
            {
                float x = (float) (Math.cos(Math.toRadians(cal_angle(position_x, position_y)))
                        * ((params.width / 2) - OFFSET));
                float y = (float) (Math.sin(Math.toRadians(cal_angle(position_x, position_y)))
                        * ((params.height / 2) - OFFSET));
                x += (params.width / 2);
                y += (params.height / 2);
                draw.position(x, y);
                draw();
            }
            else
            {
                mLayout.removeView(draw);
            }
        }
        else if(arg1.getAction() == MotionEvent.ACTION_UP)
        {
            mLayout.removeView(draw);
            touch_state = false;
        }
    }

    /**
     * Gets the current position of where you are located at
     * @return an Array with the current position
     */
    public int[] getPosition()
    {
        if(distance > min_distance && touch_state)
        {
            return new int[] { position_x, position_y };
        }
        return new int[] { 0, 0 };
    }

    /**
     * Gets the X position of the Joystick
     * @return x postion
     */
    public int getX()
    {
        if(distance > min_distance && touch_state)
        {
            return position_x;
        }
        return 0;
    }

    /**
     * Gets the Y position of the Joystick
     * @return y position
     */
    public int getY()
    {
        if(distance > min_distance && touch_state)
        {
            return position_y;
        }
        return 0;
    }

    /**
     * Gets the angle where you are pulling the stick
     * @return the angle or 0 by default
     */
    public float getAngle()
    {
        if(distance > min_distance && touch_state)
        {
            return angle;
        }
        return 0;
    }

    /**
     * Gets the distance from 0 the Joystick goes
     * @return the distance or 0 by default
     */
    public float getDistance()
    {
        if(distance > min_distance && touch_state)
        {
            return distance;
        }
        return 0;
    }

    /**
     * Sets the minium distance
     * @param minDistance The value for minimum distance
     */
    public void setMinimumDistance(int minDistance)
    {
        min_distance = minDistance;
    }

    /**
     * Gets the minimun distance
     * @return minimun distance
     */
    public int getMinimumDistance()
    {
        return min_distance;
    }

    /**
     * Looks at all 8 positions of a joystick
     * @return position it is currently on
     */
    public int get8Direction()
    {
        if(distance > min_distance && touch_state)
        {
            if(angle >= 247.5 && angle < 292.5 )
            {
                return STICK_UP;
            }
            else if(angle >= 292.5 && angle < 337.5 )
            {
                return STICK_UPRIGHT;
            }
            else if(angle >= 337.5 || angle < 22.5 )
            {
                return STICK_RIGHT;
            }
            else if(angle >= 22.5 && angle < 67.5 )
            {
                return STICK_DOWNRIGHT;
            }
            else if(angle >= 67.5 && angle < 112.5 )
            {
                return STICK_DOWN;
            }
            else if(angle >= 112.5 && angle < 157.5 )
            {
                return STICK_DOWNLEFT;
            }
            else if(angle >= 157.5 && angle < 202.5 )
            {
                return STICK_LEFT;
            }
            else if(angle >= 202.5 && angle < 247.5 )
            {
                return STICK_UPLEFT;
            }
        }
        else if(distance <= min_distance && touch_state)
        {
            return STICK_NONE;
        }
        return 0;
    }

    /**
     * Gets the 4 major directions
     * @return position currently on
     */
    public int get4Direction()
    {
        if(distance > min_distance && touch_state)
        {
            if(angle >= 225 && angle < 315 )
            {
                return STICK_UP;
            }
            else if(angle >= 315 || angle < 45 )
            {
                return STICK_RIGHT;
            }
            else if(angle >= 45 && angle < 135 )
            {
                return STICK_DOWN;
            }
            else if(angle >= 135 && angle < 225 )
            {
                return STICK_LEFT;
            }
        }
        else if(distance <= min_distance && touch_state)
        {
            return STICK_NONE;
        }
        return 0;
    }

    /**
     * Sets the offset amount
     * @param offset The offset received from the caller
     */
    public void setOffset(int offset)
    {
        OFFSET = offset;
    }

    /**
     * Gets the offset amount
     * @return the offset
     */
    public int getOffset()
    {
        return OFFSET;
    }

    /**
     * Sets the stick
     * @param alpha Alpha received from the caller
     */
    public void setStickAlpha(int alpha)
    {
        STICK_ALPHA = alpha;
        paint.setAlpha(alpha);
    }

    /**
     * Gets the Alpha
     * @return the Sticks alpha
     */
    public int getStickAlpha()
    {
        return STICK_ALPHA;
    }

    /**
     * sets the layouts alpha
     * @param alpha Alpha received from the caller
     */
    public void setLayoutAlpha(int alpha)
    {
        LAYOUT_ALPHA = alpha;
        mLayout.getBackground().setAlpha(alpha);
    }

    /**
     * Get the layout alpha
     * @return Alpha received from the caller
     */
    public int getLayoutAlpha()
    {
        return LAYOUT_ALPHA;
    }

    /**
     * Sets the stick size
     * @param width The value of width received from the caller
     * @param height The value of height received from the caller
     */
    public void setStickSize(int width, int height)
    {
        stick = Bitmap.createScaledBitmap(stick, width, height, false);
        stick_width = stick.getWidth();
        stick_height = stick.getHeight();
    }

    /**
     * Sets the sticks width
     * @param width The value of width received from the caller
     */
    public void setStickWidth(int width)
    {
        stick = Bitmap.createScaledBitmap(stick, width, stick_height, false);
        stick_width = stick.getWidth();
    }

    /**
     * Sets the sticks height
     * @param height The value of height received from the caller
     */
    public void setStickHeight(int height)
    {
        stick = Bitmap.createScaledBitmap(stick, stick_width, height, false);
        stick_height = stick.getHeight();
    }

    /**
     * Gets the sticks width
     * @return sticks width
     */
    public int getStickWidth()
    {
        return stick_width;
    }

    /**
     * Gets the sticks height
     * @return sticks height
     */
    public int getStickHeight()
    {
        return stick_height;
    }

    /**
     * set the layout size
     * @param width The width received from the caller
     * @param height The height received from the caller
     */
    public void setLayoutSize(int width, int height)
    {
        params.width = width;
        params.height = height;
    }

    /**
     * Get the layout width
     * @return the width
     */
    public int getLayoutWidth()
    {
        return params.width;
    }

    /**
     * Gets the layouts height
     * @return the height
     */
    public int getLayoutHeight()
    {
        return params.height;
    }

    /**
     * Calculates the angle
     * @param x The value of X received from the caller
     * @param y The value of Y received from the caller
     * @return The degrees
     */
    private double cal_angle(float x, float y)
    {
        if(x >= 0 && y >= 0)
        {
            return Math.toDegrees(Math.atan(y / x));
        }
        else if(x < 0 && y >= 0)
        {
            return Math.toDegrees(Math.atan(y / x)) + 180;
        }
        else if(x < 0 && y < 0)
        {
            return Math.toDegrees(Math.atan(y / x)) + 180;
        }
        else if(x >= 0 && y < 0)
        {
            return Math.toDegrees(Math.atan(y / x)) + 360;
        }
        return 0;
    }

    /**
     * Draws the layouts
     */
    private void draw()
    {
        try
        {
            mLayout.removeView(draw);
        }
        catch (Exception e)
        {

        }
        mLayout.addView(draw);
    }

    /**
     * Draws everything on canvas
     */
    private class DrawCanvas extends View
    {
        float x, y;

        private DrawCanvas(Context mContext)
        {
            super(mContext);
        }

        public void onDraw(Canvas canvas)
        {
            canvas.drawBitmap(stick, x, y, paint);
        }

        private void position(float pos_x, float pos_y)
        {
            x = pos_x - (stick_width / 2);
            y = pos_y - (stick_height / 2);
        }
    }
}