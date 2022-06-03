package model.network;

import controller.IController;
import exception.PositionFormatException;
import util.Debug;
import util.Position;
import util.ShootType;
import view.OnlineView;
import view.RematchView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public abstract class HostClient extends Thread
{
    protected IController controller;
    protected OnlineView onlineView;

    protected BufferedReader in;
    protected PrintWriter out;

    protected ReceiveAttack activeReceiveAttack;

    public HostClient(IController controller, OnlineView onlineView)
    {
        this.controller = controller;
        this.onlineView = onlineView;
    }

    public void restart()
    {
        controller.startOnline(this);
        if (this instanceof Host)
        {
            receiveAttack();
        }
    }

    public void notRestart()
    {
        controller.changeConsoleOutputOfViewBoard("Player 2 Refused");
    }

    public void close()
    {
        try
        {
            if (in != null && out != null)
            {
                in.close();
                out.close();
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void send(String message)
    {
        out.println(message + '\n');
        out.flush();
    }

    //ATTACKING REQUEST PATTERN
    //A1. Host wait for position
    //A2. Client Send Position
    //A3. Host detect attack request
    //A4. Host check content at position
    //A5. Host send content at position (true, false)
    //A6. Client receive content at position
    //A7. Client change attack board

    public boolean RequestAttack(String position)
    {
        //A2. Client Send Position
        send(position);
        //A7. Client change attack board
        return Boolean.parseBoolean(receive());
    }

    //A3. Host receive position
    public void receiveAttack()
    {
        activeReceiveAttack = new ReceiveAttack(this);
    }

    public void attack(String position)
    {
        try
        {
            //A4. Host check content at position
            boolean bool = controller.shootPlayer1At(Position.parsePosition(position)) == ShootType.HIT;
            //A5. Host send content at position (true, false)
            send(Boolean.toString(bool));
        }
        catch (PositionFormatException e)
        {
            e.printStackTrace();
        }
    }

    //A6. Client receive content at position
    public String receive()
    {
        StringBuilder message = new StringBuilder();
        try
        {
            String line = in.readLine();
            while(line != null && line.length() > 0)
            {
                message.append(line);
                line = in.readLine();
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        Debug.log("Received " + message);

        return message.toString();
    }

    //REMATCH REQUEST PATTERN
    //R1. Host wait for a position
    //R2. Client send a rematch request instead
    //R3. Host detects rematch request
    //R4. Host either accept or refuse the invitation
    //R5.1 (Case Host Accept) : Host Restart Game
    //R5.2 (Case Host Refuse) : Host continue game
    //R6. Host send choice
    //R.7 Client wait for answer
    //R8.1 (Case Host Accept) : Client restart Game
    //R8.2 (Case Host Refuse) : Client continue Game

    public void requestRematch()
    {
        //R2. Client send a rematch request instead
        send("rematch");
        Debug.log("rematch request sent");

        //R7. Wait for answer
        if(Boolean.parseBoolean(receive()))
        {
            //R8.1 (Case Host Accept) : Client restart Game
            Debug.log("He accept");
            restart();
        }
        else
        {
            //R8.2 (Case Host Refuse) : Client continue Game

            notRestart();
        }
    }

    public void specialRequestMatch()
    {
        send("rematch");
        Debug.log("special rematch request sent");
    }

    public void rematch()
    {
        //4. Host either accept or refuse
        new RematchView(this);
    }

    //region 5.
    public void acceptRematch()
    {
        //5.1 (Case Host Accept) : Host Restart Game
        restart();

        //R6. Host send choice
        send(Boolean.toString(true));
    }

    public void refuseRematch()
    {
        //5.2 (Case Host Refuse) : Host continue game
        receiveAttack();

        //R6. Host send choice
        send(Boolean.toString(false));
    }
    //endregion
}