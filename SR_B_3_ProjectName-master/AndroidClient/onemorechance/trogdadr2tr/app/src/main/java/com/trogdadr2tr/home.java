package com.trogdadr2tr;
/**
 * Created by Danny on 11/1/2017.
 */
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class home extends AppCompatActivity
{

    /**
     * Creates a new  GameSurface
     * @param savedInstanceState The saved instance received from the caller
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(new GameSurface(this));
    }
}
