package com.example.r0xas.trogdadr2tr;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class home extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(new GameSurface(this));
    }
}
