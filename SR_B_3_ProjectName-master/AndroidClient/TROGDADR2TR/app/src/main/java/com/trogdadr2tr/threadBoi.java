package com.trogdadr2tr;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by Danny on 10/10/2017.
 */

public class threadBoi extends Thread {
    public Button sendButton;
    public Button connectButton;
    public Button clearButton;
    public Socket socket;

    public threadBoi (Button connect, Button clear, Button send, final EditText textAddress, final EditText textPort, final TextView response)
    {
        sendButton = send;
        connectButton = connect;
        clearButton = clear;
        connectButton.setOnClickListener(new OnClickListener()
        {

            @Override
            public void onClick(View arg0)
            {
                    /*
                    Client myClient = new Client(editTextAddress.getText()
                            .toString(), Integer.parseInt(editTextPort
                            .getText().toString()), console);
                    myClient.execute();
                    */
                if (socket != null) {
                    try
                    {
                        socket.close();
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }
                try
                {
                    socket = new Socket(textAddress.getText().toString(), Integer.parseInt(textPort.getText().toString()));
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        });

        clearButton.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                response.setText("does nothing yet");
            }
        });

        sendButton.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v) {
                try (PrintWriter out = new PrintWriter(socket.getOutputStream());
                     BufferedReader brInput = new BufferedReader(new InputStreamReader(socket.getInputStream()));)
                {
                    out.println("ping");
                    response.setText(brInput.readLine());
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        });
    }

    public void run () {

    }
}
