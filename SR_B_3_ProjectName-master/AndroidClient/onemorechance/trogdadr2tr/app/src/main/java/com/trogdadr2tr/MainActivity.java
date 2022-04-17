package com.trogdadr2tr;
/**
 * Created by Danny on 11/1/2017.
 */
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class MainActivity extends AppCompatActivity
{
    Button b1, b2, b3;

    /**
     * Creates the screen for the Main program
     * @param savedInstanceState The instance received from the caller
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        // Set fullscreen
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Set No Title
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        b1 = (Button) findViewById(R.id.start);
        b2 = (Button) findViewById(R.id.options);
        b3 = (Button) findViewById(R.id.joystick);
        b1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent i = new Intent(MainActivity.this, home.class);
                startActivity(i);
            }
        });
        b2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent j = new Intent(MainActivity.this, ServerMessenger.class);
                startActivity(j);
            }
        });
        b3.setOnClickListener(new View.OnClickListener() 
        {
            @Override
            public void onClick(View view)
            {
                Intent z = new Intent(MainActivity.this, MainJoyStick.class);
                startActivity(z);
            }
        });
        ClientTest test = new ClientTest();
        test.start();
    }
}
