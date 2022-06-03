package model.network;

import controller.IController;
import util.Debug;
import view.OnlineView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Host extends HostClient
{
    private ServerSocket serverSocket;
    private Socket socket;

    private final int port = 1234;

    public Host(IController controller, OnlineView onlineView)
    {
        super(controller, onlineView);
        this.start();
    }

    @Override
    public void close()
    {
        try
        {
            super.close();
            socket.close();
            serverSocket.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void cancel()
    {
        try
        {
            serverSocket.close();
            this.interrupt();
        }
        catch (IOException e)
        {
            System.err.println(e);
        }
    }

    @Override
    public void run()
    {
        try
        {
            serverSocket = new ServerSocket(port);
            socket = serverSocket.accept();
            Debug.log("Connected");

            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());

            if (onlineView != null)
                onlineView.dispose();

            controller.startOnline( this);
            receiveAttack();
        }
        catch (IOException e)
        {
            System.err.println(e);
        }
    }
}