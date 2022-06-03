package model.player;

import controller.IController;
import model.Board.IBoard;
import model.audio.SoundEffect;
import model.network.Client;
import model.network.HostClient;
import model.network.Host;
import util.*;

import java.util.ArrayList;

public class RemotePlayer implements IPlayer
{
    private IController controller;
    private final IBoard attackBoard;
    private final HostClient server;

    private boolean isAlive = true;
    private PlayerState state;

    private int hitCounter;

    public RemotePlayer(IBoard attackBoard, HostClient server)
    {
        this.attackBoard = attackBoard;
        this.server = server;
    }

    public void setController(IController controller)
    {
        this.controller = controller;
    }

    @Override
    public ArrayList<Position> getPositionsWithShipsInDefenseBoard()
    {
        return null;
    }

    @Override
    public boolean isAlive()
    {
        return isAlive;
    }

    public PlayerState getState()
    {
        return state;
    }

    public void changeState(PlayerState state)
    {
        if (state == PlayerState.MY_TURN)
            server.receiveAttack();

        this.state = state;
    }

    public void tryShootAt(Position position)
    {
        if (state == PlayerState.MY_TURN && attackBoard.getTileStateAt(position) == TileState.UNKNOWN)
        {
            attackBoard.changeTileStateAt(position);
            controller.shootPlayer1At(position);
        }
    }

    public ShootType getShotAt(Position position)
    {
        ShootType type = HitOrMiss(isTileFilledAt(position));

        if (type == ShootType.HIT)
            actionOnEnemyHit(position);
        else
        {
            controller.changeConsoleOutputOfViewBoard(getMissOutput(position));
            SoundEffect.play(SoundEffectType.SPLASH);
        }
        return type;
    }

    private void actionOnEnemyHit(Position position)
    {
        if(++hitCounter == 12)
        {
            controller.changeConsoleOutputOfViewBoard(getLostOutput());
            isAlive = false;
        }
        else
        {
            controller.changeConsoleOutputOfViewBoard(getHitOutput(position));
        }
        SoundEffect.play(SoundEffectType.BOOM);
    }


    private String getMissOutput(Position position)
    {
        return "Player 1 : Missed at " + position.toStringLetterFormat();
    }

    private String getHitOutput(Position position)
    {
        return "Player 1 : Hit at " + position.toStringLetterFormat();
    }

    protected String getLostOutput()
    {
        return "You win";
    }

    private boolean isTileFilledAt(Position position)
    {
        if (server instanceof Client || server instanceof Host)
        {
            return server.RequestAttack(position.toString());
        }
        return false;
    }

    private static ShootType HitOrMiss(boolean isFilled)
    {
        return isFilled ? ShootType.HIT : ShootType.MISS;
    }
}