package com.example.r0xas.serverconnection.fanta;
        import android.app.Activity;
        import android.os.Bundle;
        import android.view.View;
        import android.view.View.OnClickListener;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.TextView;

        import com.example.r0xas.serverconnection.R;

        import java.io.ObjectInputStream;
        import java.io.ObjectOutputStream;
        import java.net.Socket;

public class MainActivity extends Activity implements Receiver
{
    TextView console;
    EditText hostNameInput, portNumberInput, messageInput;
    Button buttonConnect, buttonClear, sendButton;
    Socket socket;
    ObjectInputStream in;
    ObjectOutputStream out;

    @Override
    public void Receiver()
    {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        hostNameInput = (EditText) findViewById(R.id.addressEditText);
        portNumberInput = (EditText) findViewById(R.id.portEditText);
        messageInput = (EditText) findViewById(R.id.serverMessage);
        buttonConnect = (Button) findViewById(R.id.connectButton);
        buttonClear = (Button) findViewById(R.id.clearButton);
        sendButton = (Button) findViewById(R.id.sendButton);
        console = (TextView) findViewById(R.id.responseTextView);

        buttonConnect.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                SocketConnector myClient = new SocketConnector(socket, hostNameInput.getText()
                        .toString(), Integer.parseInt(portNumberInput
                        .getText().toString()), console, MainActivity.this);
                myClient.execute();
            }
        });

        buttonClear.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                console.setText("");
            }
        });

        sendButton.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                HandleMessage hm = new HandleMessage(out, in, console);
                hm.execute();
            }
        });


    }

    public void doTheThing(String stuff)
    {
        HandleMessage yus = new HandleMessage(out, in, console);
        yus.execute();
    }

    public void setSocket(Socket s)
    {
        socket = s;
    }


    public void setOutput(ObjectOutputStream out)
    {
        this.out = out;
    }

    public void setInput(ObjectInputStream in)
    {
        this.in = in;
    }
}