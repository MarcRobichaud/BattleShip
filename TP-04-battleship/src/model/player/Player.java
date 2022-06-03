package model.player;

import controller.IController;
import model.Board.IBoard;
import model.audio.SoundEffect;
import model.ship.Ship;
import util.PlayerState;
import util.Position;
import util.ShootType;
import util.SoundEffectType;

import java.util.ArrayList;

public abstract class Player implements IPlayer
{
    protected IController controller;
    protected IBoard attackBoard;
    protected IBoard defenseBoard;

    protected String name;
    protected int remainingShips;
    protected boolean isAlive;
    protected PlayerState state;

    public Player(IBoard attackBoard, IBoard defenseBoard, String name)
    {
        this.attackBoard = attackBoard;
        this.defenseBoard = defenseBoard;
        this.name = name;

        remainingShips = 4;
        isAlive = true;
    }

    private static ShootType HitOrMiss(Boolean isFilled)
    {
        return isFilled ? ShootType.HIT : ShootType.MISS;
    }

    @Override
    public ArrayList<Position> getPositionsWithShipsInDefenseBoard()
    {
        return defenseBoard.getPositionsWithShip();
    }

    @Override
    public PlayerState getState()
    {
        return state;
    }

    @Override
    public boolean isAlive()
    {
        return isAlive;
    }

    @Override
    public void setController(IController controller)
    {
        this.controller = controller;
        defenseBoard.fillTiles(controller.spawnShips());
    }

    @Override
    public void changeState(PlayerState state)
    {
        this.state = state;
    }

    @Override
    public ShootType getShotAt(Position position)
    {
        defenseBoard.changeTileStateAt(position);
        ShootType type = HitOrMiss(defenseBoard.isTileFilled(position));

        if (type == ShootType.HIT)
            actionOnEnemyHit(position);
        else
        {
            controller.changeConsoleOutputOfViewBoard(getMissOutput(position));
            SoundEffect.play(SoundEffectType.SPLASH);
        }


        return type;
    }

    protected void actionOnEnemyHit(Position position)
    {
        Ship ship = defenseBoard.getShipAt(position);
        ship.removeHealth();
        SoundEffect.play(SoundEffectType.BOOM);

        if (ship.isSunk())
            actionOnShipSunk(ship);
        else
            controller.changeConsoleOutputOfViewBoard(getHitOutput(position));
    }

    protected void actionOnShipSunk(Ship ship)
    {
        remainingShips--;
        controller.changeConsoleOutputOfViewBoard(name + " " + ship.getName() + " sunk");

        if (remainingShips <= 0)
        {
            isAlive = false;
            controller.changeConsoleOutputOfViewBoard(getLostOutput());
        }
    }

    protected abstract String getMissOutput(Position position);

    protected abstract String getHitOutput(Position position);

    protected abstract String getLostOutput();
}