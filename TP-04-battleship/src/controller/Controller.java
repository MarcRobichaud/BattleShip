package controller;

import model.Board.Board;
import model.network.*;
import model.player.AI;
import model.player.IPlayer;
import model.player.LocalPlayer;
import model.player.RemotePlayer;
import model.ship.Ship;
import model.ship.ShipSpawner;
import util.*;
import view.*;

import java.util.ArrayList;

public class Controller implements IController
{
    private IViewBoard viewBoard;
    private IPlayer player1;
    private IPlayer player2;
    private HostClient server;

    public Controller(IMainMenu mainMenu)
    {
        mainMenu.setController(this);
    }

    @Override
    public void startVsAi()
    {
        player1 = new LocalPlayer(new Board(), new Board(), "Player1");
        player2 = new AI(new Board(), new Board());
        viewBoard = new ViewBoard();

        player1.setController(this);
        player2.setController(this);
        viewBoard.setController(this);

        startingPlayer();
        InitViewDefenseBoard();
    }

    public void startOnline(HostClient server)
    {
        if (this.server != null)
        {
            player1 = null;
            player2 = null;
            viewBoard.disposeFrame();
            viewBoard = null;
        }
        else
        {
            this.server = server;
        }
        player1 = new LocalPlayer(new Board(), new Board(), "Player1");
        player2 = new RemotePlayer(new Board(), server);
        viewBoard = new ViewBoard();

        player1.setController(this);
        player2.setController(this);
        viewBoard.setController(this);

        startingPlayer();
        InitViewDefenseBoard();
    }

    private void InitViewDefenseBoard()
    {
        for (Position p : player1.getPositionsWithShipsInDefenseBoard())
        {
            viewBoard.changeTileOfDefenseBoard(p, GameColor.FILLED_COLOR);
        }
    }

    @Override
    public ArrayList<Ship> spawnShips()
    {
        ShipSpawner shipSpawner = new ShipSpawner();
        return new ArrayList<>(shipSpawner.getMyShipTab());
    }

    @Override
    public void shoot(Position position)
    {
        player1.tryShootAt(position);
    }

    @Override
    public ShootType shootPlayer1At(Position position)
    {
        ShootType shoot = player1.getShotAt(position);
        viewBoard.changeTileOfDefenseBoard(position, attackTileColor(shoot));

        changePlayersState();
        return shoot;
    }

    @Override
    public ShootType shootPlayer2At(Position position)
    {
        ShootType shoot = player2.getShotAt(position);
        viewBoard.changeTileOfAttackBoard(position, attackTileColor(shoot));
        viewBoard.disableButtonAt(position);

        changePlayersState();
        return shoot;
    }

    private void changePlayersState()
    {
        if (!player1.isAlive() || !player2.isAlive())
        {
            player1.changeState(PlayerState.ENEMY_TURN);
            player2.changeState(PlayerState.ENEMY_TURN);
            if (server != null)
            {
                server.receiveAttack();
            }
        }
        else if (player1.getState() == PlayerState.MY_TURN)
        {
            player1.changeState(PlayerState.ENEMY_TURN);
            player2.changeState(PlayerState.MY_TURN);
        }
        else
        {
            player2.changeState(PlayerState.ENEMY_TURN);
            player1.changeState(PlayerState.MY_TURN);
        }
    }

    private void startingPlayer()
    {
        if (server == null || server instanceof Client)
            player1.changeState(PlayerState.MY_TURN);
    }

    public void changeConsoleOutputOfViewBoard(String output)
    {
        viewBoard.changeConsoleOutput(output);
    }

    private int attackTileColor(ShootType shoot)
    {
        return shoot == ShootType.HIT ? GameColor.HIT_COLOR : GameColor.MISS_COLOR;
    }

    @Override
    public void quit()
    {
        Debug.log("restart");

        if (server != null)
            server.close();

        new Controller(new MainMenu());
    }

    @Override
    public void restart()
    {
        Debug.log("quit");
        if (server == null)
        {
            viewBoard.disposeFrame();
            startVsAi();
        }
        else
        {
            if (player1.getState() == PlayerState.MY_TURN)
            {
                server.requestRematch();
            }
            else if (!(player1.isAlive() && player2.isAlive()))
            {
                server.specialRequestMatch();
            }
            else
            {
                changeConsoleOutputOfViewBoard("Wait for your turn to send a rematch request");
            }
        }
    }

    @Override
    public void option()
    {
        new OptionMenu();
    }
}