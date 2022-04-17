package com.trogdadr2tr;
/**
 * Created by Danny on 10/13/2017.
 */

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 *
 */
public class ServerMessenger extends Activity
{
    TextView console;
    EditText hostNameInput, portNumberInput, messageInput;
    Button buttonConnect, buttonClear, sendButton;
    Socket socket;
    ObjectInputStream in;
    ObjectOutputStream out;

    /**
     *Creates the screen and sets all the values from the XML File
     * @param savedInstanceState The instance received from the caller
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_server);

        hostNameInput = (EditText) findViewById(R.id.addressEditText);
        portNumberInput = (EditText) findViewById(R.id.portEditText);
        messageInput = (EditText) findViewById(R.id.serverMessage);
        buttonConnect = (Button) findViewById(R.id.connectButton);
        buttonClear = (Button) findViewById(R.id.clearButton);
        sendButton = (Button) findViewById(R.id.sendButton);
        console = (TextView) findViewById(R.id.responseTextView);

        buttonConnect.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                SocketConnector myClient = new SocketConnector(socket, hostNameInput.getText()
                        .toString(), Integer.parseInt(portNumberInput
                        .getText().toString()), console, ServerMessenger.this);
                myClient.execute();
            }
        });

        buttonClear.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                console.setText("");
            }
        });

        sendButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                HandleMessage hm = new HandleMessage(out, in, console);
                hm.execute();
            }
        });


    }

    /**
     * Test function to use the execute function of HandleMessage
     * @param stuff
     */
    public void doTheThing(String stuff)
    {
        HandleMessage yus = new HandleMessage(out, in, console);
        yus.execute();
    }

    /**
     * Sets a socket to what was received from the caller
     * @param s The socket received from the caller
     */
    public void setSocket(Socket s)
    {
        socket = s;
    }

    /**
     * Sets the output to what was received from the caller
     * @param out The output received from the caller
     */
    public void setOutput(ObjectOutputStream out)
    {
        this.out = out;
    }

    /**
     * Sets the Input to what was received from the caller
     * @param in The input received from the caller
     */
    public void setInput(ObjectInputStream in)
    {
        this.in = in;
    }
}