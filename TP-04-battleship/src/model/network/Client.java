package model.network;

import controller.IController;
import util.Debug;
import view.OnlineView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client extends HostClient
{
    private final String serverIPAddress;
    private Socket socket;

    private final int port = 1234;

    public Client(String serverIPAddress, IController controller, OnlineView onlineView)
    {
        super(controller, onlineView);
        this.serverIPAddress = serverIPAddress;
        this.start();
    }

    @Override
    public void close()
    {
        try
        {
            super.close();
            socket.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void run()
    {
        try
        {
            socket = new Socket(serverIPAddress, port);
            Debug.log("Connected");

            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());

            if (onlineView != null)
                onlineView.dispose();

            controller.startOnline( this);
        }
        catch (IOException e)
        {
            if (onlineView != null)
                onlineView.setTextWrongIPLabel("No host at that IP");
            else
                System.err.println(e);
        }
    }
}