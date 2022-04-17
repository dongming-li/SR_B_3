package com.example.r0xas.trogdadr2tr;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import java.io.*;
import java.net.*;

public class MainActivity extends AppCompatActivity
{
    Button b1, b2;

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
            public void onClick(View view) {
                String host = "proj-309-sr-b-3.cs.iastate.edu";
                int port = 4444;
                try (Socket serverSocket = new Socket(host, port);
                     PrintWriter out = new PrintWriter(serverSocket.getOutputStream(), true);
                     BufferedReader in = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));) {
                    BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
                    String fromServer;
                    String fromUser;

                    while (true) {
                        fromUser = "Hello";
                        Toast.makeText(getApplicationContext(), "Client: Hello", Toast.LENGTH_SHORT).show();
                        out.println(fromUser);
                        fromServer = in.readLine();
                        Toast.makeText(getApplicationContext(), "Server: " + fromServer, Toast.LENGTH_SHORT).show();

                    }
                } catch (UnknownHostException e) {
                    Toast.makeText(getApplicationContext(), "Don't know about host " + host, Toast.LENGTH_SHORT).show();
                    System.exit(1);
                } catch (IOException e) {
                    Toast.makeText(getApplicationContext(), "Couldn't get I/O for the connection to " + host, Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });
       /* b2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent i = new Intent(MainActivity.this, MainJoyStick.class);
                startActivity(i);
            }
        });*/
    }
}
