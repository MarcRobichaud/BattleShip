package model.network;

import util.Debug;
import view.RematchView;

import java.io.IOException;

public class ReceiveAttack extends Thread
{
    private final HostClient hostClient;
    private boolean leaving;

    ReceiveAttack(HostClient hostClient)
    {
        this.hostClient = hostClient;
        this.start();
    }

    //REMATCH REQUEST PATTERN
    //R1. Host wait for a position
    //R2. Client send a rematch request instead
    //R3. Host detects rematch request
    //R4. Host either accept or refuse the invitation
    //R5.1 (Case Host Accept) : Host Restart Game
    //R5.2 (Case Host Refuse) : Host continue game
    //R6. Host send choice
    //R7.1 (Case Host Accept) : Client restart Game
    //R7.2 (Case Host Refuse) : Client continue Game

    @Override
    public void run()
    {
        StringBuilder message = new StringBuilder();
        try
        {
            //A1. Host wait for a position
            //R1. Host wait for a position
            Debug.log("trying to receive");
            String line = hostClient.in.readLine();

            while ((line != null && line.length() > 0) && !leaving)
            {
                message.append(line);
                line = hostClient.in.readLine();
            }
            if (leaving) return;
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        Debug.log("Received " + message);

        switch (message.toString())
        {
            case "rematch" -> {
                //R3. Host detects rematch request
                Debug.log("rematch request detected");
                hostClient.rematch();
            }
            case "true" -> hostClient.restart();
            case "false" ->{
                hostClient.notRestart();
                hostClient.receiveAttack();
            }
            default -> hostClient.attack(message.toString()); //A3. Host detect attack request
        }
    }
}